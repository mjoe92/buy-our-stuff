package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.LineItem;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LineItemDaoFile implements LineItemDao {

    private List<LineItem> data = new ArrayList<>();

    @Override
    public void createTable() {

    }

    @Override
    public void add(LineItem lineItem) {
        data.add(lineItem);
    }

    @Override
    public void remove(LineItem lineItem) {
        data.remove(lineItem);
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
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
