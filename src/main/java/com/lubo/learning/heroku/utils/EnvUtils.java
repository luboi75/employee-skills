package com.lubo.learning.heroku.utils;

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

    private int port;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public EnvUtils() {
        this.port = Integer.parseInt(getEnv(EnvVarNames.PORT));
        this.dbUrl = getEnv(EnvVarNames.DATABASE_URL);
        this.dbUser = getEnv(EnvVarNames.JDBC_DATABASE_USERNAME);
        this.dbPassword = getEnv(EnvVarNames.JDBC_DATABASE_PASSWORD);
        if (this.dbUrl.equals(EnvVarNames.DATABASE_URL.def)) {
            this.dbUrl.replace(":".concat(this.dbPassword), "");
            this.dbUrl.replace(":".concat(this.dbUser), "");
        }
    }

    private static String getEnv(EnvVarNames var) {
        String result = System.getenv(var.name);
        if (result == null) {
            // set default if not found
            result = var.def;
        }

        return result;
    }

    public int getPort() {
        return port;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}