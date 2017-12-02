package com.lubo.learning.heroku.data.holder;

import java.util.List;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private List<Employee2Skill> employee2Skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Employee2Skill> getEmployee2Skills() {
        return employee2Skills;
    }

    public void setEmployee2Skills(List<Employee2Skill> employee2Skills) {
        this.employee2Skills = employee2Skills;
    }
}
