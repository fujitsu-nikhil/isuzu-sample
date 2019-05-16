import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IData } from 'app/shared/model/data.model';

@Component({
  selector: 'jhi-data-detail',
  templateUrl: './data-detail.component.html'
})
export class DataDetailComponent implements OnInit {
  data: IData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ data }) => {
      this.data = data;
    });
  }

  previousState() {
    window.history.back();
  }
}
