import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IsuzuSampleApplicationSharedModule } from 'app/shared';
import {
  InspectionComponent,
  InspectionDetailComponent,
  InspectionUpdateComponent,
  InspectionDeletePopupComponent,
  InspectionDeleteDialogComponent,
  inspectionRoute,
  inspectionPopupRoute
} from './';

const ENTITY_STATES = [...inspectionRoute, ...inspectionPopupRoute];

@NgModule({
  imports: [IsuzuSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InspectionComponent,
    InspectionDetailComponent,
    InspectionUpdateComponent,
    InspectionDeleteDialogComponent,
    InspectionDeletePopupComponent
  ],
  entryComponents: [InspectionComponent, InspectionUpdateComponent, InspectionDeleteDialogComponent, InspectionDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationInspectionModule {}
