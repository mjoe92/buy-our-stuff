package com.codecool.buyourstuff.dao;

import java.sql.Connection;

public interface DatabaseConnectionDao {

    Connection getConnection();
}
