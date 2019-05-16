import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IDataId, DataId } from 'app/shared/model/data-id.model';
import { DataIdService } from './data-id.service';

@Component({
  selector: 'jhi-data-id-update',
  templateUrl: './data-id-update.component.html'
})
export class DataIdUpdateComponent implements OnInit {
  dataId: IDataId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    dataId: [null, []],
    dataName: [],
    sortNumber: [],
    updatedAt: []
  });

  constructor(protected dataIdService: DataIdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataId }) => {
      this.updateForm(dataId);
      this.dataId = dataId;
    });
  }

  updateForm(dataId: IDataId) {
    this.editForm.patchValue({
      id: dataId.id,
      dataId: dataId.dataId,
      dataName: dataId.dataName,
      sortNumber: dataId.sortNumber,
      updatedAt: dataId.updatedAt != null ? dataId.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataId = this.createFromForm();
    if (dataId.id !== undefined) {
      this.subscribeToSaveResponse(this.dataIdService.update(dataId));
    } else {
      this.subscribeToSaveResponse(this.dataIdService.create(dataId));
    }
  }

  private createFromForm(): IDataId {
    const entity = {
      ...new DataId(),
      id: this.editForm.get(['id']).value,
      dataId: this.editForm.get(['dataId']).value,
      dataName: this.editForm.get(['dataName']).value,
      sortNumber: this.editForm.get(['sortNumber']).value,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataId>>) {
    result.subscribe((res: HttpResponse<IDataId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
