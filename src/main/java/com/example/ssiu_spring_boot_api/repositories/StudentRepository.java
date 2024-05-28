package com.example.ssiu_spring_boot_api.repositories;

import com.example.ssiu_spring_boot_api.models.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository {

    StudentEntity save(StudentEntity studenteEntity);

    List<StudentEntity> saveAll(List<StudentEntity> studentEntities);

    List<StudentEntity> findAll();

    List<StudentEntity> findAll(List<String> ids);

    StudentEntity findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    StudentEntity update(StudentEntity studentEntity);

    long update(List<StudentEntity> studentEntities);

    
}
