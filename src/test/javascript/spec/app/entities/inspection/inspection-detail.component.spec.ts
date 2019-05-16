/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionDetailComponent } from 'app/entities/inspection/inspection-detail.component';
import { Inspection } from 'app/shared/model/inspection.model';

describe('Component Tests', () => {
  describe('Inspection Management Detail Component', () => {
    let comp: InspectionDetailComponent;
    let fixture: ComponentFixture<InspectionDetailComponent>;
    const route = ({ data: of({ inspection: new Inspection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InspectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inspection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
