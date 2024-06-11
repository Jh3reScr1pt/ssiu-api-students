package com.example.ssiu_spring_boot_api.controllers;

import com.example.ssiu_spring_boot_api.dtos.StudentDTO;
import com.example.ssiu_spring_boot_api.models.StudentEntity;
import com.example.ssiu_spring_boot_api.security.JwtTokenUtil;
import com.example.ssiu_spring_boot_api.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class StudentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final JwtTokenUtil jwtTokenUtil;

    public StudentController(StudentService studentService, JwtTokenUtil jwtTokenUtil) {
        this.studentService = studentService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("students")
    public List<StudentDTO> getStudents() {
        return studentService.findAll();
    }

    @GetMapping("student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String id) {
        StudentDTO StudentDTO = studentService.findOne(id);
        if (StudentDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(StudentDTO);
    }

    @GetMapping("students/count")
    public Long getCount() {
        return studentService.count();
    }

    @PostMapping("student")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO postStudent(@RequestBody StudentDTO StudentDTO) {
        return studentService.save(StudentDTO);
    }

    @PutMapping("student")
    public StudentDTO putStudent(@RequestBody StudentDTO StudentDTO) {
        return studentService.update(StudentDTO);
    }

    //@DeleteMapping("student/{id}")
    //public Long deleteStudent(@PathVariable String id) {
    //    return studentService.delete(id);
    //}
    @PutMapping("student/activate/state/{id}")
    public long activateState(@PathVariable String id) {
        return studentService.activateState(id);
    }

    @PutMapping("student/activate/state_app/{id}")
    public long activateStateApp(@PathVariable String id) {
        return studentService.activateStateApp(id);
    }

    @DeleteMapping("student/logical_delete/state/{id}")
    public Long logicalDeleteState(@PathVariable String id) {
        return studentService.logicalDeleteState(id);
    }

    @DeleteMapping("student/logical_delete/state_app/{id}")
    public Long logicalDeleteStateApp(@PathVariable String id) {
        return studentService.logicalDeleteStateApp(id);
    }

    @PostMapping("student/auth")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = studentService.login(email, password);
        if (isAuthenticated) {
            StudentEntity studentEntity = studentService.findByEmail(email);
            String token = jwtTokenUtil.generateToken(studentEntity.getId().toString());
            StudentDTO studentData = studentService.findOne(studentEntity.getId().toString());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("id", studentData.id());
            response.put("name", studentData.name());
            response.put("lastname", studentData.lastname());
            response.put("email_inst", studentData.email_inst());
            response.put("career", studentData.career());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    // @PostMapping("students")
    // @ResponseStatus(HttpStatus.CREATED)
    // public List<StudentDTO> postStudents(@RequestBody List<StudentDTO>
    // studentEntities) {
    // return studentService.saveAll(studentEntities);
    // }

    // @GetMapping("students/{ids}")
    // public List<StudentDTO> getStudents(@PathVariable String ids) {
    // List<String> listIds = List.of(ids.split(","));
    // return studentService.findAll(listIds);
    // }

    // @DeleteMapping("students/{ids}")
    // public Long deleteStudents(@PathVariable String ids) {
    // List<String> listIds = List.of(ids.split(","));
    // return studentService.delete(listIds);
    // }

    // @DeleteMapping("students")
    // public Long deleteStudents() {
    // return studentService.deleteAll();
    // }

    // @PutMapping("students")
    // public Long putStudent(@RequestBody List<StudentDTO> studentEntities) {
    // return studentService.update(studentEntities);
    // }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

}
