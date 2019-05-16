import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InspectionResult } from 'app/shared/model/inspection-result.model';
import { InspectionResultService } from './inspection-result.service';
import { InspectionResultComponent } from './inspection-result.component';
import { InspectionResultDetailComponent } from './inspection-result-detail.component';
import { InspectionResultUpdateComponent } from './inspection-result-update.component';
import { InspectionResultDeletePopupComponent } from './inspection-result-delete-dialog.component';
import { IInspectionResult } from 'app/shared/model/inspection-result.model';

@Injectable({ providedIn: 'root' })
export class InspectionResultResolve implements Resolve<IInspectionResult> {
  constructor(private service: InspectionResultService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInspectionResult> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InspectionResult>) => response.ok),
        map((inspectionResult: HttpResponse<InspectionResult>) => inspectionResult.body)
      );
    }
    return of(new InspectionResult());
  }
}

export const inspectionResultRoute: Routes = [
  {
    path: '',
    component: InspectionResultComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionResults'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InspectionResultDetailComponent,
    resolve: {
      inspectionResult: InspectionResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionResults'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InspectionResultUpdateComponent,
    resolve: {
      inspectionResult: InspectionResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionResults'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InspectionResultUpdateComponent,
    resolve: {
      inspectionResult: InspectionResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionResults'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inspectionResultPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InspectionResultDeletePopupComponent,
    resolve: {
      inspectionResult: InspectionResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InspectionResults'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
