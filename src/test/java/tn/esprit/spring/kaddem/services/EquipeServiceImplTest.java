package tn.esprit.spring.kaddem.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EquipeServiceImplTest {
        @InjectMocks
        private EquipeServiceImpl equipeService;

        @Mock
        private EquipeRepository equipeRepository;

        @Test
        public void testRetrieveAllEquipes() {

            List<Equipe> equipes = new ArrayList<>();
            equipes.add(new Equipe("chess",Niveau.SENIOR));

            Mockito.when(equipeRepository.findAll()).thenReturn(equipes);

            List<Equipe> result = equipeService.retrieveAllEquipes();

            Assert.assertEquals(equipes, result);
        }

        @Test
        public void testAddEquipe() {
            Equipe equipe = new Equipe("chess",Niveau.EXPERT);

            Mockito.when(equipeRepository.save(Mockito.any(Equipe.class))).thenReturn(equipe);

            Equipe result = equipeService.addEquipe(equipe);

            Assert.assertEquals(equipe, result);
        }

    @Test
    public void testDeleteEquipe() {

        Integer equipeId = 1;
        Equipe sampleEquipe = new Equipe("Chess",Niveau.JUNIOR);
        Mockito.when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(sampleEquipe));

        equipeService.deleteEquipe(equipeId);

        Mockito.verify(equipeRepository).delete(sampleEquipe);
    }


    @Test
    public void testRetrieveEquipe() {

        Integer equipeId = 1;
        Equipe equipe = new Equipe("chess",Niveau.JUNIOR);
        Mockito.when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));
        Equipe result = equipeService.retrieveEquipe(equipeId);

        Mockito.verify(equipeRepository).findById(equipeId);
        Assert.assertEquals(equipe,result);
    }

    @Test
    public void testUpdateEquipe() {

        Equipe equipe = new Equipe("Chess",Niveau.JUNIOR);

        Mockito.when(equipeRepository.save(equipe)).thenReturn(equipe);
        Equipe result = equipeService.updateEquipe(equipe);


        Mockito.verify(equipeRepository).save(equipe);
        Assert.assertEquals(result,equipe);
    }
    @Test
    public void testEvoluerEquipes() {

        Equipe equipe = new Equipe(
                "chess",Niveau.JUNIOR);

        Etudiant etudiant1 = new Etudiant(
                "Jaafar","Jaafar", Option.SE);

        Etudiant etudiant2 = new Etudiant(
                "Mazen","Slama", Option.SIM);

        Etudiant etudiant3 = new Etudiant(
                "Zied","Ben Amor", Option.NIDS);

        Contrat contrat1 = new Contrat(
                new Date(2018,6,15),
                new Date(2021,7,15),
                Specialite.IA,false,8000);

        Contrat contrat2 = new Contrat(
                new Date(2019,6,15),
                new Date(2022,2,20),
                Specialite.CLOUD,false,8850);


        Contrat contrat3 = new Contrat(
                new Date(2017,6,15),
                new Date(2020,8,20),
                Specialite.RESEAUX,false,7700);


        etudiant1.setContrats(new HashSet<>());
        etudiant1.getContrats().add(contrat1);

        etudiant2.setContrats(new HashSet<>());
        etudiant2.getContrats().add(contrat2);

        etudiant3.setContrats(new HashSet<>());
        etudiant3.getContrats().add(contrat3);



        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiant1);
        etudiants.add(etudiant2);
        etudiants.add(etudiant3);




        equipe.setEtudiants(etudiants);
        List<Equipe> equipes = new ArrayList<>();
        equipes.add(equipe);

        Mockito.when(equipeRepository.findAll()).thenReturn(equipes);

      Mockito.when(equipeRepository.save(Mockito.any(Equipe.class))).then(AdditionalAnswers.returnsFirstArg());

        equipeService.evoluerEquipes();

       Assert.assertEquals(Niveau.SENIOR, equipe.getNiveau());
    }

}




