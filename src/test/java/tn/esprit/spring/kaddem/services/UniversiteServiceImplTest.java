package tn.esprit.spring.kaddem.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UniversiteServiceImplTest {
    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;


    @Test
    public void testRetrieveAllUniversite() {

        List<Universite> universites = new ArrayList<>();
        universites.add(new Universite(1,"esprit"));

        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertNotNull(result);
        assertEquals(universites, result);

    }

    @Test
    public void testAddUniversite() {
        Universite universite = new Universite(1,"esprit");
        when(universiteRepository.save(universite)).thenReturn(universite);
        Universite result = universiteService.addUniversite(universite);
        assertNotNull(result);
        assertEquals(universite, result);
    }

    @Test
    public void testUpdateUniversite() {
        Universite universite = new Universite(1,"esprit");
        when(universiteRepository.save(universite)).thenReturn(universite);
        Universite result = universiteService.updateUniversite(universite);
        assertNotNull(result);
        assertEquals(universite, result);
    }

    @Test
    public void testRetrieveUniversite() {
        int id = 1;
        Universite universite = new Universite(1,"esprit");
        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));
        Universite result = universiteService.retrieveUniversite(id);
        assertNotNull(result);
        assertEquals(universite, result);
    }

    @Test
    public void testRemoveUniversite() {
        int id = 1;
        Universite universite = new Universite(1,"esprit");
        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));
        universiteService.deleteUniversite(id);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    public void testAssignUniversiteToDepartement() {
        int universiteId = 1;
        int departementId = 2;
        Universite universite = new Universite(1,"esprit");
        Departement departement = new Departement("Esprit");

        when(universiteRepository.findById(universiteId)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        universiteService.assignUniversiteToDepartement(universiteId, departementId);
        assertEquals(departement, universite.getDepartements());
    }




}
