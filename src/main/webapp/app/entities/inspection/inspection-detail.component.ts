import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInspection } from 'app/shared/model/inspection.model';

@Component({
  selector: 'jhi-inspection-detail',
  templateUrl: './inspection-detail.component.html'
})
export class InspectionDetailComponent implements OnInit {
  inspection: IInspection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspection }) => {
      this.inspection = inspection;
    });
  }

  previousState() {
    window.history.back();
  }
}
