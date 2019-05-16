/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionIdUpdateComponent } from 'app/entities/inspection-id/inspection-id-update.component';
import { InspectionIdService } from 'app/entities/inspection-id/inspection-id.service';
import { InspectionId } from 'app/shared/model/inspection-id.model';

describe('Component Tests', () => {
  describe('InspectionId Management Update Component', () => {
    let comp: InspectionIdUpdateComponent;
    let fixture: ComponentFixture<InspectionIdUpdateComponent>;
    let service: InspectionIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InspectionIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectionIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InspectionId(123);
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
        const entity = new InspectionId();
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
