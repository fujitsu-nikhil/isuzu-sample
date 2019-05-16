import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInspectionId } from 'app/shared/model/inspection-id.model';
import { AccountService } from 'app/core';
import { InspectionIdService } from './inspection-id.service';

@Component({
  selector: 'jhi-inspection-id',
  templateUrl: './inspection-id.component.html'
})
export class InspectionIdComponent implements OnInit, OnDestroy {
  inspectionIds: IInspectionId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inspectionIdService: InspectionIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inspectionIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IInspectionId[]>) => res.ok),
        map((res: HttpResponse<IInspectionId[]>) => res.body)
      )
      .subscribe(
        (res: IInspectionId[]) => {
          this.inspectionIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInspectionIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInspectionId) {
    return item.id;
  }

  registerChangeInInspectionIds() {
    this.eventSubscriber = this.eventManager.subscribe('inspectionIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
