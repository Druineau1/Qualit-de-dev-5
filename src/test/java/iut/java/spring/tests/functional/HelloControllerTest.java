package iut.java.spring.tests.functional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import iut.java.spring.dto.MessageDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHello() throws JsonMappingException, JsonProcessingException {
    	// Initialise la réponse attendue et crée un objet ObjectMapper pour la désérialisation JSON
        String expectedResponse = "Hello World !";
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Effectue un appel HTTP vers la méthode hello du contrôleur 
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/hello", String.class);
        String responseBodyJson = responseEntity.getBody(); // Récupère la réponse JSON
        MessageDto responseBody = objectMapper.readValue(responseBodyJson, MessageDto.class); // Désérialise la réponse JSON en un objet MessageDto
        String actualResponse = responseBody.getText(); // Extrait le texte de l'objet MessageDto

        // Vérification que le texte extrait correspond à la réponse attendue
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
