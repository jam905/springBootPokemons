package kazbekova.jm.pkmn.services;

import kazbekova.jm.pkmn.models.Card;
import kazbekova.jm.pkmn.models.Student;

import java.util.List;
import java.util.UUID;

public interface CardService {

    Card getCardById(UUID id);

    List<Card> getCardsByName(String name);

    List<Card> getCardsByOwnerId(UUID ownerId);

    Card saveCard(Card card);

    void deleteCardById(UUID id);

    public List<Card> getCardsByOwner(Student owner);

}
