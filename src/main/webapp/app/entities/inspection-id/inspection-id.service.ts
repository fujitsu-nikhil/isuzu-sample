import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInspectionId } from 'app/shared/model/inspection-id.model';

type EntityResponseType = HttpResponse<IInspectionId>;
type EntityArrayResponseType = HttpResponse<IInspectionId[]>;

@Injectable({ providedIn: 'root' })
export class InspectionIdService {
  public resourceUrl = SERVER_API_URL + 'api/inspection-ids';

  constructor(protected http: HttpClient) {}

  create(inspectionId: IInspectionId): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionId);
    return this.http
      .post<IInspectionId>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inspectionId: IInspectionId): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionId);
    return this.http
      .put<IInspectionId>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInspectionId>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInspectionId[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inspectionId: IInspectionId): IInspectionId {
    const copy: IInspectionId = Object.assign({}, inspectionId, {
      updatedAt: inspectionId.updatedAt != null && inspectionId.updatedAt.isValid() ? inspectionId.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inspectionId: IInspectionId) => {
        inspectionId.updatedAt = inspectionId.updatedAt != null ? moment(inspectionId.updatedAt) : null;
      });
    }
    return res;
  }
}
