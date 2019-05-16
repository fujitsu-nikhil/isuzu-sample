/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionResultUpdateComponent } from 'app/entities/inspection-result/inspection-result-update.component';
import { InspectionResultService } from 'app/entities/inspection-result/inspection-result.service';
import { InspectionResult } from 'app/shared/model/inspection-result.model';

describe('Component Tests', () => {
  describe('InspectionResult Management Update Component', () => {
    let comp: InspectionResultUpdateComponent;
    let fixture: ComponentFixture<InspectionResultUpdateComponent>;
    let service: InspectionResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionResultUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InspectionResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectionResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InspectionResult(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new InspectionResult();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
