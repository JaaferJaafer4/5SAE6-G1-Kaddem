import { Component, OnInit } from '@angular/core';
import { Contrat } from 'src/app/models/contrat';
import { ContratService } from 'src/app/services/contrat.service';

@Component({
  selector: 'app-contrat',
  templateUrl: './contrat.component.html',
  styleUrls: ['./contrat.component.css']
})
export class ContratComponent implements OnInit {
  list_contrat!: Contrat[];
  contrat : Contrat = new Contrat();
  constructor(private _service : ContratService) { }

  ngOnInit(): void {
    this._service.getContrats().subscribe(
      data => this.list_contrat = data
    )
  }


  addContrat(event : any)
  {
    this.contrat.archive = event.value;
    this._service.addContrat(this.contrat).subscribe(
      (data) => this.list_contrat.push(data)
    )
  }
}
