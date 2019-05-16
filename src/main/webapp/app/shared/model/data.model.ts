import { Moment } from 'moment';

export interface IData {
  id?: number;
  vehicleIdNumber?: string;
  dataId?: string;
  detail?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class Data implements IData {
  constructor(
    public id?: number,
    public vehicleIdNumber?: string,
    public dataId?: string,
    public detail?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment
  ) {}
}
