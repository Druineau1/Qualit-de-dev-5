package iut.java.spring.tests.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import iut.java.spring.dto.MessageDto;
import iut.java.spring.service.interfaces.IMessageService;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private IMessageService messageService;

    @Test
    public void testSayHelloDefault() {
        //Appel de la méthode sayHello avec un paramètre null pour tester le comportement par défaut.
        MessageDto message = messageService.sayHello(null);

        //Vérification que le texte du message retourné est correct.
        assertThat(message.getText()).isEqualTo("Hello World !");
    }
    @Test
    public void testSayHello() {
        // Création de la variable qui va contenir le nom voulu
        String name = "TomTom";

        // Appel de la fonction avec la variable
        MessageDto message = messageService.sayHello(name);

        // Vérification du résultat
        assertThat(message.getText()).isEqualTo("Hello TomTom !");
    }
    
}
