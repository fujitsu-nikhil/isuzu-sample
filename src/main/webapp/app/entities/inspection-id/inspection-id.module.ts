import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IsuzuSampleApplicationSharedModule } from 'app/shared';
import {
  InspectionIdComponent,
  InspectionIdDetailComponent,
  InspectionIdUpdateComponent,
  InspectionIdDeletePopupComponent,
  InspectionIdDeleteDialogComponent,
  inspectionIdRoute,
  inspectionIdPopupRoute
} from './';

const ENTITY_STATES = [...inspectionIdRoute, ...inspectionIdPopupRoute];

@NgModule({
  imports: [IsuzuSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InspectionIdComponent,
    InspectionIdDetailComponent,
    InspectionIdUpdateComponent,
    InspectionIdDeleteDialogComponent,
    InspectionIdDeletePopupComponent
  ],
  entryComponents: [
    InspectionIdComponent,
    InspectionIdUpdateComponent,
    InspectionIdDeleteDialogComponent,
    InspectionIdDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationInspectionIdModule {}
