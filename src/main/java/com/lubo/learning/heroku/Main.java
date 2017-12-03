package com.lubo.learning.heroku;

import com.google.gson.Gson;
import com.lubo.learning.heroku.controller.EmployeeController;
import com.lubo.learning.heroku.utils.EnvUtils;
import com.lubo.learning.heroku.data.utils.SchemaUtils;

import java.util.logging.Logger;

import static spark.Spark.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        EnvUtils envUtils = new EnvUtils();
        // ensure, DB is in right shape
        SchemaUtils.ensureDB(envUtils.getDbUrl(), envUtils.getDbUser(), envUtils.getDbPassword());

        // init port either on heroku environment variable or set by default
        port(envUtils.getPort());
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
        path("/api", () -> {
            before("/*", (req, res) -> logger.info("api call received"));
            path("/employee", () -> {
                post("/add", "application/json",
                        (request, response) -> EmployeeController.getEmployee(request, response));
                put("/change/:id", "application/json",
                        (request, response) -> "changing employee " + request.params(":id"));
                get("/:id", (request, response) -> "returning employee " + request.params(":id"));
                get ("/list", (request, response) -> "returning employee list");
                delete("/:id", (request, response) -> "deleting employee " + request.params(":id"));
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
