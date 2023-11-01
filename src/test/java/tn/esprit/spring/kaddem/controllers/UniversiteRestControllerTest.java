package tn.esprit.spring.kaddem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.services.IEtudiantService;
import tn.esprit.spring.kaddem.services.IUniversiteService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UniversiteRestControllerTest {

    @InjectMocks
    private UniversiteRestController universiteRestController;

    @Mock
    private IUniversiteService universiteService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(universiteRestController).build();
    }

    @Test
    public void testGetUniversites() throws Exception {

        List<Universite> universites = new ArrayList<>();
        universites.add(new Universite(1,"esprit"));
        when(universiteService.retrieveAllUniversites()).thenReturn(universites);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/universite/retrieve-all-universites"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(greaterThan(0)));
        assertNotNull(result);

    }


    @Test
    public void testAddUniversite() throws Exception {
        Universite universite = new Universite(1,"esprit");
        when(universiteService.addUniversite(any())).thenReturn(universite);

       ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/universite/add-universite")
                        .content(asJsonString(universite))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(greaterThan(0)));

        assertNotNull(result);

    }

    @Test
    public void testRetrieveUniversite() throws Exception {
        int universiteId = 1;
        Universite universite = new Universite(1, "esprit");
        when(universiteService.retrieveUniversite(universiteId)).thenReturn(universite);

        mockMvc.perform(MockMvcRequestBuilders.get("/universite/retrieve-universite/{universite-id}", universiteId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRemoveUniversite() throws Exception {
        int universiteId = 1;
        doNothing().when(universiteService).deleteUniversite(universiteId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/universite/remove-universite/{universite-id}", universiteId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateUniversite() throws Exception {
        Universite universite = new Universite(1, "esprit");
        when(universiteService.updateUniversite(any())).thenReturn(universite);

        mockMvc.perform(MockMvcRequestBuilders.put("/universite/update-universite")
                        .content(asJsonString(universite))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAffecterUniversiteToDepartement() throws Exception {
        int universiteId = 1;
        int departementId = 2;
        doNothing().when(universiteService).assignUniversiteToDepartement(universiteId, departementId);

        mockMvc.perform(MockMvcRequestBuilders.put("/universite/affecter-universite-departement/{universiteId}/{departementId}", universiteId, departementId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
