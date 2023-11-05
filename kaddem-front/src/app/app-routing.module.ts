import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContratComponent } from './components/contrat/contrat.component';

const routes: Routes = [
  {path:'contrat',component:ContratComponent},
  {path:'',redirectTo:'/contrat',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
