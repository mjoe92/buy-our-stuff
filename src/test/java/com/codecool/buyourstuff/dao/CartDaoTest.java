package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.dao.implementation.database.DatabaseDataSourceDaoJDBC;
import com.codecool.buyourstuff.model.Cart;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class CartDaoTest {

    private static CartDao cartDao;
    private static Connection connection;
    private static Statement statement;

    @BeforeAll
    public static void setup() throws SQLException {
        DatabaseDataSourceDao source = new DatabaseDataSourceDaoJDBC();
        DataSource dataSource = source.createDataSource();
        connection = dataSource.getConnection();
    }

    @BeforeEach
    void init() throws SQLException {
        statement.execute("DROP TABLE IF EXISTS public.cart;");
        statement.execute("CREATE TABLE IF NOT EXISTS public.cart(" +
                "id SERIAL PRIMARY KEY, " +
                "currency TEXT NOT NULL);");
    }

    @Test
    public void testAdd() {
        Cart testCart = new Cart();
    }

    @Test
    void doSomeTesting() {
        assertTrue(3 == 3);
    }

}
