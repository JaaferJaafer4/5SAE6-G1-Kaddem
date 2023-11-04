package tn.esprit.spring.kaddem.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import static org.mockito.Mockito.times;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ContratServiceImplTest {
    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllContrats() {
        List<Contrat> contrats = new ArrayList<>();
        contrats.add( new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,false,7850));
        Mockito.when(contratRepository.findAll()).thenReturn(contrats);

        List<Contrat> result = contratService.retrieveAllContrats();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateContrat() {
        Contrat contrat = new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,false,7850);
        Mockito.when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.updateContrat(contrat);
        assertNotNull(result);
    }

    @Test
    public void testAddContrat() {
        Contrat contrat = new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,false,7850);
        Mockito.when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);
        assertNotNull(result);
    }

    @Test
    public void testRetrieveContrat() {
        Integer idContrat = 1;
        Contrat contrat = new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,false,7850);
        Mockito.when(contratRepository.findById(idContrat)).thenReturn(java.util.Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(idContrat);
        assertNotNull(result);
    }

    @Test
    public void testRemoveContrat() {
        Integer idContrat = 1;
        Contrat contrat = new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,false,7850);
        Mockito.when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));
        Mockito.doNothing().when(contratRepository).delete(contrat);
        contratService.removeContrat(idContrat);
        Mockito.verify(contratRepository, times(1)).delete(contrat);
    }


    @Test
    public void testAffectContratToEtudiant() {
        Integer idContrat = 1;
        String nomE = "Jaafar";
        String prenomE = "Jaafar";
        Contrat contrat = new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,true,7850);
        Etudiant etudiant = new Etudiant(nomE, prenomE);
        Set<Contrat> contrats = new HashSet<>();
        contrats.add(contrat);
        etudiant.setContrats(contrats);


        Mockito.when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);
        Mockito.when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        Mockito.when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);
        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
    }

   @Test
    public void testNbContratsValides() {
        Date startDate = new Date(2018,1,1);
        Date endDate = new Date(2021,1,1);

        Mockito.when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(3);

        int result = contratService.nbContratsValides(startDate, endDate);
        assertEquals(3, result);
    }

    @Test
    public void testRetrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = new ArrayList<>();

        Contrat contrat1 = new Contrat(
                new Date(2018,6,15),new Date(), Specialite.IA,false,7850);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        Date dateMinus15Days = calendar.getTime();
        Contrat contrat2 = new Contrat(
                new Date(2018,6,15),dateMinus15Days, Specialite.IA,false,7850);

        contrats.add(contrat1);
        contrats.add(contrat2);


        Mockito.when(contratRepository.findAll()).thenReturn(contrats);
        Mockito.when(contratRepository.save(contrat1)).thenReturn(contrat1);
        contratService.retrieveAndUpdateStatusContrat();

        Mockito.verify(contratRepository, Mockito.atLeastOnce()).save(contrat1);

    }

    @Test
    public void testGetChiffreAffaireEntreDeuxDates() {
        Date startDate = new Date(2018,1,1);
        Date endDate = new Date(2018,6,1);


        List<Contrat> contrats = getContrats();

        Mockito.when(contratRepository.findAll()).thenReturn(contrats);

        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);
       assertEquals(7500, result, 0.01);
    }

    private static List<Contrat> getContrats() {
        List<Contrat> contrats = new ArrayList<>();
        Contrat contrat1 = new Contrat(
                new Date(2018,6,15),new Date(), Specialite.IA,false,7850);
        Contrat contrat2 = new Contrat(
                new Date(2018,6,15),new Date(), Specialite.CLOUD,false,7850);
        Contrat contrat3 = new Contrat(
                new Date(2018,6,15),new Date(), Specialite.RESEAUX,false,7850);
        Contrat contrat4 = new Contrat(
                new Date(2018,6,15),new Date(), Specialite.SECURITE,false,7850);

        contrats.add(contrat1);
        contrats.add(contrat2);
        contrats.add(contrat3);
        contrats.add(contrat4);
        return contrats;
    }

}
