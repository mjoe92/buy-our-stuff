package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.Supplier;

import java.util.List;

public interface SupplierDao {
    void createTable();
    Supplier add(Supplier supplier);
    Supplier find(int id);
    Supplier findByName(String name);
    void remove(int id);
    void clear();
    List<Supplier> getAll();
}
