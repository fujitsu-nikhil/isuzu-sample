import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataId } from 'app/shared/model/data-id.model';

type EntityResponseType = HttpResponse<IDataId>;
type EntityArrayResponseType = HttpResponse<IDataId[]>;

@Injectable({ providedIn: 'root' })
export class DataIdService {
  public resourceUrl = SERVER_API_URL + 'api/data-ids';

  constructor(protected http: HttpClient) {}

  create(dataId: IDataId): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataId);
    return this.http
      .post<IDataId>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dataId: IDataId): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataId);
    return this.http
      .put<IDataId>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDataId>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDataId[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dataId: IDataId): IDataId {
    const copy: IDataId = Object.assign({}, dataId, {
      updatedAt: dataId.updatedAt != null && dataId.updatedAt.isValid() ? dataId.updatedAt.toJSON() : null
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
      res.body.forEach((dataId: IDataId) => {
        dataId.updatedAt = dataId.updatedAt != null ? moment(dataId.updatedAt) : null;
      });
    }
    return res;
  }
}
