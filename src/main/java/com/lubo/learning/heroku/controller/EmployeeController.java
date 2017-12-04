package com.lubo.learning.heroku.controller;

import com.google.gson.Gson;
import com.lubo.learning.heroku.data.holder.Employee;
import com.lubo.learning.heroku.data.utils.ConnectionPool;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Request;
import spark.Response;

import java.util.List;

public class EmployeeController {
    private static final Logger logger = Logger.getLogger(EmployeeController.class.getName());
    private static final Gson gson = new Gson();

    private static Connection getConnection() {
        HikariDataSource hds = ConnectionPool.getDataSource();
        Sql2o sql2o = new Sql2o(hds);
        return sql2o.open();
    }

    public static Object getEmployee(Request request, Response response) {
        Object result = null;
        Connection con = getConnection();
        try {
            List<Employee> employeeList = con.createQuery("SELECT * FROM employees WHERE id = :id").
                    addParameter("id", Integer.parseInt(request.params(":id"))).
                    executeAndFetch(Employee.class);

            if (!employeeList.isEmpty()) {
                result = employeeList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return gson.toJson(result);
    }

    public static Object getEmployeeList(Request request, Response response) {
        Object result = null;
        Connection con = getConnection();
        try {
            List<Employee> employeeList = con.createQuery("SELECT * FROM employees").
                    executeAndFetch(Employee.class);
            result = employeeList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return gson.toJson(result);
    }

    public static Object createEmployee(Request request, Response response) {
        Object result = null;
        Connection con = getConnection();
        Employee employee = gson.fromJson(request.body(), Employee.class);
        try {
            result = con.createQuery("INSERT INTO employees (firstName, lastName) VALUES (:firstName, :lastName)").
                    addParameter("firstName", employee.getFirstName()).
                    addParameter("lastName", employee.getLastName()).
                    executeUpdate().getKey();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        response.type("application/json");
        response.status(200);
        return gson.toJson(result);
    }
    public static Object updateEmployee(Request request, Response response) {
        Object result = null;
        Connection con = getConnection();
        Employee employee = gson.fromJson(request.body(), Employee.class);
        try {
            result = con.createQuery("UPDATE employees SET firstName=:firstName, lastName=:lastName WHERE id = :id").
                    addParameter("id", employee.getId()).
                    addParameter("firstName", employee.getFirstName()).
                    addParameter("lastName", employee.getLastName()).
                    executeUpdate().getKey();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        response.type("application/json");
        response.status(200);
        return gson.toJson(result);
    }

    public static Object deleteEmployee(Request request, Response response) {
        Object result = null;
        Connection con = getConnection();
        try {
            result = con.createQuery("DELETE FROM employees WHERE id = :id").
                    addParameter("id", Integer.parseInt(request.params(":id"))).
                    executeUpdate().getResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        response.type("application/json");
        response.status(200);
        return gson.toJson(result);
    }
}
