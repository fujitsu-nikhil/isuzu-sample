import { Moment } from 'moment';

export interface IDataId {
  id?: number;
  dataId?: string;
  dataName?: string;
  sortNumber?: string;
  updatedAt?: Moment;
}

export class DataId implements IDataId {
  constructor(
    public id?: number,
    public dataId?: string,
    public dataName?: string,
    public sortNumber?: string,
    public updatedAt?: Moment
  ) {}
}
