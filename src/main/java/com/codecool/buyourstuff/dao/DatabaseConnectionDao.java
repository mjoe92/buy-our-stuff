package com.codecool.buyourstuff.dao;

import javax.sql.DataSource;

public interface DatabaseConnectionDao {

    DataSource createDataSource();
}
