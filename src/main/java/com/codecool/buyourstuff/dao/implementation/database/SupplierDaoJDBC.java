package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Supplier;

import java.sql.Connection;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {

    private Connection connection;

    public SupplierDaoJDBC(Connection connection) {
        this.connection = connection;
        createTable();
    }

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

    }

    @Override
    public void clear() {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }

}
