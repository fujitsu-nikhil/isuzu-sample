import { Moment } from 'moment';

export interface IInspectionResult {
  id?: number;
  vehicleIdNumber?: string;
  inspectionId?: string;
  systemId?: string;
  pattern1?: string;
  judgment?: string;
  createdAt?: Moment;
  partsNumber?: string;
  detail?: string;
  updatedAt?: Moment;
}

export class InspectionResult implements IInspectionResult {
  constructor(
    public id?: number,
    public vehicleIdNumber?: string,
    public inspectionId?: string,
    public systemId?: string,
    public pattern1?: string,
    public judgment?: string,
    public createdAt?: Moment,
    public partsNumber?: string,
    public detail?: string,
    public updatedAt?: Moment
  ) {}
}
