import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IsuzuSampleApplicationSharedModule } from 'app/shared';
import {
  InspectionResultComponent,
  InspectionResultDetailComponent,
  InspectionResultUpdateComponent,
  InspectionResultDeletePopupComponent,
  InspectionResultDeleteDialogComponent,
  inspectionResultRoute,
  inspectionResultPopupRoute
} from './';

const ENTITY_STATES = [...inspectionResultRoute, ...inspectionResultPopupRoute];

@NgModule({
  imports: [IsuzuSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InspectionResultComponent,
    InspectionResultDetailComponent,
    InspectionResultUpdateComponent,
    InspectionResultDeleteDialogComponent,
    InspectionResultDeletePopupComponent
  ],
  entryComponents: [
    InspectionResultComponent,
    InspectionResultUpdateComponent,
    InspectionResultDeleteDialogComponent,
    InspectionResultDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationInspectionResultModule {}
