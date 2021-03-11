package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.User;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserDaoFile implements UserDao {

    private List<User> users = new ArrayList<>();

    @Override
    public void createTable() {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public User find(String name, String password) {
        return null;
    }

    @Override
    public void clear() {
        users = new ArrayList<>();
    }

    @Override
    public boolean isNameAvailable(String name) {
        return false;
    }
}
