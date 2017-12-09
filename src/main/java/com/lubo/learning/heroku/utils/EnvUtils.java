package com.lubo.learning.heroku.utils;

public class EnvUtils {
    private enum EnvVarNames {
        PORT("PORT"),
        JDBC_DATABASE_URL("JDBC_DATABASE_URL"),
        JDBC_DATABASE_USERNAME("JDBC_DATABASE_USERNAME"),
        JDBC_DATABASE_PASSWORD("JDBC_DATABASE_PASSWORD"),
        MAX_POOL_SIZE("MAX_POOL_SIZE"),
        MIN_IDLE_COUNT("MIN_IDLE_COUNT");


        private final String name;

        EnvVarNames(final String name) {
            this.name = name;
        }
    }

    private static String getEnv(EnvVarNames var) {
        return System.getenv(var.name);
    }

    public static int getPort() {
        return Integer.parseInt(getEnv(EnvVarNames.PORT));
    }

    public static String getDbUrl() {
        return getEnv(EnvVarNames.JDBC_DATABASE_URL);
    }

    public static String getDbUser() {
        return getEnv(EnvVarNames.JDBC_DATABASE_USERNAME);
    }

    public static String getDbPassword() {
        return getEnv(EnvVarNames.JDBC_DATABASE_PASSWORD);
    }

    public static int getMinIdleCount() {
        return Integer.parseInt(getEnv(EnvVarNames.MIN_IDLE_COUNT));
    }

    public static int getMaxPoolSize() {
        return Integer.parseInt(getEnv(EnvVarNames.MAX_POOL_SIZE));
    }
}
