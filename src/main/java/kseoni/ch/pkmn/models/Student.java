package kseoni.ch.pkmn.models;

import kseoni.ch.pkmn.entities.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Student implements Serializable {

    public static final long serialVersionUID = 1L;

    String firstName;
    String surName;
    String familyName;
    String group;

    public static Student fromEntity(StudentEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Student(
                entity.getFirstName(),
                entity.getSurName(),
                entity.getFamilyName(),
                entity.getGroup()
        );
    }

    public static StudentEntity toEntity(Student student) {
        if (student == null) {
            return null;
        }
        StudentEntity entity = new StudentEntity();
        entity.setFirstName(student.getFirstName());
        entity.setSurName(student.getSurName());
        entity.setFamilyName(student.getFamilyName());
        entity.setGroup(student.getGroup());
        return entity;
    }

    @Override
    public String toString() {
        return "firstName = " + firstName + " surName = " + surName + " familyName = " + familyName +  " group = " + group;
    }
}
