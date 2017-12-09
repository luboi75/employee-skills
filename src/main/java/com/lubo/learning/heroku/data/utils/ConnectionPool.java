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
            hds = getDataSourceFromConfig();
        }
        return hds;
    }
    private static synchronized HikariDataSource getDataSourceFromConfig() {

        if (hds == null) {
            HikariConfig jdbcConfig = new HikariConfig();
            jdbcConfig.setPoolName(poolName);
            jdbcConfig.setMaximumPoolSize(EnvUtils.getMaxPoolSize());
            jdbcConfig.setMinimumIdle(EnvUtils.getMinIdleCount());
            jdbcConfig.setJdbcUrl(EnvUtils.getDbUrl());
            jdbcConfig.setUsername(EnvUtils.getDbUser());
            jdbcConfig.setPassword(EnvUtils.getDbPassword());

            jdbcConfig.addDataSourceProperty("cachePrepStmts", true);
            jdbcConfig.addDataSourceProperty("prepStmtCacheSize", 256);
            jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
            jdbcConfig.addDataSourceProperty("useServerPrepStmts", true);

            hds = new HikariDataSource(jdbcConfig);
        }
        return hds;
    }
}
