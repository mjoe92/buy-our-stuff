package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.LineItem;

import javax.sql.DataSource;
import java.util.List;

public class LineItemDaoJDBC implements LineItemDao {

    private DataSource dataSource;

    public LineItemDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {

    }

    @Override
    public void add(LineItem lineItem) {

    }

    @Override
    public void remove(LineItem lineItem) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void update(LineItem lineItem, int quantity) {

    }

    @Override
    public LineItem find(int id) {
        return null;
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        return null;
    }

}
