package kseoni.ch.pkmn.services;

import kseoni.ch.pkmn.clients.PokemonTcgClient;
import kseoni.ch.pkmn.dao.CardDao;
import kseoni.ch.pkmn.dao.StudentDao;
import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.entities.StudentEntity;
import kseoni.ch.pkmn.models.Card;
import kseoni.ch.pkmn.models.PokemonStage;
import kseoni.ch.pkmn.models.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;
    private final StudentDao studentDao;
    private final PokemonTcgClient pokemonTcgClient;

    @Override
    public Card getCardById(UUID id) {
        CardEntity cardEntity = cardDao.getCardById(id);
        return fromEntity(cardEntity);
    }

    @Override
    public List<Card> getCardsByName(String name) {
        List<CardEntity> cardEntities = cardDao.getCardsByName(name);
        return cardEntities.stream().map(this::fromEntity).toList();
    }

    @Override
    public List<Card> getCardsByOwnerId(UUID ownerId) {
        List<CardEntity> cardEntities = cardDao.getCardsByOwnerId(ownerId);
        return cardEntities.stream().map(this::fromEntity).toList();
    }

    @Override
    public Card saveCard(Card card) {
        Student owner = card.getPokemonOwner();
        if (owner == null) {
            throw new RuntimeException("Owner is required");
        }

        StudentEntity ownerEntity = studentDao.getStudentByFioAndGroup(
                owner.getFirstName(),
                owner.getSurName(),
                owner.getFamilyName(),
                owner.getGroup()
        );
        if (ownerEntity == null) {
            throw new RuntimeException("Owner not found in DB");
        }

        if ((card.getPokemonStage() == PokemonStage.STAGE1 || card.getPokemonStage() == PokemonStage.STAGE2)
                && card.getEvolvesFrom() == null) {
            throw new RuntimeException("EvolvesFrom is required for non-basic stages");
        }

        //сохранение URL
        String imageUrl = pokemonTcgClient.getCardImageByName(card.getName());
        card.setImageUrl(imageUrl);

        Optional<CardEntity> existing = cardDao.getCardByNameAndNumber(card.getName(), card.getNumber());
        CardEntity entityToSave = toEntity(card);
        if (existing.isPresent()) {
            CardEntity existingEntity = existing.get();
            existingEntity.setHp(card.getHp());
            entityToSave = existingEntity;
        }

        CardEntity saved = cardDao.saveCard(entityToSave);
        return fromEntity(saved);
    }

    @Override
    public void deleteCardById(UUID id) {
        cardDao.deleteCardById(id);
    }

    private Card fromEntity(CardEntity entity) {
        if (entity == null) {
            return null;
        }
        return Card.builder()
                .pokemonStage(entity.getPokemonStage())
                .name(entity.getName())
                .hp(entity.getHp())
                .pokemonType(entity.getPokemonType())
                .evolvesFrom(fromEntity(entity.getEvolvesFrom()))
                .skills(entity.getSkills())
                .weaknessType(entity.getWeaknessType())
                .resistanceType(entity.getResistanceType())
                .retreatCost(entity.getRetreatCost())
                .gameSet(entity.getGameSet())
                .regulationMark(entity.getRegulationMark())
                .pokemonOwner(Student.fromEntity(entity.getPokemonOwner()))
                .number(entity.getNumber())
                .build();
    }


    private CardEntity toEntity(Card card) {
        if (card == null) {
            return null;
        }

        CardEntity evolvesFromEntity = null;
        if (card.getEvolvesFrom() != null) {
            evolvesFromEntity = cardDao.getCardByNameAndNumber(card.getEvolvesFrom().getName(), card.getEvolvesFrom().getNumber())
                    .orElse(null);
        }

        StudentEntity ownerEntity = null;
        if (card.getPokemonOwner() != null) {
            ownerEntity = Student.toEntity(card.getPokemonOwner());
            ownerEntity = studentDao.saveStudent(ownerEntity);
        }

        return CardEntity.builder()
                .pokemonStage(card.getPokemonStage())
                .name(card.getName())
                .hp(card.getHp())
                .pokemonType(card.getPokemonType())
                .evolvesFrom(evolvesFromEntity)
                .skills(card.getSkills())
                .weaknessType(card.getWeaknessType())
                .resistanceType(card.getResistanceType())
                .retreatCost(card.getRetreatCost())
                .gameSet(card.getGameSet())
                .regulationMark(card.getRegulationMark())
                .pokemonOwner(ownerEntity)
                .number(card.getNumber())
                .build();
    }

    @Override
    public List<Card> getCardsByOwner(Student owner) {
        StudentEntity studentEntity = studentDao.getStudentByFioAndGroup(
                owner.getFirstName(),
                owner.getSurName(),
                owner.getFamilyName(),
                owner.getGroup()
        );
        if (studentEntity == null) {
            throw new RuntimeException("Owner not found");
        }
        List<CardEntity> cardEntities = cardDao.getCardsByOwnerId(studentEntity.getId());
        return cardEntities.stream().map(this::fromEntity).toList();
    }

}
