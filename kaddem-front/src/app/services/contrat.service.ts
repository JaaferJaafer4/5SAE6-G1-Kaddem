import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Contrat } from '../models/contrat';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContratService {

  url ="http://http://192.168.56.20:8089/kaddem/contrat/";
  constructor(private http:HttpClient) { }


  getContrats() : Observable<Contrat[]>{
    return this.http.get<[Contrat]>(this.url+"retrieve-all-contrats");
  }

  retrieveContrat(idContrat : number) : Observable<Contrat>{
    return this.http.get<Contrat>(this.url+"retrieve-contrat/"+idContrat);
  }
  

  addContrat(contrat : Contrat) : Observable<Contrat>{
    return this.http.post<Contrat>(this.url+"add-contrat",contrat);
  }

  removeContrat(idContrat : number) : Observable<any>{
    return this.http.delete(this.url+"remove-contrat/"+idContrat);
  }

  updateContrat(contrat : Contrat) : Observable<Contrat>{
    return this.http.put<Contrat>(this.url+"update-contrat",contrat);
  }

  assignContratToEtudiant(idContrat : number,nomE : string,prenomE : string) : Observable<Contrat>{
    return this.http.get<Contrat>(this.url+"assignContratToEtudiant/"+idContrat+"/"+nomE+"/"+prenomE);
  }

  getnbContratsValides(startDate : Date,endDate : Date) : Observable<number>{
    return this.http.get<number>(this.url+"getnbContratsValides/"+startDate+"/"+endDate);
  }

  calculChiffreAffaireEntreDeuxDate(startDate : Date,endDate : Date) : Observable<number>{
    return this.http.get<number>(this.url+"calculChiffreAffaireEntreDeuxDate/"+startDate+"/"+endDate);
  }

  majStatusContrat() : Observable<any>{
    return this.http.put(this.url+"majStatusContrat",null);
  }
}
