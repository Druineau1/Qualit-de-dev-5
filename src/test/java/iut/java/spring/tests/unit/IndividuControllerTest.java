package iut.java.spring.tests.unit;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import iut.java.spring.controller.IndividuController;
import iut.java.spring.dto.IndividuDto;
import iut.java.spring.service.interfaces.IIndividuService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class IndividuControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IIndividuService individuServiceMock;

    @InjectMocks
    private IndividuController individuController;

    @Test
    public void testRemove() throws Exception {
    	// ID de l'individu à supprimer et définition du comportement du mock 
        long idToRemove = 1L;
        doNothing().when(individuServiceMock).remove(idToRemove);
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

    @Test
    public void testGetFound() throws Exception {
        //ARRANGE Définition de l'ID de l'individu à trouver et de l'individu
        long idToFind = 1L;
        IndividuDto foundIndividuDto = new IndividuDto();
        foundIndividuDto.setId(idToFind);
        foundIndividuDto.setFirstName("John");
        foundIndividuDto.setLastName("Doe");
        
        // Définition du comportement du mock
        when(individuServiceMock.get(idToFind)).thenReturn(Optional.of(foundIndividuDto));
        
        // ACT & ASSERT
        // Création d'un objet MockMvc pour simuler les requêtes HTTP
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(individuController).build();
        
        // Envoi d'une requête GET  et vérification de la réponse
        mockMvc.perform(get("/individu/{id}", idToFind))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(foundIndividuDto.getId()))
            .andExpect(jsonPath("$.firstName").value(foundIndividuDto.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(foundIndividuDto.getLastName()));
    }
    @Test
    public void testGetNotFound() throws Exception {
        //ARRANGE
        long idToFind = 1L;
        // Définition du comportement du mock pour retourner un Optional vide
        when(individuServiceMock.get(idToFind)).thenReturn(Optional.empty());

        //ACT
        // Création d'un objet MockMvc pour simuler les requêtes HTTP
        mockMvc = MockMvcBuilders.standaloneSetup(individuController).build();
        
        //ASSERT Envoie d'une requête GET  et vérification du statut de la réponse
        mockMvc.perform(get("/individu/{id}", idToFind))
            .andExpect(status().isNotFound());

        // Vérification que la méthode get a été appelée
        verify(individuServiceMock).get(idToFind);
        // Vérification qu'il n'y a pas d'autres interactions avec le mock du service
        verifyNoMoreInteractions(individuServiceMock);
    }
}
