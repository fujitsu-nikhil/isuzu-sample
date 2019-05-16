import { Moment } from 'moment';

export interface IInspection {
  id?: number;
  modelYear?: string;
  modelCode?: string;
  lotStart?: string;
  unitStart?: string;
  lotEnd?: string;
  unitEnd?: string;
  estimatedProductionDateStart?: Moment;
  estimatedProductionDateEnd?: Moment;
  inspectionId?: string;
  systemId?: string;
  pattern?: string;
  patternDivisionNumber?: string;
  patternDivisionNumberTotal?: string;
}

export class Inspection implements IInspection {
  constructor(
    public id?: number,
    public modelYear?: string,
    public modelCode?: string,
    public lotStart?: string,
    public unitStart?: string,
    public lotEnd?: string,
    public unitEnd?: string,
    public estimatedProductionDateStart?: Moment,
    public estimatedProductionDateEnd?: Moment,
    public inspectionId?: string,
    public systemId?: string,
    public pattern?: string,
    public patternDivisionNumber?: string,
    public patternDivisionNumberTotal?: string
  ) {}
}
