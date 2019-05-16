import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IData, Data } from 'app/shared/model/data.model';
import { DataService } from './data.service';

@Component({
  selector: 'jhi-data-update',
  templateUrl: './data-update.component.html'
})
export class DataUpdateComponent implements OnInit {
  data: IData;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    vehicleIdNumber: [],
    dataId: [],
    detail: [],
    createdAt: [],
    updatedAt: []
  });

  constructor(protected dataService: DataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ data }) => {
      this.updateForm(data);
      this.data = data;
    });
  }

  updateForm(data: IData) {
    this.editForm.patchValue({
      id: data.id,
      vehicleIdNumber: data.vehicleIdNumber,
      dataId: data.dataId,
      detail: data.detail,
      createdAt: data.createdAt != null ? data.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: data.updatedAt != null ? data.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const data = this.createFromForm();
    if (data.id !== undefined) {
      this.subscribeToSaveResponse(this.dataService.update(data));
    } else {
      this.subscribeToSaveResponse(this.dataService.create(data));
    }
  }

  private createFromForm(): IData {
    const entity = {
      ...new Data(),
      id: this.editForm.get(['id']).value,
      vehicleIdNumber: this.editForm.get(['vehicleIdNumber']).value,
      dataId: this.editForm.get(['dataId']).value,
      detail: this.editForm.get(['detail']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IData>>) {
    result.subscribe((res: HttpResponse<IData>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
