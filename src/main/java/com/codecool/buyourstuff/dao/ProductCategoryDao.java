package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    void createTable();
    ProductCategory add(ProductCategory category);
    ProductCategory find(int id);
    ProductCategory findByName(String Name);
    void remove(int id);
    void clear();
    List<ProductCategory> getAll();
}
