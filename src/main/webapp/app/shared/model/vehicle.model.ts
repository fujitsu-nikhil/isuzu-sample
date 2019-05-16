import { Moment } from 'moment';

export interface IVehicle {
  id?: number;
  vehicleIdNumber?: string;
  overallJudgment?: string;
  overallJudgmentAt?: Moment;
  modelYear?: Moment;
  modelCode?: string;
  lotNumber?: string;
  unitNumber?: string;
  estimatedProductionDate?: Moment;
  updatedAt?: Moment;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public vehicleIdNumber?: string,
    public overallJudgment?: string,
    public overallJudgmentAt?: Moment,
    public modelYear?: Moment,
    public modelCode?: string,
    public lotNumber?: string,
    public unitNumber?: string,
    public estimatedProductionDate?: Moment,
    public updatedAt?: Moment
  ) {}
}
