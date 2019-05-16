import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Inspection } from 'app/shared/model/inspection.model';
import { InspectionService } from './inspection.service';
import { InspectionComponent } from './inspection.component';
import { InspectionDetailComponent } from './inspection-detail.component';
import { InspectionUpdateComponent } from './inspection-update.component';
import { InspectionDeletePopupComponent } from './inspection-delete-dialog.component';
import { IInspection } from 'app/shared/model/inspection.model';

@Injectable({ providedIn: 'root' })
export class InspectionResolve implements Resolve<IInspection> {
  constructor(private service: InspectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInspection> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Inspection>) => response.ok),
        map((inspection: HttpResponse<Inspection>) => inspection.body)
      );
    }
    return of(new Inspection());
  }
}

export const inspectionRoute: Routes = [
  {
    path: '',
    component: InspectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InspectionDetailComponent,
    resolve: {
      inspection: InspectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InspectionUpdateComponent,
    resolve: {
      inspection: InspectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InspectionUpdateComponent,
    resolve: {
      inspection: InspectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspections'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inspectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InspectionDeletePopupComponent,
    resolve: {
      inspection: InspectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspections'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
