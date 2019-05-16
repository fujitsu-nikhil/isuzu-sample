import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IInspectionId, InspectionId } from 'app/shared/model/inspection-id.model';
import { InspectionIdService } from './inspection-id.service';

@Component({
  selector: 'jhi-inspection-id-update',
  templateUrl: './inspection-id-update.component.html'
})
export class InspectionIdUpdateComponent implements OnInit {
  inspectionId: IInspectionId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    inspectionId: [null, []],
    inspectionFlag: [],
    inspectionName: [],
    sortNumber: [],
    updatedAt: []
  });

  constructor(protected inspectionIdService: InspectionIdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inspectionId }) => {
      this.updateForm(inspectionId);
      this.inspectionId = inspectionId;
    });
  }

  updateForm(inspectionId: IInspectionId) {
    this.editForm.patchValue({
      id: inspectionId.id,
      inspectionId: inspectionId.inspectionId,
      inspectionFlag: inspectionId.inspectionFlag,
      inspectionName: inspectionId.inspectionName,
      sortNumber: inspectionId.sortNumber,
      updatedAt: inspectionId.updatedAt != null ? inspectionId.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inspectionId = this.createFromForm();
    if (inspectionId.id !== undefined) {
      this.subscribeToSaveResponse(this.inspectionIdService.update(inspectionId));
    } else {
      this.subscribeToSaveResponse(this.inspectionIdService.create(inspectionId));
    }
  }

  private createFromForm(): IInspectionId {
    const entity = {
      ...new InspectionId(),
      id: this.editForm.get(['id']).value,
      inspectionId: this.editForm.get(['inspectionId']).value,
      inspectionFlag: this.editForm.get(['inspectionFlag']).value,
      inspectionName: this.editForm.get(['inspectionName']).value,
      sortNumber: this.editForm.get(['sortNumber']).value,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspectionId>>) {
    result.subscribe((res: HttpResponse<IInspectionId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
