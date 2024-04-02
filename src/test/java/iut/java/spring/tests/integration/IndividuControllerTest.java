package iut.java.spring.tests.integration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import iut.java.spring.entity.Individu;
import iut.java.spring.repository.IIndividuRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class IndividuControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private IIndividuRepository repositoryMock;

    @Test
    public void testRemove() {
        //ARRANGE Définition d'une id a supprimé ainsi qu'un chemin
        long idToRemove = 1L;
        String path = "/individu/{id}";

        // Définition du comportement du mock
        doNothing().when(repositoryMock).deleteById(idToRemove);

        //ACT Envoie d'une requête http pour supprimer l'id
        client.delete().uri(path, idToRemove)
                .exchange()
                .expectStatus().isOk();

        //ASSERT Vérification que deleteById a été appelé et  qu'il n'y a pas d'autre interaction
        verify(repositoryMock).deleteById(idToRemove);
        verifyNoMoreInteractions(repositoryMock);
    }

}
