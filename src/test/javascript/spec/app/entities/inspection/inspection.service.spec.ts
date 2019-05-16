/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InspectionService } from 'app/entities/inspection/inspection.service';
import { IInspection, Inspection } from 'app/shared/model/inspection.model';

describe('Service Tests', () => {
  describe('Inspection Service', () => {
    let injector: TestBed;
    let service: InspectionService;
    let httpMock: HttpTestingController;
    let elemDefault: IInspection;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InspectionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Inspection(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            estimatedProductionDateStart: currentDate.format(DATE_FORMAT),
            estimatedProductionDateEnd: currentDate.format(DATE_FORMAT)
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

      it('should create a Inspection', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            estimatedProductionDateStart: currentDate.format(DATE_FORMAT),
            estimatedProductionDateEnd: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            estimatedProductionDateStart: currentDate,
            estimatedProductionDateEnd: currentDate
          },
          returnedFromService
        );
        service
          .create(new Inspection(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Inspection', async () => {
        const returnedFromService = Object.assign(
          {
            modelYear: 'BBBBBB',
            modelCode: 'BBBBBB',
            lotStart: 'BBBBBB',
            unitStart: 'BBBBBB',
            lotEnd: 'BBBBBB',
            unitEnd: 'BBBBBB',
            estimatedProductionDateStart: currentDate.format(DATE_FORMAT),
            estimatedProductionDateEnd: currentDate.format(DATE_FORMAT),
            inspectionId: 'BBBBBB',
            systemId: 'BBBBBB',
            pattern: 'BBBBBB',
            patternDivisionNumber: 'BBBBBB',
            patternDivisionNumberTotal: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedProductionDateStart: currentDate,
            estimatedProductionDateEnd: currentDate
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

      it('should return a list of Inspection', async () => {
        const returnedFromService = Object.assign(
          {
            modelYear: 'BBBBBB',
            modelCode: 'BBBBBB',
            lotStart: 'BBBBBB',
            unitStart: 'BBBBBB',
            lotEnd: 'BBBBBB',
            unitEnd: 'BBBBBB',
            estimatedProductionDateStart: currentDate.format(DATE_FORMAT),
            estimatedProductionDateEnd: currentDate.format(DATE_FORMAT),
            inspectionId: 'BBBBBB',
            systemId: 'BBBBBB',
            pattern: 'BBBBBB',
            patternDivisionNumber: 'BBBBBB',
            patternDivisionNumberTotal: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            estimatedProductionDateStart: currentDate,
            estimatedProductionDateEnd: currentDate
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

      it('should delete a Inspection', async () => {
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
