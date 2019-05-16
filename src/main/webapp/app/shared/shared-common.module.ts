import { NgModule } from '@angular/core';

import { IsuzuSampleApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [IsuzuSampleApplicationSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [IsuzuSampleApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class IsuzuSampleApplicationSharedCommonModule {}
