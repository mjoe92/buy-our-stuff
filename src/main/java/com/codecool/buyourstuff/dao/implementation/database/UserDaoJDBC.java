package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.User;

import java.sql.Connection;

public class UserDaoJDBC implements UserDao {

    private Connection connection;

    public UserDaoJDBC(Connection connection) {
        this.connection = connection;
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
