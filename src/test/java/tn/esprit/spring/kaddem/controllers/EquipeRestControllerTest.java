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
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.services.IEquipeService;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EquipeRestControllerTest {

    @InjectMocks
    private EquipeRestController equipeRestController;

    @Mock
    private IEquipeService equipeService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(equipeRestController).build();
    }

    @Test
    public void testGetEtudiants() throws Exception {

        List<Equipe> equipes= new ArrayList<>();
        equipes.add(new Equipe("Chess", Niveau.JUNIOR));
        when(equipeService.retrieveAllEquipes()).thenReturn(equipes);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/equipe/retrieve-all-equipes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(greaterThan(0)));
        assertNotNull(result);

    }

    @Test
    public void testRetrieveEquipe() throws Exception {
        int equipeId = 1;
        Equipe equipe = new Equipe("Chess", Niveau.JUNIOR);

        when(equipeService.retrieveEquipe(equipeId)).thenReturn(equipe);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/equipe/retrieve-equipe/{equipe-id}", equipeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        assertNotNull(result);
    }

    @Test
    public void testAddEquipe() throws Exception {
        Equipe equipe = new Equipe("Chess", Niveau.JUNIOR);

       // when(equipeService.addEquipe(equipe)).thenReturn(equipe);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/equipe/add-equipe")
                        .content(asJsonString(equipe))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipe)));

        assertNotNull(result);
    }

    @Test
    public void testRemoveEquipe() throws Exception {
        int equipeId = 1;

        doNothing().when(equipeService).deleteEquipe(equipeId);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/equipe/remove-equipe/{equipe-id}", equipeId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertNotNull(result);
    }

    @Test
    public void testUpdateEquipe() throws Exception {
        Equipe equipe = new Equipe("Chess", Niveau.JUNIOR);

      //  when(equipeService.updateEquipe(equipe)).thenReturn(equipe);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/equipe/update-equipe")
                        .content(asJsonString(equipe))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipe)));

        assertNotNull(result);
    }

    @Test
    public void testFaireEvoluerEquipes() throws Exception {
        doNothing().when(equipeService).evoluerEquipes();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/equipe/faireEvoluerEquipes"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertNotNull(result);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
