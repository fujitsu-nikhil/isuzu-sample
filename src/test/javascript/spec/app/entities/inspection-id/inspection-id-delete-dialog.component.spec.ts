/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionIdDeleteDialogComponent } from 'app/entities/inspection-id/inspection-id-delete-dialog.component';
import { InspectionIdService } from 'app/entities/inspection-id/inspection-id.service';

describe('Component Tests', () => {
  describe('InspectionId Management Delete Component', () => {
    let comp: InspectionIdDeleteDialogComponent;
    let fixture: ComponentFixture<InspectionIdDeleteDialogComponent>;
    let service: InspectionIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionIdDeleteDialogComponent]
      })
        .overrideTemplate(InspectionIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectionIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionIdService);
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
