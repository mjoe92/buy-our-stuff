package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoFile implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();

    @Override
    public void createTable() {

    }

    @Override
    public void add(ProductCategory category) {
    }

    @Override
    public ProductCategory find(int id) {
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
    public List<ProductCategory> getAll() {
        return data;
    }
}
