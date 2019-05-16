/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { DataIdUpdateComponent } from 'app/entities/data-id/data-id-update.component';
import { DataIdService } from 'app/entities/data-id/data-id.service';
import { DataId } from 'app/shared/model/data-id.model';

describe('Component Tests', () => {
  describe('DataId Management Update Component', () => {
    let comp: DataIdUpdateComponent;
    let fixture: ComponentFixture<DataIdUpdateComponent>;
    let service: DataIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [DataIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataId(123);
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
        const entity = new DataId();
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
