package com.lubo.learning.heroku.data.utils;

import com.lubo.learning.heroku.utils.EnvUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {
    public static final String poolName = "myHikariPool";

    // prevent creating instance
    private ConnectionPool() {}

    private static HikariDataSource hds = null;
    public static HikariDataSource getDataSource() {
        if (hds == null) {
            EnvUtils envUtils = new EnvUtils();
            hds = getDataSourceFromConfig(envUtils);
        }
        return hds;
    }
    private static synchronized HikariDataSource getDataSourceFromConfig(EnvUtils conf) {

        if (hds == null) {
            HikariConfig jdbcConfig = new HikariConfig();
            jdbcConfig.setPoolName(poolName);
            jdbcConfig.setMaximumPoolSize(2);
            jdbcConfig.setMinimumIdle(1);
            jdbcConfig.setJdbcUrl(conf.getDbUrl());
            jdbcConfig.setUsername(conf.getDbUser());
            jdbcConfig.setPassword(conf.getDbPassword());

            jdbcConfig.addDataSourceProperty("cachePrepStmts", true);
            jdbcConfig.addDataSourceProperty("prepStmtCacheSize", 256);
            jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
            jdbcConfig.addDataSourceProperty("useServerPrepStmts", true);

            hds = new HikariDataSource(jdbcConfig);
        }
        return hds;
    }
}
