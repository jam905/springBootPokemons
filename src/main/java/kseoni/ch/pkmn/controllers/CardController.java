package kseoni.ch.pkmn.controllers;

import kseoni.ch.pkmn.models.Card;
import kseoni.ch.pkmn.models.Student;
import kseoni.ch.pkmn.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/name/{name}")
    public List<Card> getCardsByName(@PathVariable String name) {
        return cardService.getCardsByName(name);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Card> getCardsByOwnerId(@PathVariable("ownerId") String ownerId) {
        UUID parsedOwnerId = UUID.fromString(ownerId);
        return cardService.getCardsByOwnerId(parsedOwnerId);
    }

    @GetMapping("/id/{id}")
    public Card getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }


    @PostMapping("")
    public Card createOrUpdateCard(@RequestBody Card cardRequest) {
        return cardService.saveCard(cardRequest);
    }


    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable UUID id) {
        cardService.deleteCardById(id);
    }

    @PostMapping("/owner")
    public List<Card> getCardsByOwner(@RequestBody Student owner) {
        return cardService.getCardsByOwner(owner);
    }
}
