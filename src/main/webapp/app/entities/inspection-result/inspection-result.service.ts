import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInspectionResult } from 'app/shared/model/inspection-result.model';

type EntityResponseType = HttpResponse<IInspectionResult>;
type EntityArrayResponseType = HttpResponse<IInspectionResult[]>;

@Injectable({ providedIn: 'root' })
export class InspectionResultService {
  public resourceUrl = SERVER_API_URL + 'api/inspection-results';

  constructor(protected http: HttpClient) {}

  create(inspectionResult: IInspectionResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionResult);
    return this.http
      .post<IInspectionResult>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inspectionResult: IInspectionResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionResult);
    return this.http
      .put<IInspectionResult>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInspectionResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInspectionResult[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inspectionResult: IInspectionResult): IInspectionResult {
    const copy: IInspectionResult = Object.assign({}, inspectionResult, {
      createdAt: inspectionResult.createdAt != null && inspectionResult.createdAt.isValid() ? inspectionResult.createdAt.toJSON() : null,
      updatedAt: inspectionResult.updatedAt != null && inspectionResult.updatedAt.isValid() ? inspectionResult.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inspectionResult: IInspectionResult) => {
        inspectionResult.createdAt = inspectionResult.createdAt != null ? moment(inspectionResult.createdAt) : null;
        inspectionResult.updatedAt = inspectionResult.updatedAt != null ? moment(inspectionResult.updatedAt) : null;
      });
    }
    return res;
  }
}
