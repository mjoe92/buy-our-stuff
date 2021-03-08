package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.DatabaseConnectionDao;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DatabaseConnectionDaoJDBC implements DatabaseConnectionDao {

    private final String host = "localhost";
    private final int port = 5432;
    private final String dbName;
    private final String userName;
    private final String password;

    /*
     * Add your own values in
     * com.codecool.buyourstuff.dao.implementation.database.Security.
     */

    public DatabaseConnectionDaoJDBC() {
        dbName = "database";       //DB_NAME.getPd();
        userName = "postgres";     //USER_NAME.getPd();
        password = "asdfqwer";     //PASSWORD.getPd();
    }

    // Delete later if not needed
//
//    @Override
//    public Connection getConnection() {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:postgresql://" + host + ":5432/" + dbName, userName, password);
//        } catch (SQLException sqle) {
//            throw new RuntimeException("DatabaseDaoJdbc getConnection(): " + sqle.getSQLState());
//        }
//        return connection;
//    }


    @Override
    public DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(host);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(dbName);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        return dataSource;
    }



}
