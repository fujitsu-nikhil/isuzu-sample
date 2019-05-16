import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IData } from 'app/shared/model/data.model';

type EntityResponseType = HttpResponse<IData>;
type EntityArrayResponseType = HttpResponse<IData[]>;

@Injectable({ providedIn: 'root' })
export class DataService {
  public resourceUrl = SERVER_API_URL + 'api/data';

  constructor(protected http: HttpClient) {}

  create(data: IData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(data);
    return this.http
      .post<IData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(data: IData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(data);
    return this.http
      .put<IData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(data: IData): IData {
    const copy: IData = Object.assign({}, data, {
      createdAt: data.createdAt != null && data.createdAt.isValid() ? data.createdAt.toJSON() : null,
      updatedAt: data.updatedAt != null && data.updatedAt.isValid() ? data.updatedAt.toJSON() : null
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
      res.body.forEach((data: IData) => {
        data.createdAt = data.createdAt != null ? moment(data.createdAt) : null;
        data.updatedAt = data.updatedAt != null ? moment(data.updatedAt) : null;
      });
    }
    return res;
  }
}
