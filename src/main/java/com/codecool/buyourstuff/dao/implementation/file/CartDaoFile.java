package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Deserializer;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoFile implements CartDao {

    private List<Cart> cartsMemo = new ArrayList<>();
    private int idCounter;

    private final String url = "src/main/resources/cart.json";
    private final Serializer<Cart> serializer = new Serializer<Cart>(url);
    private final Deserializer<Cart> deserializer = new Deserializer<Cart>(url, Cart.class);

    public CartDaoFile() {
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
        cartsMemo.clear();
        cartsMemo = deserializer.deserializeAll();
    }

    private void setIdCounter() {
        if (cartsMemo.size() == 0) {
            idCounter = 1;
        } else {
            idCounter = cartsMemo.get(cartsMemo.size() - 1).getId() + 1;
        }
    }

    @Override
    public Cart add(Cart cart) {
        cart.setId(idCounter++);
//        data.add(cart);
        serializer.serializeOne(cart);
        return cart;
    }

    @Override
    public Cart find(int id) {
        loadFileDataToMemory();
        return cartsMemo
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such cart"));
    }

    @Override
    public void remove(int id) {
        loadFileDataToMemory();
        cartsMemo.remove(find(id));
        serializer.serializeAll(cartsMemo);
    }

    @Override
    public void clear() {
        cartsMemo = new ArrayList<>();
        serializer.serializeAll(cartsMemo);
    }

    //@Override
    public List<Cart> getAll() {
        loadFileDataToMemory();
        return cartsMemo;
    }
}
