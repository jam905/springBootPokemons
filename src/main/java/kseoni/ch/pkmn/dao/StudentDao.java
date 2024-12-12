package kseoni.ch.pkmn.dao;

import kseoni.ch.pkmn.entities.StudentEntity;
import kseoni.ch.pkmn.repositories.StudentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudentDao {

    private final StudentEntityRepository studentRepository;

    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));
    }

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<StudentEntity> getStudentsByGroup(String group) {
        return studentRepository.findByGroup(group);
    }

    public StudentEntity getStudentByFio(String firstName, String surName, String familyName) {
        List<StudentEntity> students = studentRepository.findByFirstNameAndSurNameAndFamilyName(firstName, surName, familyName);
        if (students.isEmpty()) {
            throw new RuntimeException("No student found with provided FIO");
        } else if (students.size() > 1) {
            throw new RuntimeException("Multiple students found with provided FIO");
        }
        return students.get(0);
    }

    public StudentEntity getStudentByFioAndGroup(String firstName, String surName, String familyName, String group) {
        List<StudentEntity> students = studentRepository.findByFirstNameAndSurNameAndFamilyNameAndGroup(firstName, surName, familyName, group);
        if (students.isEmpty()) {
            return null;
        } else if (students.size() > 1) {
            throw new RuntimeException("Multiple students found with provided FIO and group");
        }
        return students.get(0);
    }

    public StudentEntity saveStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    public void deleteStudentById(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteCardById(id);
    }
}
