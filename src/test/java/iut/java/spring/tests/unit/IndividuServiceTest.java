package iut.java.spring.tests.unit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
}
