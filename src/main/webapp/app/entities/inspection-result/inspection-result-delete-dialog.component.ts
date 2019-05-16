import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInspectionResult } from 'app/shared/model/inspection-result.model';
import { InspectionResultService } from './inspection-result.service';

@Component({
  selector: 'jhi-inspection-result-delete-dialog',
  templateUrl: './inspection-result-delete-dialog.component.html'
})
export class InspectionResultDeleteDialogComponent {
  inspectionResult: IInspectionResult;

  constructor(
    protected inspectionResultService: InspectionResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inspectionResultService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inspectionResultListModification',
        content: 'Deleted an inspectionResult'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inspection-result-delete-popup',
  template: ''
})
export class InspectionResultDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspectionResult }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InspectionResultDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inspectionResult = inspectionResult;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inspection-result', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inspection-result', { outlets: { popup: null } }]);
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
