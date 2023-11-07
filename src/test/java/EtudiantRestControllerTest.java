import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.kaddem.controllers.EtudiantRestController;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EtudiantRestControllerTest {

    @InjectMocks
    private EtudiantRestController etudiantController;

    @Mock
    private IEtudiantService etudiantService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantController).build();
    }

    @Test
    public void testGetEtudiants() throws Exception {

        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("meriem","meriem", Option.SE));
        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieve-all-etudiants"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(greaterThan(0)));
        assertNotNull(result);

    }


    @Test
    public void testAddEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant("meriem","meriem", Option.SE);
        when(etudiantService.addEtudiant(any())).thenReturn(etudiant);

       ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/etudiant/add-etudiant")
                        .content(asJsonString(etudiant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(greaterThan(0)));

        assertNotNull(result);

    }

    @Test
    public void testRetrieveEtudiant() throws Exception {
        int etudiantId = 1;
        Etudiant etudiant = new Etudiant("meriem", "meriem", Option.SE);
        when(etudiantService.retrieveEtudiant(etudiantId)).thenReturn(etudiant);

        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieve-etudiant/{etudiant-id}", etudiantId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRemoveEtudiant() throws Exception {
        int etudiantId = 1;
        doNothing().when(etudiantService).removeEtudiant(etudiantId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/etudiant/remove-etudiant/{etudiant-id}", etudiantId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant("meriem", "meriem", Option.SE);
        when(etudiantService.updateEtudiant(any())).thenReturn(etudiant);

        mockMvc.perform(MockMvcRequestBuilders.put("/etudiant/update-etudiant")
                        .content(asJsonString(etudiant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAffecterEtudiantToDepartement() throws Exception {
        int etudiantId = 1;
        int departementId = 2;
        doNothing().when(etudiantService).assignEtudiantToDepartement(etudiantId, departementId);

        mockMvc.perform(MockMvcRequestBuilders.put("/etudiant/affecter-etudiant-departement/{etudiantId}/{departementId}", etudiantId, departementId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testAddEtudiantWithEquipeAndContract() throws Exception {
        Etudiant etudiant = new Etudiant("meriem", "meriem", Option.SE);
        int idContrat = 1;
        int idEquipe = 2;
        when(etudiantService.addAndAssignEtudiantToEquipeAndContract(any(), anyInt(), anyInt())).thenReturn(etudiant);

        mockMvc.perform(MockMvcRequestBuilders.post("/etudiant/add-assign-Etudiant/{idContrat}/{idEquipe}", idContrat, idEquipe)
                        .content(asJsonString(etudiant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetEtudiantsParDepartement() throws Exception {
        int idDepartement = 1;
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("meriem", "meriem", Option.SE));
        when(etudiantService.getEtudiantsByDepartement(idDepartement)).thenReturn(etudiants);

        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/getEtudiantsByDepartement/{idDepartement}", idDepartement))
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
