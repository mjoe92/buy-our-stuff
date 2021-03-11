package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoFile implements CartDao {

    private List<Cart> cartsMemo = new ArrayList<>();

    private final String url = "src/main/resources/cart.json";
    private final Serializer<Cart> serializer = new Serializer(url);

    public CartDaoFile() {
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
    public Cart add(Cart cart) {
        cart.setId(cartsMemo.size() + 1);
//        data.add(cart);
        serializer.serializeOne(cart);
        return cart;
    }

    @Override
    public Cart find(int id) {
        return cartsMemo
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such cart"));
    }

    @Override
    public void remove(int id) {
        cartsMemo.remove(find(id));
    }

    @Override
    public void clear() {
        cartsMemo = new ArrayList<>();
    }

    //@Override
    public List<Cart> getAll() {
        return cartsMemo;
    }
}
