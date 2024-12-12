package kseoni.ch.pkmn.entities;

import jakarta.persistence.*;
import kseoni.ch.pkmn.entities.CardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "sur_name", nullable = false)
    private String surName;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(name = "group", nullable = false)
    private String group;

    @OneToMany(mappedBy = "pokemonOwner", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CardEntity> cards;
}
