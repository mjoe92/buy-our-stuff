package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.User;

import javax.sql.DataSource;

public class UserDaoJDBC implements UserDao {

    private DataSource dataSource;

    public UserDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public User find(String userName, String password) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isNameAvailable(String username) {
        return false;
    }

}
