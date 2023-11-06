import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
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
  contrat_update : Contrat = new Contrat();
  nbValides = 0;
  nbAff = 0;
  constructor(private _service : ContratService,private modalService: NgbModal) { }

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
    this.contrat = new Contrat();
  }

  openModal(content : any,idContrat : number) {
    this._service.retrieveContrat(idContrat).subscribe(
      data => this.contrat_update = data
    );
		this.modalService.open(content, { size: 'lg' });
	}

  deleteContrat(idContrat : number){
    this._service.removeContrat(idContrat).subscribe(
      () => this.list_contrat = this.list_contrat.filter(item => item.idContrat != idContrat)
    )
  }

  updateContrat(event: any) {
    this.contrat_update.archive = event.value;
    this._service.updateContrat(this.contrat_update).subscribe(
      (data) => {
        this.list_contrat = this.list_contrat.map(contract => {
          if (contract.idContrat === data.idContrat) {
            return data;
          }
          return contract;
        });
        this.modalService.dismissAll();
      }
    )
  }
  

  openValidesModal(nbvalides : any)
  {
    this.modalService.open(nbvalides);
  }

  nbvalidesContrats(startva : any,endva : any) {
    let startDate = startva.value;
    let endDate = endva.value;
    this._service.getnbContratsValides(startDate,endDate).subscribe(
      data => this.nbValides = data
    )
  }

  openAffModal(affmod : any){
    this.modalService.open(affmod);

  }


  nbAffContrats(startva : any,endva : any)
  {
    let startDate = startva.value;
    let endDate = endva.value;
    this._service.calculChiffreAffaireEntreDeuxDate(startDate,endDate).subscribe(
      data => this.nbAff = data
    )
  }
}
