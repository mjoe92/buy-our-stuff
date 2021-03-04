package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.DatabaseConnectionDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codecool.buyourstuff.dao.implementation.database.Security.*;

public class DatabaseConnectionDaoJDBC implements DatabaseConnectionDao {

    private final String dbName;
    private final String userName;
    private final String password;
    private final String host = "localhost";

    /*
     * Add your own values in
     * com.codecool.buyourstuff.dao.implementation.database.Security.
     */
    public DatabaseConnectionDaoJDBC() {
        dbName = DB_NAME.getPd();
        userName = USER_NAME.getPd();
        password = PASSWORD.getPd();
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":5432/" + dbName, userName, password);
        } catch (SQLException sqle) {
            throw new RuntimeException("DatabaseDaoJdbc getConnection(): " + sqle.getSQLState());
        }
        return connection;
    }

    // Delete later if not needed

//    @Override
//    public DataSource setDataSource() {
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setDatabaseName(dbName);
//        dataSource.setUser(userName);
//        dataSource.setPassword(password);
//        return dataSource;
//    }

}
