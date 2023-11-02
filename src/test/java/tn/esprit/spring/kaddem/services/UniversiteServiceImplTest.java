package tn.esprit.spring.kaddem.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UniversiteServiceImplTest {

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Test
    public void testRetrieveAllUniversites() {

        List<Universite> sampleUniversites = new ArrayList<>();
        when(universiteRepository.findAll()).thenReturn(sampleUniversites);

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertEquals(sampleUniversites, result);
    }

    @Test
    public void testAddUniversite() {

        Universite sampleUniversite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(sampleUniversite);

        Universite result = universiteService.addUniversite(new Universite());

        assertEquals(sampleUniversite, result);
    }


    @Test
    public void testAssignUniversiteToDepartement() {

        Universite sampleUniversite = new Universite();
        Departement sampleDepartement = new Departement();
        when(universiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(sampleUniversite));
        when(departementRepository.findById(any(Integer.class))).thenReturn(Optional.of(sampleDepartement));


        universiteService.assignUniversiteToDepartement(1, 2);


        assertTrue(sampleUniversite.getDepartements().contains(sampleDepartement));
        verify(universiteRepository, times(1)).save(sampleUniversite);
    }

    @Test
    public void testUpdateUniversite() {

        Universite sampleUniversite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(sampleUniversite);


        Universite result = universiteService.updateUniversite(new Universite());

        assertEquals(sampleUniversite, result);
    }

    @Test
    public void testRetrieveUniversite() {

        Universite sampleUniversite = new Universite();
        when(universiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(sampleUniversite));


        Universite result = universiteService.retrieveUniversite(1);


        assertEquals(sampleUniversite, result);
    }

    @Test
    public void testDeleteUniversite() {

        Universite sampleUniversite = new Universite();
        when(universiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(sampleUniversite));


        universiteService.deleteUniversite(1);

        verify(universiteRepository, times(1)).delete(sampleUniversite);
    }

    @Test
    public void testRetrieveDepartementsByUniversite() {

        Universite sampleUniversite = new Universite();
        Departement departement1 = new Departement();
        Departement departement2 = new Departement();
        sampleUniversite.setDepartements(new HashSet<>());
        sampleUniversite.getDepartements().add(departement1);
        sampleUniversite.getDepartements().add(departement2);
        when(universiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(sampleUniversite));

        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(1);


        assertEquals(2, result.size());
        assertTrue(result.contains(departement1));
        assertTrue(result.contains(departement2));
    }
}