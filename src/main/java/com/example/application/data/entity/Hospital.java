package com.example.application.data.entity;

import org.hibernate.annotations.Formula;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Hospital extends AbstractEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "hospital")
    @Nullable
    private List<Doctor> employees = new LinkedList<>();

    @Formula("(select count(c.id) from Doctor c where c.hospital_id = id)")
    private int employeeCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Doctor> employees) {
        this.employees = employees;
    }

    public int getEmployeeCount(){
        return employeeCount;
    }
}
