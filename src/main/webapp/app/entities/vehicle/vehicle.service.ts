import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVehicle } from 'app/shared/model/vehicle.model';

type EntityResponseType = HttpResponse<IVehicle>;
type EntityArrayResponseType = HttpResponse<IVehicle[]>;

@Injectable({ providedIn: 'root' })
export class VehicleService {
  public resourceUrl = SERVER_API_URL + 'api/vehicles';

  constructor(protected http: HttpClient) {}

  create(vehicle: IVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicle);
    return this.http
      .post<IVehicle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicle: IVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicle);
    return this.http
      .put<IVehicle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vehicle: IVehicle): IVehicle {
    const copy: IVehicle = Object.assign({}, vehicle, {
      overallJudgmentAt:
        vehicle.overallJudgmentAt != null && vehicle.overallJudgmentAt.isValid() ? vehicle.overallJudgmentAt.toJSON() : null,
      modelYear: vehicle.modelYear != null && vehicle.modelYear.isValid() ? vehicle.modelYear.toJSON() : null,
      estimatedProductionDate:
        vehicle.estimatedProductionDate != null && vehicle.estimatedProductionDate.isValid()
          ? vehicle.estimatedProductionDate.format(DATE_FORMAT)
          : null,
      updatedAt: vehicle.updatedAt != null && vehicle.updatedAt.isValid() ? vehicle.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.overallJudgmentAt = res.body.overallJudgmentAt != null ? moment(res.body.overallJudgmentAt) : null;
      res.body.modelYear = res.body.modelYear != null ? moment(res.body.modelYear) : null;
      res.body.estimatedProductionDate = res.body.estimatedProductionDate != null ? moment(res.body.estimatedProductionDate) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicle: IVehicle) => {
        vehicle.overallJudgmentAt = vehicle.overallJudgmentAt != null ? moment(vehicle.overallJudgmentAt) : null;
        vehicle.modelYear = vehicle.modelYear != null ? moment(vehicle.modelYear) : null;
        vehicle.estimatedProductionDate = vehicle.estimatedProductionDate != null ? moment(vehicle.estimatedProductionDate) : null;
        vehicle.updatedAt = vehicle.updatedAt != null ? moment(vehicle.updatedAt) : null;
      });
    }
    return res;
  }
}
