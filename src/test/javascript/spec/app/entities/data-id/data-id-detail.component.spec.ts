/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsuzuSampleApplicationTestModule } from '../../../test.module';
import { DataIdDetailComponent } from 'app/entities/data-id/data-id-detail.component';
import { DataId } from 'app/shared/model/data-id.model';

describe('Component Tests', () => {
  describe('DataId Management Detail Component', () => {
    let comp: DataIdDetailComponent;
    let fixture: ComponentFixture<DataIdDetailComponent>;
    const route = ({ data: of({ dataId: new DataId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IsuzuSampleApplicationTestModule],
        declarations: [DataIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
