package tn.esprit.spring.kaddem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.services.IContratService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ContratRestControllerTest {

    @InjectMocks
    private ContratRestController contratRestController;

    @Mock
    private IContratService contratService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contratRestController).build();
    }

    @Test
    public void testGetContrats() throws Exception {
        List<Contrat> contrats = new ArrayList<>();
        contrats.add(new Contrat(
                new Date(2018,6,15),new Date(2021,8,30), Specialite.IA,false,7850));
        Mockito.when(contratService.retrieveAllContrats()).thenReturn(contrats);

        mockMvc.perform(MockMvcRequestBuilders.get("/contrat/retrieve-all-contrats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testRetrieveContrat() throws Exception {
        Contrat contrat = new Contrat(
                new Date(2018, 6, 15), new Date(2021, 8, 30), Specialite.CLOUD, false, 7850);
        Mockito.when(contratService.retrieveContrat(1)).thenReturn(contrat);

        mockMvc.perform(MockMvcRequestBuilders.get("/contrat/retrieve-contrat/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialite").value("CLOUD"));
    }

    @Test
    public void testAddContrat() throws Exception {
        Contrat contrat = new Contrat(
                new Date(2018, 6, 15), new Date(2021, 8, 30), Specialite.SECURITE, false, 7850);
       // Mockito.when(contratService.addContrat(contrat)).thenReturn(contrat);

        mockMvc.perform(MockMvcRequestBuilders.post("/contrat/add-contrat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contrat)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRemoveContrat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/contrat/remove-contrat/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateContrat() throws Exception {
        Contrat contrat = new Contrat(
                new Date(2018, 6, 15), new Date(2021, 8, 30), Specialite.RESEAUX, false, 7850);
      //  Mockito.when(contratService.updateContrat(contrat)).thenReturn(contrat);

        mockMvc.perform(MockMvcRequestBuilders.put("/contrat/update-contrat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contrat)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAssignContratToEtudiant() throws Exception {

        Integer idContrat = 1;
        String nomE = "Jaafar";
        String prenomE = "Jaafar";
      //  Contrat contrat1 = new Contrat(new Date(), new Date(), Specialite.IA, false, 958236);
       // Mockito.when(contratService.affectContratToEtudiant(idContrat, nomE, prenomE)).thenReturn(contrat);

        mockMvc.perform(MockMvcRequestBuilders.put("/contrat/assignContratToEtudiant/{idContrat}/{nomE}/{prenomE}", idContrat, nomE, prenomE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetnbContratsValides() throws Exception {

        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalDate endDate = LocalDate.of(2004, 1, 1);

        String startDateString = startDate.format(DateTimeFormatter.ISO_DATE);
        String endDateString = endDate.format(DateTimeFormatter.ISO_DATE);

      //  Integer nbContratsValides = 5;


        mockMvc.perform(MockMvcRequestBuilders.get("/contrat/getnbContratsValides/{startDate}/{endDate}", startDateString, endDateString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testMajStatusContrat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contrat/majStatusContrat"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCalculChiffreAffaireEntreDeuxDates() throws Exception {
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalDate endDate = LocalDate.of(2004, 1, 1);

        String startDateString = startDate.format(DateTimeFormatter.ISO_DATE);
        String endDateString = endDate.format(DateTimeFormatter.ISO_DATE);

       // float chiffreAffaire = 12345.67f;
     //   Mockito.when(contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate)).thenReturn(chiffreAffaire);

        mockMvc.perform(MockMvcRequestBuilders.get("/contrat/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}",startDateString,endDateString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

