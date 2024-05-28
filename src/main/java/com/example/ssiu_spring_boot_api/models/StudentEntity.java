package com.example.ssiu_spring_boot_api.models;

import org.bson.types.ObjectId;


import java.util.Date;
import java.util.Objects;;

public class StudentEntity {
    
    private ObjectId id;

    private String name;
    private String lastname;

    private String email_inst;
    private String password;

    private String career;
    private int semester;

    private boolean state_app;
    private boolean state;

    private Date creation_date = new Date();

    public StudentEntity(){
    }

    public StudentEntity(ObjectId id, String name, String lastname, String email_inst, String password, String career,
            int semester, boolean state_app, boolean state, Date creation_date) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email_inst = email_inst;
        this.password = password;
        this.career = career;
        this.semester = semester;
        this.state_app = state_app;
        this.state = state;
        this.creation_date = creation_date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail_inst() {
        return email_inst;
    }

    public void setEmail_inst(String email_inst) {
        this.email_inst = email_inst;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean isState_app() {
        return state_app;
    }

    public void setState_app(boolean state_app) {
        this.state_app = state_app;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email_inst='" + email_inst + '\'' +
                ", password='" + password + '\'' +
                ", career='" + career + '\'' +
                ", semester=" + semester +
                ", state_app=" + state_app +
                ", state=" + state +
                ", creation_date=" + creation_date +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        StudentEntity studentEntity = (StudentEntity) o;
        return id == studentEntity.id && Objects.equals(name, studentEntity.name) && Objects.equals(lastname, studentEntity.lastname) && Objects.equals(email_inst, studentEntity.email_inst) && Objects.equals(password, studentEntity.password) && Objects.equals(career, studentEntity.career) && Objects.equals(semester, studentEntity.semester) && Objects.equals(state_app, studentEntity.state_app) && Objects.equals(state, studentEntity.state) && Objects.equals(creation_date, studentEntity.creation_date);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, email_inst, password, career, semester, state_app, state, creation_date);
    }

}
