package com.lubo.learning.heroku;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(Integer.parseInt(System.getenv("PORT")));
        staticFileLocation("static");
        get("/hello", (req, res) -> "Hello World!");
    }
}
