import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html'
})
export class VehicleUpdateComponent implements OnInit {
  vehicle: IVehicle;
  isSaving: boolean;
  estimatedProductionDateDp: any;

  editForm = this.fb.group({
    id: [],
    vehicleIdNumber: [null, [Validators.required]],
    overallJudgment: [],
    overallJudgmentAt: [],
    modelYear: [],
    modelCode: [],
    lotNumber: [],
    unitNumber: [],
    estimatedProductionDate: [],
    updatedAt: []
  });

  constructor(protected vehicleService: VehicleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);
      this.vehicle = vehicle;
    });
  }

  updateForm(vehicle: IVehicle) {
    this.editForm.patchValue({
      id: vehicle.id,
      vehicleIdNumber: vehicle.vehicleIdNumber,
      overallJudgment: vehicle.overallJudgment,
      overallJudgmentAt: vehicle.overallJudgmentAt != null ? vehicle.overallJudgmentAt.format(DATE_TIME_FORMAT) : null,
      modelYear: vehicle.modelYear != null ? vehicle.modelYear.format(DATE_TIME_FORMAT) : null,
      modelCode: vehicle.modelCode,
      lotNumber: vehicle.lotNumber,
      unitNumber: vehicle.unitNumber,
      estimatedProductionDate: vehicle.estimatedProductionDate,
      updatedAt: vehicle.updatedAt != null ? vehicle.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  private createFromForm(): IVehicle {
    const entity = {
      ...new Vehicle(),
      id: this.editForm.get(['id']).value,
      vehicleIdNumber: this.editForm.get(['vehicleIdNumber']).value,
      overallJudgment: this.editForm.get(['overallJudgment']).value,
      overallJudgmentAt:
        this.editForm.get(['overallJudgmentAt']).value != null
          ? moment(this.editForm.get(['overallJudgmentAt']).value, DATE_TIME_FORMAT)
          : undefined,
      modelYear:
        this.editForm.get(['modelYear']).value != null ? moment(this.editForm.get(['modelYear']).value, DATE_TIME_FORMAT) : undefined,
      modelCode: this.editForm.get(['modelCode']).value,
      lotNumber: this.editForm.get(['lotNumber']).value,
      unitNumber: this.editForm.get(['unitNumber']).value,
      estimatedProductionDate: this.editForm.get(['estimatedProductionDate']).value,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>) {
    result.subscribe((res: HttpResponse<IVehicle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
