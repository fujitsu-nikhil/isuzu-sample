import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataId } from 'app/shared/model/data-id.model';

@Component({
  selector: 'jhi-data-id-detail',
  templateUrl: './data-id-detail.component.html'
})
export class DataIdDetailComponent implements OnInit {
  dataId: IDataId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataId }) => {
      this.dataId = dataId;
    });
  }

  previousState() {
    window.history.back();
  }
}
