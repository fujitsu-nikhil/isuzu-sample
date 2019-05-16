/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionDeleteDialogComponent } from 'app/entities/inspection/inspection-delete-dialog.component';
import { InspectionService } from 'app/entities/inspection/inspection.service';

describe('Component Tests', () => {
  describe('Inspection Management Delete Component', () => {
    let comp: InspectionDeleteDialogComponent;
    let fixture: ComponentFixture<InspectionDeleteDialogComponent>;
    let service: InspectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionDeleteDialogComponent]
      })
        .overrideTemplate(InspectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
