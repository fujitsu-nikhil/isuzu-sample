/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionIdComponent } from 'app/entities/inspection-id/inspection-id.component';
import { InspectionIdService } from 'app/entities/inspection-id/inspection-id.service';
import { InspectionId } from 'app/shared/model/inspection-id.model';

describe('Component Tests', () => {
  describe('InspectionId Management Component', () => {
    let comp: InspectionIdComponent;
    let fixture: ComponentFixture<InspectionIdComponent>;
    let service: InspectionIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionIdComponent],
        providers: []
      })
        .overrideTemplate(InspectionIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectionIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InspectionId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inspectionIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
