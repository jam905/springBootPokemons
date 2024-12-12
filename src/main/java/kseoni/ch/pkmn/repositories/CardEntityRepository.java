package kseoni.ch.pkmn.repositories;

import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardEntityRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findByName(String name);

    Optional<CardEntity> findByNameAndNumber(String name, String number);

    List<CardEntity> findByPokemonOwner_Id(UUID ownerId);

    public void deleteCardById(UUID id);

    public boolean existsById(UUID id);

    public CardEntity findById(UUID id);
}
