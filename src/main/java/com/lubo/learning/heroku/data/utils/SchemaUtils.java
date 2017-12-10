package com.lubo.learning.heroku.data.utils;

import com.lubo.learning.heroku.GlobalConstants;
import com.lubo.learning.heroku.data.holder.DBVersion;
import com.lubo.learning.heroku.utils.ResourceLoader;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SchemaUtils {

    private static final Logger logger = Logger.getLogger(SchemaUtils.class.getName());

    /**
     * validates application can connect to database
     * validates database schema version
     * on success:
     *  - application can connect
     *  - database schema is at expected version
     * on failure
     *  - attempts to create DB schema
     */
    public static void ensureDB() {
        HikariDataSource hds = ConnectionPool.getDataSource();
        Sql2o sql2o = new Sql2o(hds);
        Connection con = sql2o.open();
        try {
            List<DBVersion> dbVersionList = con.
                    createQuery("SELECT * FROM db_version order by major desc, minor desc, build desc").
                    executeAndFetch(DBVersion.class);
            if (dbVersionList.isEmpty()) {
                // TODO: need to figure out correct action - db_version table exists however no record was found
            }
            // TODO: identify, whether correct version is present
        } catch (Sql2oException e) {
            try {
                PSQLException psqlException = (PSQLException) e.getCause();
                if (psqlException.getSQLState().equals("42P01")) {
                    // attempt to recover
                    logger.info("attempting to recover - creating DB schema");
                    createDB(con);
                    logger.info("creating DB schema complete");
                } else {
                    // exception not known, unable to recover
                    logger.info("unable to decide on recovery procedure, acquired PostgreSQL exception: " +
                            psqlException.getMessage());
                    System.exit(GlobalConstants.ErrorCode.UnknownPSQLException.getCode());
                }
            } catch (ClassCastException cce) {
                logger.info("exception if unexpected type: " + e.getClass().getName());
                System.exit(GlobalConstants.ErrorCode.UnknownSql2oException.getCode());
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    // for some - currently unknown reason, this code does not work properly on heroku.
    public static void createDB(Connection con) {
        ResourceLoader rl = new ResourceLoader();
        List<String> dbVersions = rl.getResourceFiles("/pgSql");
        logger.info("starting DB creation");
        logger.info("found resource count: " + dbVersions.size());
        dbVersions.forEach((version)->{
            List<String> files = rl.getResourceFiles(version);
            files.forEach((file)->{
                logger.info("----------------------------------------");
                String s = rl.loadFile(file);
                logger.info(s);
                logger.info("----------------------------------------");
                con.createQuery(s).executeUpdate();
            });
        });
    }

}
