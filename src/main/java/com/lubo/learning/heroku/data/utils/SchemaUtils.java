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
                System.exit(GlobalConstants.ErrorCode.UnknownDatabaseStatus.getCode());
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

    private static void loadDBVersion(Connection con, String version) {
        ResourceLoader rl = new ResourceLoader();
        DBVersion dbv = new DBVersion(version);
        String fileName = "/pgSql/" + dbv.getVersionName() + "/updateDB.sql";
        logger.info(fileName);
        String file = rl.loadFile(fileName);
        logger.info("starting DB update to " + dbv.getVersionName());
        logger.info(file);
        con.createQuery(file).executeUpdate();

    }

    // for some - currently unknown reason, this code does not work properly on heroku.
    private static void createDB(Connection con) {
        ResourceLoader rl = new ResourceLoader();
        DBVersion v = new DBVersion("0.1.1");

        //List<String> versionFiles = rl.getResourceFiles("/pgSql/" + v.getVersionName() + "/updateDB.sql");
        String fileName = "/pgSql/" + v.getVersionName() + "/updateDB.sql";
        logger.info(fileName);
        String file = rl.loadFile(fileName);
        logger.info("starting DB creation");
        logger.info("----------------------------------------");
        logger.info(file);
        logger.info("----------------------------------------");
        con.createQuery(file).executeUpdate();
    }

}
