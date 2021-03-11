package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CartDaoFile implements CartDao {

    private List<Cart> data = new ArrayList<>();

    @Override
    public void createTable() {}

    @Override
    public void add(Cart cart) {

    }

    @Override
    public Cart find(int id) {
        return null;

    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }

    //@Override
    public List<Cart> getAll() {
        return data;
    }
}
