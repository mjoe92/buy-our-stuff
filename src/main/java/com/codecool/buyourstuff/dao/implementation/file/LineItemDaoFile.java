package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.LineItem;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LineItemDaoFile implements LineItemDao {

    private List<LineItem> data = new ArrayList<>();

    private final String url = "src/main/resources/line_item.json";
    private final Serializer<LineItem> serializer = new Serializer(url);

    public LineItemDaoFile() {
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
    public void add(LineItem lineItem) {

//        data.add(lineItem);
        serializer.serializeOne(lineItem);

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
        data
                .stream()
                .filter(
                        item -> item.getId() == lineItem.getId()
                )
                .findFirst()
                .ifPresent(
                        item -> item.setQuantity(quantity)
                );
    }

    @Override
    public LineItem find(int id) {
        return data
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such line-item"));
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        return data
                .stream()
                .filter(
                        lineItem -> lineItem.getCartId() == cart.getId()
                )
                .collect(Collectors.toList());
    }
}
