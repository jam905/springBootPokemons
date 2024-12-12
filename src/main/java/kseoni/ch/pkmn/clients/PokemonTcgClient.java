package kseoni.ch.pkmn.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PokemonTcgClient {

    private final RestTemplateBuilder restTemplateBuilder;


    public String getCardImageByName(String cardName) {
        String POKEMON_TCG_API_URL = "https://api.pokemontcg.io/v2/cards?q=name:" + cardName;
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = String.format(POKEMON_TCG_API_URL, cardName);

        String jsonResponse = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode data = root.get("data");

            JsonNode firstCard = data.get(0);
            JsonNode images = firstCard.get("images");
            if (images == null) {
                throw new RuntimeException("No images found for card: " + cardName);
            }

            String imageUrl = images.has("large") ? images.get("large").asText() : null;
            if (imageUrl == null && images.has("small")) {
                imageUrl = images.get("small").asText();
            }

            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }
}
