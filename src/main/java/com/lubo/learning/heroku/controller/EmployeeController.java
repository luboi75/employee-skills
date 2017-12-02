package com.lubo.learning.heroku.controller;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class EmployeeController {
    private static final Logger logger = Logger.getLogger(EmployeeController.class.getName());
    private static final Gson gson = new Gson();

    public static Route getEmployee = (Request request, Response response)  -> {
        return null;
    };

    public static Object getEmployee(Request request, Response response) {
        Object result = null;

        return gson.toJson(result);
    }
}
