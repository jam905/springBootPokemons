package kseoni.ch.pkmn.dao;

import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.repositories.CardEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDao {

    private final CardEntityRepository cardRepository;

    public CardEntity getCardById(UUID id) {
        try{
            return cardRepository.findById(id);
        }
        catch (RuntimeException r){
            return null;
        }
    }

    public List<CardEntity> getCardsByName(String name) {
        return cardRepository.findByName(name);
    }

    public List<CardEntity> getCardsByOwnerId(UUID ownerId) {
        return cardRepository.findByPokemonOwner_Id(ownerId);
    }

    public Optional<CardEntity> getCardByNameAndNumber(String name, String number) {
        return cardRepository.findByNameAndNumber(name, number);
    }

    public CardEntity saveCard(CardEntity cardEntity) {
        return cardRepository.save(cardEntity);
    }

    public void deleteCardById(UUID id) {
        if (!cardRepository.existsById(id)) {
            throw new RuntimeException("Card with id " + id + " does not exist");
        }
        cardRepository.deleteCardById(id);
    }

    public List<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }
}
