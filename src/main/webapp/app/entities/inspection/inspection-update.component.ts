import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IInspection, Inspection } from 'app/shared/model/inspection.model';
import { InspectionService } from './inspection.service';

@Component({
  selector: 'jhi-inspection-update',
  templateUrl: './inspection-update.component.html'
})
export class InspectionUpdateComponent implements OnInit {
  inspection: IInspection;
  isSaving: boolean;
  estimatedProductionDateStartDp: any;
  estimatedProductionDateEndDp: any;

  editForm = this.fb.group({
    id: [],
    modelYear: [null, [Validators.required, Validators.maxLength(3)]],
    modelCode: [null, [Validators.required, Validators.maxLength(5)]],
    lotStart: [null, [Validators.required, Validators.maxLength(3)]],
    unitStart: [null, [Validators.required, Validators.maxLength(3)]],
    lotEnd: [null, [Validators.required, Validators.maxLength(3)]],
    unitEnd: [null, [Validators.required, Validators.maxLength(3)]],
    estimatedProductionDateStart: [null, [Validators.required]],
    estimatedProductionDateEnd: [null, [Validators.required]],
    inspectionId: [null, [Validators.maxLength(15)]],
    systemId: [null, []],
    pattern: [],
    patternDivisionNumber: [null, [Validators.maxLength(1)]],
    patternDivisionNumberTotal: [null, [Validators.maxLength(1)]]
  });

  constructor(protected inspectionService: InspectionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inspection }) => {
      this.updateForm(inspection);
      this.inspection = inspection;
    });
  }

  updateForm(inspection: IInspection) {
    this.editForm.patchValue({
      id: inspection.id,
      modelYear: inspection.modelYear,
      modelCode: inspection.modelCode,
      lotStart: inspection.lotStart,
      unitStart: inspection.unitStart,
      lotEnd: inspection.lotEnd,
      unitEnd: inspection.unitEnd,
      estimatedProductionDateStart: inspection.estimatedProductionDateStart,
      estimatedProductionDateEnd: inspection.estimatedProductionDateEnd,
      inspectionId: inspection.inspectionId,
      systemId: inspection.systemId,
      pattern: inspection.pattern,
      patternDivisionNumber: inspection.patternDivisionNumber,
      patternDivisionNumberTotal: inspection.patternDivisionNumberTotal
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inspection = this.createFromForm();
    if (inspection.id !== undefined) {
      this.subscribeToSaveResponse(this.inspectionService.update(inspection));
    } else {
      this.subscribeToSaveResponse(this.inspectionService.create(inspection));
    }
  }

  private createFromForm(): IInspection {
    const entity = {
      ...new Inspection(),
      id: this.editForm.get(['id']).value,
      modelYear: this.editForm.get(['modelYear']).value,
      modelCode: this.editForm.get(['modelCode']).value,
      lotStart: this.editForm.get(['lotStart']).value,
      unitStart: this.editForm.get(['unitStart']).value,
      lotEnd: this.editForm.get(['lotEnd']).value,
      unitEnd: this.editForm.get(['unitEnd']).value,
      estimatedProductionDateStart: this.editForm.get(['estimatedProductionDateStart']).value,
      estimatedProductionDateEnd: this.editForm.get(['estimatedProductionDateEnd']).value,
      inspectionId: this.editForm.get(['inspectionId']).value,
      systemId: this.editForm.get(['systemId']).value,
      pattern: this.editForm.get(['pattern']).value,
      patternDivisionNumber: this.editForm.get(['patternDivisionNumber']).value,
      patternDivisionNumberTotal: this.editForm.get(['patternDivisionNumberTotal']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspection>>) {
    result.subscribe((res: HttpResponse<IInspection>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
