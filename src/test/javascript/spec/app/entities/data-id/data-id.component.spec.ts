/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { DataIdComponent } from 'app/entities/data-id/data-id.component';
import { DataIdService } from 'app/entities/data-id/data-id.service';
import { DataId } from 'app/shared/model/data-id.model';

describe('Component Tests', () => {
  describe('DataId Management Component', () => {
    let comp: DataIdComponent;
    let fixture: ComponentFixture<DataIdComponent>;
    let service: DataIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [DataIdComponent],
        providers: []
      })
        .overrideTemplate(DataIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DataId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dataIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
