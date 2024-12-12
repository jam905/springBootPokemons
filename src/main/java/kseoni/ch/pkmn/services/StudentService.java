package kseoni.ch.pkmn.services;

import kseoni.ch.pkmn.models.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    List<Student> getAllStudents();

    List<Student> getStudentsByGroup(String group);

    Student getStudentByFio(String firstName, String surName, String familyName);

    Student saveStudent(Student student);

    Student getStudentById(Long id);

    void deleteStudentById(UUID id);
}
