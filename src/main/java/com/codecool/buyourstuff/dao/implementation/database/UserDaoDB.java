package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.User;

public class UserDaoDB implements UserDao {

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
