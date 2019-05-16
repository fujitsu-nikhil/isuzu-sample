import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInspectionResult } from 'app/shared/model/inspection-result.model';
import { AccountService } from 'app/core';
import { InspectionResultService } from './inspection-result.service';

@Component({
  selector: 'jhi-inspection-result',
  templateUrl: './inspection-result.component.html'
})
export class InspectionResultComponent implements OnInit, OnDestroy {
  inspectionResults: IInspectionResult[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inspectionResultService: InspectionResultService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inspectionResultService
      .query()
      .pipe(
        filter((res: HttpResponse<IInspectionResult[]>) => res.ok),
        map((res: HttpResponse<IInspectionResult[]>) => res.body)
      )
      .subscribe(
        (res: IInspectionResult[]) => {
          this.inspectionResults = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInspectionResults();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInspectionResult) {
    return item.id;
  }

  registerChangeInInspectionResults() {
    this.eventSubscriber = this.eventManager.subscribe('inspectionResultListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
