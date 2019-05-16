/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { InspectionResultDeleteDialogComponent } from 'app/entities/inspection-result/inspection-result-delete-dialog.component';
import { InspectionResultService } from 'app/entities/inspection-result/inspection-result.service';

describe('Component Tests', () => {
  describe('InspectionResult Management Delete Component', () => {
    let comp: InspectionResultDeleteDialogComponent;
    let fixture: ComponentFixture<InspectionResultDeleteDialogComponent>;
    let service: InspectionResultService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [InspectionResultDeleteDialogComponent]
      })
        .overrideTemplate(InspectionResultDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectionResultDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectionResultService);
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
