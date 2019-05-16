import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInspection } from 'app/shared/model/inspection.model';
import { InspectionService } from './inspection.service';

@Component({
  selector: 'jhi-inspection-delete-dialog',
  templateUrl: './inspection-delete-dialog.component.html'
})
export class InspectionDeleteDialogComponent {
  inspection: IInspection;

  constructor(
    protected inspectionService: InspectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inspectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inspectionListModification',
        content: 'Deleted an inspection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inspection-delete-popup',
  template: ''
})
export class InspectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InspectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inspection = inspection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inspection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inspection', { outlets: { popup: null } }]);
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
