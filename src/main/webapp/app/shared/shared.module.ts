import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {
  IsuzuSampleApplicationSharedLibsModule,
  IsuzuSampleApplicationSharedCommonModule,
  JhiLoginModalComponent,
  HasAnyAuthorityDirective
} from './';

@NgModule({
  imports: [IsuzuSampleApplicationSharedLibsModule, IsuzuSampleApplicationSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [IsuzuSampleApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationSharedModule {
  static forRoot() {
    return {
      ngModule: IsuzuSampleApplicationSharedModule
    };
  }
}
