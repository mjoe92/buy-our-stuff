package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Deserializer;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoFile implements SupplierDao {

    private List<Supplier> suppliersMemo = new ArrayList<>();
    private int idCounter;

    private final String url = "src/main/resources/supplier.json";
    private final Serializer<Supplier> serializer = new Serializer<Supplier>(url);
    private final Deserializer<Supplier> deserializer = new Deserializer<Supplier>(url, Supplier.class);

    public SupplierDaoFile() {
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
        suppliersMemo.clear();
        suppliersMemo = deserializer.deserializeAll();
    }

    private void setIdCounter() {
        if (suppliersMemo.size() == 0) {
            idCounter = 1;
        } else {
            idCounter = suppliersMemo.get(suppliersMemo.size() - 1).getId() + 1;
        }
    }

    @Override
    public Supplier add(Supplier supplier) {
        supplier.setId(idCounter++);
//        data.add(supplier);
        serializer.serializeOne(supplier);
        return supplier;
    }

    @Override
    public Supplier find(int id) {
        loadFileDataToMemory();
        return suppliersMemo
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such supplier"));
    }

    @Override
    public Supplier findByName(String name) {
        loadFileDataToMemory();
        return suppliersMemo
                .stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such supplier"));
    }

    @Override
    public void remove(int id) {
        loadFileDataToMemory();
        suppliersMemo.remove(find(id));
        serializer.serializeAll(suppliersMemo);
    }

    @Override
    public void clear() {
        suppliersMemo = new ArrayList<>();
        serializer.serializeAll(suppliersMemo);
    }

    @Override
    public List<Supplier> getAll() {
        loadFileDataToMemory();
        return suppliersMemo;
    }

}
