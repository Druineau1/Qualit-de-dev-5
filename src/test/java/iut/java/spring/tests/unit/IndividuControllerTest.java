package iut.java.spring.tests.unit;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import iut.java.spring.controller.IndividuController;
import iut.java.spring.service.interfaces.IIndividuService;

@ExtendWith(MockitoExtension.class)
public class IndividuControllerTest {

    @Mock
    private IIndividuService individuServiceMock;

    @InjectMocks
    private IndividuController individuController;

    @Test
    public void testRemove() throws Exception {
    	// ID de l'individu à supprimer et définition du comportement du mock 
        long idToRemove = 1L;
        doNothing().when(individuServiceMock).remove(idToRemove);

        // Création d'un objet MockMvc pour simuler les requêtes HTTP
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(individuController).build();
        
        //ASSERT
        // Envoie d'une requête DELETE HTTP et vérification que la réponse a un statut HTTP 200 OK
        mockMvc.perform(delete("/individu/{id}", idToRemove))
                .andExpect(status().isOk());

        // Vérification que la méthode remove du service a bien été appelée
        verify(individuServiceMock, times(1)).remove(idToRemove);
        // Vérification qu'il n'y a pas d'autres interactions avec le mock du service
        verifyNoMoreInteractions(individuServiceMock);
    }
}