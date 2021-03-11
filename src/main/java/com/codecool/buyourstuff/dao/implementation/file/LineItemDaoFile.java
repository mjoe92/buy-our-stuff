package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Deserializer;
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

    private List<LineItem> lineItemsMemo = new ArrayList<>();
    private int idCounter;

    private final String url = "src/main/resources/line_item.json";
    private final Serializer<LineItem> serializer = new Serializer<LineItem>(url);
    private final Deserializer<LineItem> deserializer = new Deserializer<LineItem>(url, LineItem.class);

    public LineItemDaoFile() {
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
        lineItemsMemo.clear();
        lineItemsMemo = deserializer.deserializeAll();
    }

    private void setIdCounter() {
        if (lineItemsMemo.size() == 0) {
            idCounter = 1;
        } else {
            idCounter = lineItemsMemo.get(lineItemsMemo.size() - 1).getId() + 1;
        }
    }

    @Override
    public void add(LineItem lineItem) {
        lineItem.setId(idCounter++);
//        data.add(lineItem);
        serializer.serializeOne(lineItem);
    }

    @Override
    public void remove(LineItem lineItem) {
        loadFileDataToMemory();
        lineItemsMemo = lineItemsMemo
                .stream()
                .filter(
                        item -> item.getId() != lineItem.getId()
                ).collect(Collectors.toCollection(ArrayList::new));
        serializer.serializeAll(lineItemsMemo);
    }

    @Override
    public void clear() {
        lineItemsMemo = new ArrayList<>();
        serializer.serializeAll(lineItemsMemo);
    }

    @Override
    public void update(LineItem lineItem, int quantity) {
        loadFileDataToMemory();
        lineItemsMemo
                .stream()
                .filter(
                        item -> item.getId() == lineItem.getId()
                )
                .findFirst()
                .ifPresent(
                        item -> item.setQuantity(quantity)
                );
        serializer.serializeAll(lineItemsMemo);
    }

    @Override
    public LineItem find(int id) {
        loadFileDataToMemory();
        return lineItemsMemo
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such line-item"));
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        loadFileDataToMemory();
        return lineItemsMemo
                .stream()
                .filter(
                        lineItem -> lineItem.getCartId() == cart.getId()
                )
                .collect(Collectors.toList());
    }
}
