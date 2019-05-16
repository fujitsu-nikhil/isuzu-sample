import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataId } from 'app/shared/model/data-id.model';
import { DataIdService } from './data-id.service';

@Component({
  selector: 'jhi-data-id-delete-dialog',
  templateUrl: './data-id-delete-dialog.component.html'
})
export class DataIdDeleteDialogComponent {
  dataId: IDataId;

  constructor(protected dataIdService: DataIdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataIdListModification',
        content: 'Deleted an dataId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-id-delete-popup',
  template: ''
})
export class DataIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataId = dataId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-id', { outlets: { popup: null } }]);
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
