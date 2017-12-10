package com.lubo.learning.heroku;

import com.google.gson.Gson;
import com.lubo.learning.heroku.controller.EmployeeController;
import com.lubo.learning.heroku.data.utils.SchemaUtils;
import com.lubo.learning.heroku.utils.EnvUtils;
import com.lubo.learning.heroku.utils.ResourceLoader;
import org.apache.log4j.Logger;

import java.net.URL;
import java.net.URLClassLoader;

import static spark.Spark.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            logger.info(url.getFile());
        }
        // ensure, DB is in right shape
        SchemaUtils.ensureDB();
        // init port either on heroku environment variable or set by default
        port(EnvUtils.getPort());
        // define root directory for static files
        staticFileLocation("static");
        // define routes
        initRoutes();
    }

    /**
     * initialize routes for the application
     * initialization is based on path groups
     * root for all routes is
     *  /api
     * followed by entity name
     *  /api/employee
     *  /api/skill
     *
     */
    private static void initRoutes() {
        get("/resource/:name", (request, response) -> {
            ResourceLoader rl = new ResourceLoader();
            return rl.loadFile(request.params(":name"));
        });
        get("/resource/:folder1/:folder2", (request, response) -> {
            ResourceLoader rl = new ResourceLoader();
            return rl.loadFile(request.params(":folder1") + "/" + request.params(":folder2"));
        });
        path("/api", () -> {
            before("/*", (req, res) -> logger.info("api call received"));
            path("/employee", () -> {
                post("", "application/json",
                        (request, response) -> EmployeeController.createEmployee(request, response));
                put("", "application/json",
                        (request, response) -> EmployeeController.updateEmployee(request, response));
                get ("", (request, response) -> EmployeeController.getEmployeeList(request, response));
                get("/:id", (request, response) -> EmployeeController.getEmployee(request, response));
                delete("/:id", (request, response) -> EmployeeController.deleteEmployee(request, response));
            });
            path("/skill", () -> {
                post("/add", (request, response) -> "adding skill");
                put("/change/:id", (request, response) -> "changing skill " + request.params(":id"));
                get("/:id", (request, response) -> "returning skill " + request.params(":id"));
                delete("/:id", (request, response) -> "deleting skill " + request.params(":id"));
            });
            path("/employeeSkill", () -> {
                post("/add", (request, response) -> "adding skill to employee");
                put("/change/:e-id/:s-id", (request, response) -> "modifying employee's skill "
                        + request.params(":e-id") + ", " + request.params(":s-id"));
                get("/:e-id/s-id", (request, response) -> "returning employee's skill level"
                        + request.params(":e-id") + ", " + request.params(":s-id"));
                delete("/:e-id/s-id", (request, response) -> "deleting employee's skill "
                        + request.params(":e-id") + ", " + request.params(":s-id"));
            });

        } );
    }
}
