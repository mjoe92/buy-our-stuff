package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.User;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoFile implements UserDao {

    private List<User> users = new ArrayList<>();

    private final String url = "src/main/resources/user.json";
    private final Serializer<User> serializer = new Serializer(url);

    public UserDaoFile() {
        createTable();
    }

    @Override
    public void createTable() {
        try {
            File file = new File(url);
            file.createNewFile();       // doesn't do anything if file exists
        } catch (IOException ioe) {
            System.out.println("Exception while creating" + url);
        }
    }

    @Override
    public void add(User user) {
        if (isNameAvailable(user.getName())) {
            CartDao cartDao = DataManager.getCartDao();
            Cart cart = new Cart();
            cartDao.add(cart);

            user.setCartId(cart.getId());
            user.setId(users.size() + 1);
//            users.add(user);
            serializer.serializeOne(user);

        }
    }

    @Override
    public User find(String name, String password) {
        return users
                .stream()
                .filter(user -> user.getName().equals(name) && BCrypt.checkpw(password, user.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such user"));
    }

    @Override
    public void clear() {
        users = new ArrayList<>();
    }

    @Override
    public boolean isNameAvailable(String name) {
        return users
                .stream()
                .map(User::getName)
                .noneMatch(username -> username.equals(name));
    }
}
