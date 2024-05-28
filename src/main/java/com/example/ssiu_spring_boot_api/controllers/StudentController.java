package com.example.ssiu_spring_boot_api.controllers;

import com.example.ssiu_spring_boot_api.dtos.StudentDTO;
import com.example.ssiu_spring_boot_api.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("student")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO postStudent(@RequestBody StudentDTO StudentDTO) {
        return studentService.save(StudentDTO);
    }

    @PostMapping("students")
    @ResponseStatus(HttpStatus.CREATED)
    public List<StudentDTO> postStudents(@RequestBody List<StudentDTO> studentEntities) {
        return studentService.saveAll(studentEntities);
    }

    @GetMapping("students")
    public List<StudentDTO> getStudents() {
        return studentService.findAll();
    }

    @GetMapping("student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String id) {
        StudentDTO StudentDTO = studentService.findOne(id);
        if (StudentDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(StudentDTO);
    }

    @GetMapping("students/{ids}")
    public List<StudentDTO> getStudents(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return studentService.findAll(listIds);
    }

    @GetMapping("students/count")
    public Long getCount() {
        return studentService.count();
    }

    @DeleteMapping("student/{id}")
    public Long deleteStudent(@PathVariable String id) {
        return studentService.delete(id);
    }

    @DeleteMapping("students/{ids}")
    public Long deleteStudents(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return studentService.delete(listIds);
    }

    @DeleteMapping("students")
    public Long deleteStudents() {
        return studentService.deleteAll();
    }

    @PutMapping("student")
    public StudentDTO putStudent(@RequestBody StudentDTO StudentDTO) {
        return studentService.update(StudentDTO);
    }

    @PutMapping("students")
    public Long putStudent(@RequestBody List<StudentDTO> studentEntities) {
        return studentService.update(studentEntities);
    }

    

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

}
