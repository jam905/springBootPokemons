package kseoni.ch.pkmn.models;

import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.entities.StudentEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Card {
    private PokemonStage pokemonStage;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private Card evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private Student pokemonOwner;
    private String number;
    private String imageUrl;

}
