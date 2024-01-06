package com.example.jpastudent22.controller;

import com.example.jpastudent22.model.Student;
import com.example.jpastudent22.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentRestController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/")
    public String detteErRoden() {
        return "Du er i roden af JPAStudent22, yo";
    }

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Student postStudent(@RequestBody Student student) {
        System.out.println("Indsætter ny student");
        System.out.println(student);
        return studentRepository.save(student);
    }

    @PutMapping("/studentsimple")
    @ResponseStatus(HttpStatus.CREATED)
    public Student putStudent(@RequestBody Student student) {
        System.out.println(student);
        return studentRepository.save(student);
    }

    @PutMapping("/studentnocode")
    public Student putStudentNoCode(@RequestBody Student student) {
        Optional<Student> orgStudent = studentRepository.findById(student.getId());
        if (orgStudent.isPresent()) {
            return studentRepository.save(student);
        } else {
            Student notstudent = new Student();
            notstudent.setName("NotFound");
            return notstudent;
        }
    }

    @PutMapping("/student")
    public ResponseEntity<Student> putStudent1parm(@RequestBody Student student) {
        Optional<Student> orgStudent = studentRepository.findById(student.getId());
        if (orgStudent.isPresent()) {
            studentRepository.save(student);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Student(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> orgStudent = studentRepository.findById(id);
        if (orgStudent.isPresent()) {
            student.setId(id);
            studentRepository.save(student);
            return ResponseEntity.ok(student);
            //return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
            //return new ResponseEntity<>(new Student(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        Optional<Student> orgStudent = studentRepository.findById(id);
        if (orgStudent.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok("Student deleted");
        } else {
            //return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }


}
