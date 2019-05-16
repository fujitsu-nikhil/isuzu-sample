import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IsuzuSampleApplicationSharedModule } from 'app/shared';
import {
  DataIdComponent,
  DataIdDetailComponent,
  DataIdUpdateComponent,
  DataIdDeletePopupComponent,
  DataIdDeleteDialogComponent,
  dataIdRoute,
  dataIdPopupRoute
} from './';

const ENTITY_STATES = [...dataIdRoute, ...dataIdPopupRoute];

@NgModule({
  imports: [IsuzuSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DataIdComponent, DataIdDetailComponent, DataIdUpdateComponent, DataIdDeleteDialogComponent, DataIdDeletePopupComponent],
  entryComponents: [DataIdComponent, DataIdUpdateComponent, DataIdDeleteDialogComponent, DataIdDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationDataIdModule {}
