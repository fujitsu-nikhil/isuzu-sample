import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInspection } from 'app/shared/model/inspection.model';

type EntityResponseType = HttpResponse<IInspection>;
type EntityArrayResponseType = HttpResponse<IInspection[]>;

@Injectable({ providedIn: 'root' })
export class InspectionService {
  public resourceUrl = SERVER_API_URL + 'api/inspections';

  constructor(protected http: HttpClient) {}

  create(inspection: IInspection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspection);
    return this.http
      .post<IInspection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inspection: IInspection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspection);
    return this.http
      .put<IInspection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInspection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInspection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inspection: IInspection): IInspection {
    const copy: IInspection = Object.assign({}, inspection, {
      estimatedProductionDateStart:
        inspection.estimatedProductionDateStart != null && inspection.estimatedProductionDateStart.isValid()
          ? inspection.estimatedProductionDateStart.format(DATE_FORMAT)
          : null,
      estimatedProductionDateEnd:
        inspection.estimatedProductionDateEnd != null && inspection.estimatedProductionDateEnd.isValid()
          ? inspection.estimatedProductionDateEnd.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.estimatedProductionDateStart =
        res.body.estimatedProductionDateStart != null ? moment(res.body.estimatedProductionDateStart) : null;
      res.body.estimatedProductionDateEnd =
        res.body.estimatedProductionDateEnd != null ? moment(res.body.estimatedProductionDateEnd) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inspection: IInspection) => {
        inspection.estimatedProductionDateStart =
          inspection.estimatedProductionDateStart != null ? moment(inspection.estimatedProductionDateStart) : null;
        inspection.estimatedProductionDateEnd =
          inspection.estimatedProductionDateEnd != null ? moment(inspection.estimatedProductionDateEnd) : null;
      });
    }
    return res;
  }
}
