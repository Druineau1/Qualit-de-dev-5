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
    @Test
    public void testGetFound() {
        // ARRANGE
        long idToGet = 1L;
        String path = "/individu/{id}";

        // Création de l'individu fictif
        Individu individu = new Individu();
        individu.setId(idToGet);
        individu.setFirstName("John");
        individu.setLastName("Doe");
        individu.setTitle("Mr");
        individu.setHeight(180);
        individu.setBirthDate(LocalDate.of(1980, 5, 15));

        // Simulation du comportement du mock lorsque l'entité est trouvée dans la base de données
        when(repositoryMock.findById(idToGet)).thenReturn(Optional.of(individu));

        //ACT Requête http
        Individu response = client.get().uri(path, idToGet)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Individu.class)
                .returnResult().getResponseBody();

        //ASSERT Vérification de tous les champs d'ue l'individu
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(idToGet);
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
        assertThat(response.getTitle()).isEqualTo("Mr");
        assertThat(response.getHeight()).isEqualTo(180);
        assertThat(response.getBirthDate()).isEqualTo(LocalDate.of(1980, 5, 15));
        }
    @Test
    public void testGetNotFound() {
    	//ARRANGE Définition d'un id et d'un chemein pour la requête
        long idToNotGet = 1L;
        String path = "/individu/{id}";
        
        when(repositoryMock.findById(idToNotGet)).thenReturn(Optional.empty());
        
        //ACT Envoie de la requête pour récupérer l'id
        Individu response = client.get().uri(path, idToNotGet)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Individu.class)
                .returnResult().getResponseBody();
        
        //ASSERT Vérification findById a été appelé et qu'il n'y a pas d'autre interaction
        verify(repositoryMock).findById(idToNotGet);
        verifyNoMoreInteractions(repositoryMock);
    }

}
