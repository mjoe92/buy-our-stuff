package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.DatabaseDataSourceDao;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DatabaseDataSourceDaoJDBC implements DatabaseDataSourceDao {

    private final String host = "localhost";
    private final int port = 5432;
    private final String dbName;
    private final String userName;
    private final String password;

    /*
     * Add your own values in
     * com.codecool.buyourstuff.dao.implementation.database.Security.
     */

    public DatabaseDataSourceDaoJDBC() {
        dbName = Security.DB_NAME.getPd();
        userName = Security.USER_NAME.getPd();
        password = Security.PASSWORD.getPd();
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