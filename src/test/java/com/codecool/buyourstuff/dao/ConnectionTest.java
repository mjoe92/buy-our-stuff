package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.dao.implementation.database.DatabaseDataSourceDaoJDBC;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionTest {

    private static Connection connection;

    @BeforeAll
    public static void setup() throws SQLException {
        DatabaseDataSourceDao source = new DatabaseDataSourceDaoJDBC();
        DataSource dataSource = source.createDataSource();
        connection = dataSource.getConnection();
    }

    @Test
    public void testGetConnectionNotNull() {
        assertNotNull(connection);

    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.close();
    }
}
