package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoFile implements SupplierDao {

    private final String url = "src/main/resources/supplier.json";
    private final Serializer<Supplier> serializer = new Serializer(url);

    private List<Supplier> data = new ArrayList<>();

    public SupplierDaoFile() {
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
    public Supplier add(Supplier supplier) {
        supplier.setId(data.size() + 1);
//        data.add(supplier);
        serializer.serializeOne(supplier);
        return supplier;
    }

    @Override
    public Supplier find(int id) {
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such supplier"));
    }

    @Override
    public Supplier findByName(String name) {
        return data
                .stream()
                .filter(t -> t.getName() == name)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such supplier"));
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }

    @Override
    public List<Supplier> getAll() {
        return data;
    }

}
