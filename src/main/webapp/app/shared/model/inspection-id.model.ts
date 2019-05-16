import { Moment } from 'moment';

export interface IInspectionId {
  id?: number;
  inspectionId?: string;
  inspectionFlag?: string;
  inspectionName?: string;
  sortNumber?: string;
  updatedAt?: Moment;
}

export class InspectionId implements IInspectionId {
  constructor(
    public id?: number,
    public inspectionId?: string,
    public inspectionFlag?: string,
    public inspectionName?: string,
    public sortNumber?: string,
    public updatedAt?: Moment
  ) {}
}
