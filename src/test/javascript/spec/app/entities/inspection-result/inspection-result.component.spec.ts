/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionResultComponent } from 'app/entities/inspection-result/inspection-result.component';
import { InspectionResultService } from 'app/entities/inspection-result/inspection-result.service';
import { InspectionResult } from 'app/shared/model/inspection-result.model';

describe('Component Tests', () => {
  describe('InspectionResult Management Component', () => {
    let comp: InspectionResultComponent;
    let fixture: ComponentFixture<InspectionResultComponent>;
    let service: InspectionResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionResultComponent],
        providers: []
      })
        .overrideTemplate(InspectionResultComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectionResultComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionResultService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InspectionResult(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inspectionResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
