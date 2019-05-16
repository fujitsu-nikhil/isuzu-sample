import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataId } from 'app/shared/model/data-id.model';
import { DataIdService } from './data-id.service';
import { DataIdComponent } from './data-id.component';
import { DataIdDetailComponent } from './data-id-detail.component';
import { DataIdUpdateComponent } from './data-id-update.component';
import { DataIdDeletePopupComponent } from './data-id-delete-dialog.component';
import { IDataId } from 'app/shared/model/data-id.model';

@Injectable({ providedIn: 'root' })
export class DataIdResolve implements Resolve<IDataId> {
  constructor(private service: DataIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataId>) => response.ok),
        map((dataId: HttpResponse<DataId>) => dataId.body)
      );
    }
    return of(new DataId());
  }
}

export const dataIdRoute: Routes = [
  {
    path: '',
    component: DataIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DataIds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataIdDetailComponent,
    resolve: {
      dataId: DataIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DataIds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataIdUpdateComponent,
    resolve: {
      dataId: DataIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DataIds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataIdUpdateComponent,
    resolve: {
      dataId: DataIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DataIds'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataIdDeletePopupComponent,
    resolve: {
      dataId: DataIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DataIds'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
