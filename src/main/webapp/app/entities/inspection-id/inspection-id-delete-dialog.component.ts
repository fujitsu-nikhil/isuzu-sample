import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInspectionId } from 'app/shared/model/inspection-id.model';
import { InspectionIdService } from './inspection-id.service';

@Component({
  selector: 'jhi-inspection-id-delete-dialog',
  templateUrl: './inspection-id-delete-dialog.component.html'
})
export class InspectionIdDeleteDialogComponent {
  inspectionId: IInspectionId;

  constructor(
    protected inspectionIdService: InspectionIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inspectionIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inspectionIdListModification',
        content: 'Deleted an inspectionId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inspection-id-delete-popup',
  template: ''
})
export class InspectionIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspectionId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InspectionIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inspectionId = inspectionId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inspection-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inspection-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
