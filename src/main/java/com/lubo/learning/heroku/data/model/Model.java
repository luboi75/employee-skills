package com.lubo.learning.heroku.data.model;

import com.lubo.learning.heroku.data.holder.Employee;
import com.lubo.learning.heroku.data.holder.Skill;

import java.util.List;

public interface Model {
    // employee
    boolean createEmployee(Employee e);
    Employee readEmplpoyee(int id);
    boolean updateEmployee(Employee e);
    boolean deleteEmployee(int id);
    List<Employee> searchEmployee(String pattern);
    List<Employee> findEmployeesWithSkill(int skillId);

    // skill
    boolean createSkill(Skill s);
    Skill readSkill(int id);
    boolean updateSKill(Skill s);
    boolean deleteSkill(int id);
    List<Skill> searchSkill(String pattern);
    List<Skill> findSkillsToEmployee(int employeeId);

}
