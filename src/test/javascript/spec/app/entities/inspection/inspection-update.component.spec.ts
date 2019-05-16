/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionUpdateComponent } from 'app/entities/inspection/inspection-update.component';
import { InspectionService } from 'app/entities/inspection/inspection.service';
import { Inspection } from 'app/shared/model/inspection.model';

describe('Component Tests', () => {
  describe('Inspection Management Update Component', () => {
    let comp: InspectionUpdateComponent;
    let fixture: ComponentFixture<InspectionUpdateComponent>;
    let service: InspectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InspectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inspection(123);
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
        const entity = new Inspection();
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
