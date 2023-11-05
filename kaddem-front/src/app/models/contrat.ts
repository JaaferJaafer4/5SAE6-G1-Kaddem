import { Etudiant } from "./etudiant";

export class Contrat{
    idContrat! : number;
    dateDebutContrat! : Date;
    dateFinContrat! : Date;
    specialite! : string;
    archive! : boolean;
    montantContrat ! : number;
    etudiant! : Etudiant;
}