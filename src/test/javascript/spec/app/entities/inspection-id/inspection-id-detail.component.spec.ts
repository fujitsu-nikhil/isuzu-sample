/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionIdDetailComponent } from 'app/entities/inspection-id/inspection-id-detail.component';
import { InspectionId } from 'app/shared/model/inspection-id.model';

describe('Component Tests', () => {
  describe('InspectionId Management Detail Component', () => {
    let comp: InspectionIdDetailComponent;
    let fixture: ComponentFixture<InspectionIdDetailComponent>;
    const route = ({ data: of({ inspectionId: new InspectionId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InspectionIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectionIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inspectionId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
