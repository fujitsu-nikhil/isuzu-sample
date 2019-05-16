import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInspectionId } from 'app/shared/model/inspection-id.model';

@Component({
  selector: 'jhi-inspection-id-detail',
  templateUrl: './inspection-id-detail.component.html'
})
export class InspectionIdDetailComponent implements OnInit {
  inspectionId: IInspectionId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspectionId }) => {
      this.inspectionId = inspectionId;
    });
  }

  previousState() {
    window.history.back();
  }
}
