package com.example.ssiu_spring_boot_api.services;

import java.util.List;

import com.example.ssiu_spring_boot_api.dtos.StudentDTO;

public interface StudentService {

    StudentDTO save(StudentDTO StudentDTO);

    List<StudentDTO> saveAll(List<StudentDTO> studentEntities);

    List<StudentDTO> findAll();

    List<StudentDTO> findAll(List<String> ids);

    StudentDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    StudentDTO update(StudentDTO StudentDTO);

    long update(List<StudentDTO> studentEntities);

    long activateState(String id);

    long activateStateApp(String id);

    long logicalDeleteState(String id);

    long logicalDeleteStateApp(String id);

    boolean login(String email, String password);
    

}
