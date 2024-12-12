package kseoni.ch.pkmn.services;

import kseoni.ch.pkmn.dao.StudentDao;
import kseoni.ch.pkmn.entities.StudentEntity;
import kseoni.ch.pkmn.models.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents().stream()
                .map(Student::fromEntity)
                .toList();
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        return studentDao.getStudentsByGroup(group).stream()
                .map(Student::fromEntity)
                .toList();
    }

    @Override
    public Student getStudentByFio(String firstName, String surName, String familyName) {
        StudentEntity entity = studentDao.getStudentByFio(firstName, surName, familyName);
        return Student.fromEntity(entity);
    }

    @Override
    public Student saveStudent(Student student) {
        List<StudentEntity> sameFio = studentDao.getStudentByFioAndGroup(
                student.getFirstName(),
                student.getSurName(),
                student.getFamilyName(),
                student.getGroup()
        ) == null ? List.of() : List.of(studentDao.getStudentByFioAndGroup(
                student.getFirstName(),
                student.getSurName(),
                student.getFamilyName(),
                student.getGroup()
        ));

        if (!sameFio.isEmpty()) {
            throw new RuntimeException("Student with such FIO and group already exists");
        }

        StudentEntity saved = studentDao.saveStudent(Student.toEntity(student));
        return Student.fromEntity(saved);
    }

    @Override
    public Student getStudentById(Long id) {
        return Student.fromEntity(studentDao.getStudentById(id));
    }

    @Override
    public void deleteStudentById(UUID id) {
        studentDao.deleteStudentById(id);
    }
}
