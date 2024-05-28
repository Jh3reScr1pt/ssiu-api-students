package com.example.ssiu_spring_boot_api.dtos;

import com.example.ssiu_spring_boot_api.models.StudentEntity;

import java.util.Date;

import org.bson.types.ObjectId;

public record StudentDTO(
    String id,
    String name,
    String lastname,
    String email_inst,
    String password,
    String career,
    int semester,
    boolean state_app,
    boolean state,
    Date creation_date) {
    public StudentDTO(StudentEntity s){
        this(s.getId() == null ? new ObjectId().toHexString() : s.getId().toHexString(), s.getName(), s.getLastname(), s.getEmail_inst(), s.getPassword(), s.getCareer(), s.getSemester(), s.isState_app(), s.isState(), s.getCreation_date());
    } 
    public StudentEntity toStudentEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new StudentEntity(_id,name,lastname,email_inst,password,career,semester,state_app,state,creation_date);
    }
}
