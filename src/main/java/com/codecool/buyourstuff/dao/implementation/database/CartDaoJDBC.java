package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.Cart;

import javax.sql.DataSource;
import java.util.List;

public class CartDaoJDBC implements CartDao {

    private DataSource dataSource;

    public CartDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {

    }

    @Override
    public void add(Cart cart) {

    }

    @Override
    public Cart find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void clear() {

    }

    @Override
    public List<Cart> getAll() {
        return null;
    }

}
