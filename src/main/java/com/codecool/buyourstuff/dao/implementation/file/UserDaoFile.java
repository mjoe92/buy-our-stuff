package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Deserializer;
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

    private List<User> usersMemo = new ArrayList<>();
    private int idCounter;

    private final String url = "src/main/resources/user.json";
    private final Serializer<User> serializer = new Serializer<User>(url);
    private final Deserializer<User> deserializer = new Deserializer<User>(url, User.class);

    public UserDaoFile() {
        createTable();
        loadFileDataToMemory();
        setIdCounter();
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

    private void loadFileDataToMemory() {
        usersMemo.clear();
        usersMemo = deserializer.deserializeAll();
    }

    private void setIdCounter() {
        if (usersMemo.size() == 0) {
            idCounter = 1;
        } else {
            idCounter = usersMemo.get(usersMemo.size() - 1).getId() + 1;
        }
    }

    @Override
    public void add(User user) {
        if (isNameAvailable(user.getName())) {
            CartDao cartDao = DataManager.getCartDao();
            Cart cart = new Cart();
            cartDao.add(cart);

            user.setCartId(cart.getId());
            user.setId(idCounter++);
//            users.add(user);
            serializer.serializeOne(user);
        }
    }

    @Override
    public User find(String name, String password) {
        loadFileDataToMemory();
        return usersMemo
                .stream()
                .filter(user -> user.getName().equals(name) && BCrypt.checkpw(password, user.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such user"));
    }

    @Override
    public void clear() {
        usersMemo = new ArrayList<>();
        serializer.serializeAll(usersMemo);
    }

    @Override
    public boolean isNameAvailable(String name) {
        loadFileDataToMemory();
        return usersMemo
                .stream()
                .map(User::getName)
                .noneMatch(username -> username.equals(name));
    }
}
