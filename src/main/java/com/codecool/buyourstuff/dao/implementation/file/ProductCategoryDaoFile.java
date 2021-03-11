package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoFile implements ProductCategoryDao {

    private List<ProductCategory> productCategoriesMemo = new ArrayList<>();

    private final String url = "src/main/resources/product_category.json";
    private final Serializer<ProductCategory> serializer = new Serializer(url);

    public ProductCategoryDaoFile() {
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
    public ProductCategory add(ProductCategory category) {
        category.setId(productCategoriesMemo.size() + 1);
//        data.add(category);
        serializer.serializeOne(category);
        return category;
    }

    @Override
    public ProductCategory find(int id) {
        return productCategoriesMemo
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such category"));
    }

    @Override
    public ProductCategory findByName(String name) {
        return productCategoriesMemo
                .stream()
                .filter(t -> t.getName() == name)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such category"));
    }

    @Override
    public void remove(int id) {
        productCategoriesMemo.remove(find(id));
    }

    @Override
    public void clear() {
        productCategoriesMemo = new ArrayList<>();
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoriesMemo;
    }
}
