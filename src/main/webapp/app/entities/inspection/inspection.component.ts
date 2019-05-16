import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInspection } from 'app/shared/model/inspection.model';
import { AccountService } from 'app/core';
import { InspectionService } from './inspection.service';

@Component({
  selector: 'jhi-inspection',
  templateUrl: './inspection.component.html'
})
export class InspectionComponent implements OnInit, OnDestroy {
  inspections: IInspection[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inspectionService: InspectionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inspectionService
      .query()
      .pipe(
        filter((res: HttpResponse<IInspection[]>) => res.ok),
        map((res: HttpResponse<IInspection[]>) => res.body)
      )
      .subscribe(
        (res: IInspection[]) => {
          this.inspections = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInspections();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInspection) {
    return item.id;
  }

  registerChangeInInspections() {
    this.eventSubscriber = this.eventManager.subscribe('inspectionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
