package kseoni.ch.pkmn.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kseoni.ch.pkmn.models.AttackSkill;
import kseoni.ch.pkmn.models.EnergyType;
import kseoni.ch.pkmn.models.PokemonStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private PokemonStage pokemonStage;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hp")
    private int hp;

    @Enumerated(EnumType.STRING)
    @Column(name = "pokemon_type")
    private EnergyType pokemonType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evolves_from_id")
    private CardEntity evolvesFrom;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attack_skills")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    @Enumerated(EnumType.STRING)
    @Column(name = "weakness_type")
    private EnergyType weaknessType;

    @Enumerated(EnumType.STRING)
    @Column(name = "resistance_type")
    private EnergyType resistanceType;

    @Column(name = "retreat_cost")
    private String retreatCost;

    @Column(name = "game_set")
    private String gameSet;

    @Column(name = "regulation_mark", length = 1)
    private char regulationMark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_owner_id")
    private StudentEntity pokemonOwner;

    @Column(name = "card_number")
    private String number;

    @Column(name = "image_url")
    private String imageUrl;

}
