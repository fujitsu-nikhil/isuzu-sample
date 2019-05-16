import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IsuzuSampleApplicationSharedModule } from 'app/shared';
import {
  DataComponent,
  DataDetailComponent,
  DataUpdateComponent,
  DataDeletePopupComponent,
  DataDeleteDialogComponent,
  dataRoute,
  dataPopupRoute
} from './';

const ENTITY_STATES = [...dataRoute, ...dataPopupRoute];

@NgModule({
  imports: [IsuzuSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DataComponent, DataDetailComponent, DataUpdateComponent, DataDeleteDialogComponent, DataDeletePopupComponent],
  entryComponents: [DataComponent, DataUpdateComponent, DataDeleteDialogComponent, DataDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationDataModule {}
