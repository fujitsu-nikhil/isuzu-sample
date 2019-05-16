import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IInspectionResult, InspectionResult } from 'app/shared/model/inspection-result.model';
import { InspectionResultService } from './inspection-result.service';

@Component({
  selector: 'jhi-inspection-result-update',
  templateUrl: './inspection-result-update.component.html'
})
export class InspectionResultUpdateComponent implements OnInit {
  inspectionResult: IInspectionResult;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    vehicleIdNumber: [],
    inspectionId: [],
    systemId: [],
    pattern1: [],
    judgment: [],
    createdAt: [],
    partsNumber: [],
    detail: [],
    updatedAt: []
  });

  constructor(
    protected inspectionResultService: InspectionResultService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inspectionResult }) => {
      this.updateForm(inspectionResult);
      this.inspectionResult = inspectionResult;
    });
  }

  updateForm(inspectionResult: IInspectionResult) {
    this.editForm.patchValue({
      id: inspectionResult.id,
      vehicleIdNumber: inspectionResult.vehicleIdNumber,
      inspectionId: inspectionResult.inspectionId,
      systemId: inspectionResult.systemId,
      pattern1: inspectionResult.pattern1,
      judgment: inspectionResult.judgment,
      createdAt: inspectionResult.createdAt != null ? inspectionResult.createdAt.format(DATE_TIME_FORMAT) : null,
      partsNumber: inspectionResult.partsNumber,
      detail: inspectionResult.detail,
      updatedAt: inspectionResult.updatedAt != null ? inspectionResult.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inspectionResult = this.createFromForm();
    if (inspectionResult.id !== undefined) {
      this.subscribeToSaveResponse(this.inspectionResultService.update(inspectionResult));
    } else {
      this.subscribeToSaveResponse(this.inspectionResultService.create(inspectionResult));
    }
  }

  private createFromForm(): IInspectionResult {
    const entity = {
      ...new InspectionResult(),
      id: this.editForm.get(['id']).value,
      vehicleIdNumber: this.editForm.get(['vehicleIdNumber']).value,
      inspectionId: this.editForm.get(['inspectionId']).value,
      systemId: this.editForm.get(['systemId']).value,
      pattern1: this.editForm.get(['pattern1']).value,
      judgment: this.editForm.get(['judgment']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      partsNumber: this.editForm.get(['partsNumber']).value,
      detail: this.editForm.get(['detail']).value,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspectionResult>>) {
    result.subscribe((res: HttpResponse<IInspectionResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
