import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataId } from 'app/shared/model/data-id.model';
import { AccountService } from 'app/core';
import { DataIdService } from './data-id.service';

@Component({
  selector: 'jhi-data-id',
  templateUrl: './data-id.component.html'
})
export class DataIdComponent implements OnInit, OnDestroy {
  dataIds: IDataId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataIdService: DataIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataId[]>) => res.ok),
        map((res: HttpResponse<IDataId[]>) => res.body)
      )
      .subscribe(
        (res: IDataId[]) => {
          this.dataIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataId) {
    return item.id;
  }

  registerChangeInDataIds() {
    this.eventSubscriber = this.eventManager.subscribe('dataIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
