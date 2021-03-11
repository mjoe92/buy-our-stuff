package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SupplierDaoFile implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();

    @Override
    public void createTable() {

    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
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

    @Override
    public List<Supplier> getAll() {
        return data;
    }
}
