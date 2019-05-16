/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionComponent } from 'app/entities/inspection/inspection.component';
import { InspectionService } from 'app/entities/inspection/inspection.service';
import { Inspection } from 'app/shared/model/inspection.model';

describe('Component Tests', () => {
  describe('Inspection Management Component', () => {
    let comp: InspectionComponent;
    let fixture: ComponentFixture<InspectionComponent>;
    let service: InspectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionComponent],
        providers: []
      })
        .overrideTemplate(InspectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Inspection(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inspections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
