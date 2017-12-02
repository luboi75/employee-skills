package com.lubo.learning.heroku.data.holder;

import java.util.List;

public class Skill {
    private int id;
    private String skillName;
    private List<Employee2Skill> employee2Skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public List<Employee2Skill> getEmployee2Skills() {
        return employee2Skills;
    }

    public void setEmployee2Skills(List<Employee2Skill> employee2Skills) {
        this.employee2Skills = employee2Skills;
    }
}
