package kseoni.ch.pkmn.controllers;

import kseoni.ch.pkmn.models.Student;
import kseoni.ch.pkmn.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{group}")
    public List<Student> getStudentsByGroup(@PathVariable("group") String group) {
        return studentService.getStudentsByGroup(group);
    }



    @GetMapping("")
    public Student getStudentByFIO(@RequestBody Student request) {
        return studentService.getStudentByFio(
                request.getFirstName(),
                request.getSurName(),
                request.getFamilyName()
        );
    }

    @PostMapping("")
    public Student createStudent(@RequestBody Student request) {
        return studentService.saveStudent(request);
    }
}
