/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';
import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';

describe('Service Tests', () => {
  describe('Vehicle Service', () => {
    let injector: TestBed;
    let service: VehicleService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicle;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(VehicleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vehicle(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            overallJudgmentAt: currentDate.format(DATE_TIME_FORMAT),
            modelYear: currentDate.format(DATE_TIME_FORMAT),
            estimatedProductionDate: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Vehicle', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            overallJudgmentAt: currentDate.format(DATE_TIME_FORMAT),
            modelYear: currentDate.format(DATE_TIME_FORMAT),
            estimatedProductionDate: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            overallJudgmentAt: currentDate,
            modelYear: currentDate,
            estimatedProductionDate: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );
        service
          .create(new Vehicle(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Vehicle', async () => {
        const returnedFromService = Object.assign(
          {
            vehicleIdNumber: 'BBBBBB',
            overallJudgment: 'BBBBBB',
            overallJudgmentAt: currentDate.format(DATE_TIME_FORMAT),
            modelYear: currentDate.format(DATE_TIME_FORMAT),
            modelCode: 'BBBBBB',
            lotNumber: 'BBBBBB',
            unitNumber: 'BBBBBB',
            estimatedProductionDate: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            overallJudgmentAt: currentDate,
            modelYear: currentDate,
            estimatedProductionDate: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Vehicle', async () => {
        const returnedFromService = Object.assign(
          {
            vehicleIdNumber: 'BBBBBB',
            overallJudgment: 'BBBBBB',
            overallJudgmentAt: currentDate.format(DATE_TIME_FORMAT),
            modelYear: currentDate.format(DATE_TIME_FORMAT),
            modelCode: 'BBBBBB',
            lotNumber: 'BBBBBB',
            unitNumber: 'BBBBBB',
            estimatedProductionDate: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            overallJudgmentAt: currentDate,
            modelYear: currentDate,
            estimatedProductionDate: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vehicle', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
