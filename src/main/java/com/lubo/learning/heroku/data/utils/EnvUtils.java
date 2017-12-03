package com.lubo.learning.heroku.data.utils;

public class EnvUtils {
    public enum EnvVarNames {
        PORT("PORT", "4567"),
        DATABASE_URL("DATABASE_URL", "jdbc:postgresql://localhost:5432/postgres"),
        JDBC_DATABASE_USERNAME("JDBC_DATABASE_USERNAME", "postgres"),
        JDBC_DATABASE_PASSWORD("JDBC_DATABASE_PASSWORD", "pass4you");

        private final String name;
        private final String def;

        EnvVarNames(final String name, String def) {
            this.name = name;
            this.def = def;
        }
    }

    public static String getEnv(EnvVarNames var) {
        String result = System.getenv(var.name);
        if (result == null) {
            // set default if not found
            result = var.def;
        }
        return result;
    }
}
