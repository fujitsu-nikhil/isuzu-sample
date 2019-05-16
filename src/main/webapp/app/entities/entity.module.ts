import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vehicle',
        loadChildren: './vehicle/vehicle.module#IsuzuSampleApplicationVehicleModule'
      },
      {
        path: 'inspection',
        loadChildren: './inspection/inspection.module#IsuzuSampleApplicationInspectionModule'
      },
      {
        path: 'inspection-result',
        loadChildren: './inspection-result/inspection-result.module#IsuzuSampleApplicationInspectionResultModule'
      },
      {
        path: 'inspection-id',
        loadChildren: './inspection-id/inspection-id.module#IsuzuSampleApplicationInspectionIdModule'
      },
      {
        path: 'data',
        loadChildren: './data/data.module#IsuzuSampleApplicationDataModule'
      },
      {
        path: 'data-id',
        loadChildren: './data-id/data-id.module#IsuzuSampleApplicationDataIdModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IsuzuSampleApplicationEntityModule {}
