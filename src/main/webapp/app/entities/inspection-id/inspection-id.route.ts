import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InspectionId } from 'app/shared/model/inspection-id.model';
import { InspectionIdService } from './inspection-id.service';
import { InspectionIdComponent } from './inspection-id.component';
import { InspectionIdDetailComponent } from './inspection-id-detail.component';
import { InspectionIdUpdateComponent } from './inspection-id-update.component';
import { InspectionIdDeletePopupComponent } from './inspection-id-delete-dialog.component';
import { IInspectionId } from 'app/shared/model/inspection-id.model';

@Injectable({ providedIn: 'root' })
export class InspectionIdResolve implements Resolve<IInspectionId> {
  constructor(private service: InspectionIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInspectionId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InspectionId>) => response.ok),
        map((inspectionId: HttpResponse<InspectionId>) => inspectionId.body)
      );
    }
    return of(new InspectionId());
  }
}

export const inspectionIdRoute: Routes = [
  {
    path: '',
    component: InspectionIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionIds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InspectionIdDetailComponent,
    resolve: {
      inspectionId: InspectionIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionIds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InspectionIdUpdateComponent,
    resolve: {
      inspectionId: InspectionIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionIds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InspectionIdUpdateComponent,
    resolve: {
      inspectionId: InspectionIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionIds'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inspectionIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InspectionIdDeletePopupComponent,
    resolve: {
      inspectionId: InspectionIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionIds'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
