package iut.java.spring.tests.unit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.entity.Individu;
import iut.java.spring.repository.IIndividuRepository;
import iut.java.spring.service.impl.IndividuService;

@SpringBootTest
public class IndividuServiceTest {

    @Mock
    private IIndividuRepository repositoryMock;

    @InjectMocks
    private IndividuService service;

    @Test
    public void testRemove() {
        // Création delindividu et de son id
        long id = 1L;
        Individu individu = new Individu();
        individu.setId(id);
        when(repositoryMock.findById(id)).thenReturn(Optional.of(individu));

        // Appel de la méthode remove de IndividuService pour supprimer l'individu
        service.remove(id);

        // Vérification que l'individu a été supprimé
        verify(repositoryMock).deleteById(id);
        verifyNoMoreInteractions(repositoryMock);
    }
    
    @Test
    public void testGetFound() {
        //Création d'un individu entier
        long id = 1L;
        Individu individu = new Individu();
        individu.setId(id);
        individu.setFirstName("John");
        individu.setLastName("Doe");
        individu.setTitle("Mr");
        individu.setHeight(180);
        individu.setBirthDate(LocalDate.of(1980, 1, 1));
        when(repositoryMock.findById(id)).thenReturn(Optional.of(individu));

        // ACT Obtention de l'optiponal
        Optional<IndividuDto> result = service.get(id);

        // ASSERT
        assertThat(result).isPresent(); // Vérifie si l'optionnal contient une valeur
        IndividuDto individuDto = result.get(); // Obtient la valeur de l'optionnal
        assertThat(individuDto.getId()).isEqualTo(id); // Vérifie l'ID
        assertThat(individuDto.getFirstName()).isEqualTo("John"); // Vérifie le prénom
        assertThat(individuDto.getLastName()).isEqualTo("Doe"); // Vérifie le nom de famille
        assertThat(individuDto.getTitle()).isEqualTo("Mr"); // Vérifie le titre
        assertThat(individuDto.getHeight()).isEqualTo(180); // Vérifie la taille
        assertThat(individuDto.getBirthDate()).isEqualTo(LocalDate.of(1980, 1, 1)); // Vérifie la date de naissance
    }
    
    @Test
    public void testGetNotFound() {
        // Configuration du comportement du mock pour retourner un Optional vide
        long idNotFound = 1L;
        when(repositoryMock.findById(idNotFound)).thenReturn(Optional.empty());

        // Appel de la méthode get de IndividuService avec l'ID spécifié
        Optional<IndividuDto> result = service.get(idNotFound);

        // Vérification que le résultat est vide
        assertThat(result).isEmpty();
    }
}
