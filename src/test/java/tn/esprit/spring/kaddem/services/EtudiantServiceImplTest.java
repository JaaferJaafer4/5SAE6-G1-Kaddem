package tn.esprit.spring.kaddem.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EtudiantServiceImplTest {
    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;


    @Test
    public void testRetrieveAllEtudiants() {

        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("jaafar","jaafar", Option.SE));

        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertNotNull(result);
        assertEquals(etudiants, result);

    }

    @Test
    public void testAddEtudiant() {
        Etudiant etudiant = new Etudiant("jaafar","jaafar", Option.SE);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant result = etudiantService.addEtudiant(etudiant);
        assertNotNull(result);
        assertEquals(etudiant, result);
    }

    @Test
    public void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant("jaafar","jaafar", Option.SE);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant result = etudiantService.updateEtudiant(etudiant);
        assertNotNull(result);
        assertEquals(etudiant, result);
    }

    @Test
    public void testRetrieveEtudiant() {
        int id = 1;
        Etudiant etudiant = new Etudiant("jaafar","jaafar", Option.SE);
        when(etudiantRepository.findById(id)).thenReturn(Optional.of(etudiant));
        Etudiant result = etudiantService.retrieveEtudiant(id);
        assertNotNull(result);
        assertEquals(etudiant, result);
    }

    @Test
    public void testRemoveEtudiant() {
        int id = 1;
        Etudiant etudiant = new Etudiant("jaafar","jaafar", Option.SE);
        when(etudiantRepository.findById(id)).thenReturn(Optional.of(etudiant));
        etudiantService.removeEtudiant(id);
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    public void testAssignEtudiantToDepartement() {
        int etudiantId = 1;
        int departementId = 2;
        Etudiant etudiant = new Etudiant("jaafar","jaafar", Option.SE);
        Departement departement = new Departement("Esprit");

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        etudiantService.assignEtudiantToDepartement(etudiantId, departementId);
        assertEquals(departement, etudiant.getDepartement());
    }

    @Test
    public void testAddAndAssignEtudiantToEquipeAndContract() {
       int idContrat = 1;
        int idEquipe = 1;
        Etudiant etudiant = new Etudiant("jaafar", "jaafar", Option.SE);
     Contrat contrat = new Contrat( new Date(2021, 10, 15), new Date(2023, 6, 15), Specialite.IA, true, 8000);
      Equipe equipe = new Equipe("Chess", Niveau.SENIOR);

    when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));
     when(equipeRepository.findById(idEquipe)).thenReturn(Optional.of(equipe));
      when(contratRepository.save(contrat)).thenReturn(contrat);
      when(equipeRepository.save(equipe)).thenReturn(equipe);

        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, idContrat, idEquipe);

        assertNotNull(result);

        assertEquals(etudiant, contrat.getEtudiant());

        assertNotNull(equipe);

     //   assertTrue(equipe.getEtudiants().contains(etudiant));
    }


    @Test
    public void testGetEtudiantsByDepartement() {
        int idDepartement = 1;
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("jaafar","jaafar", Option.SE));
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(idDepartement)).thenReturn(etudiants);
        List<Etudiant> result = etudiantService.getEtudiantsByDepartement(idDepartement);
        assertNotNull(result);
        assertEquals(etudiants, result);
    }
}
