package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Deserializer;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCategoryDaoFile implements ProductCategoryDao {

    private List<ProductCategory> productCategoriesMemo = new ArrayList<>();
    private int idCounter;

    private final String url = "src/main/resources/product_category.json";
    private final Serializer<ProductCategory> serializer = new Serializer<ProductCategory>(url);
    private final Deserializer<ProductCategory> deserializer = new Deserializer<ProductCategory>(url, ProductCategory.class);

    public ProductCategoryDaoFile() {
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
        productCategoriesMemo.clear();
        productCategoriesMemo = deserializer.deserializeAll();
    }

    private void setIdCounter() {
        if (productCategoriesMemo.size() == 0) {
            idCounter = 1;
        } else {
            idCounter = productCategoriesMemo.get(productCategoriesMemo.size() - 1).getId() + 1;
        }
    }

    @Override
    public ProductCategory add(ProductCategory category) {
        category.setId(idCounter++);
//        data.add(category);
        serializer.serializeOne(category);
        return category;
    }

    @Override
    public ProductCategory find(int id) {
        loadFileDataToMemory();
        return productCategoriesMemo
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such category"));
    }

    @Override
    public ProductCategory findByName(String name) {
        loadFileDataToMemory();
        return productCategoriesMemo
                .stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such category"));
    }

    @Override
    public void remove(int id) {
        loadFileDataToMemory();
        productCategoriesMemo = productCategoriesMemo
                .stream()
                .filter(
                        item -> item.getId() != id
                ).collect(Collectors.toCollection(ArrayList::new));
        serializer.serializeAll(productCategoriesMemo);
    }

    @Override
    public void clear() {
        productCategoriesMemo = new ArrayList<>();
        serializer.serializeAll(productCategoriesMemo);
    }

    @Override
    public List<ProductCategory> getAll() {
        loadFileDataToMemory();
        return new ArrayList<>(productCategoriesMemo);
    }
}
