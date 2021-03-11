package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Deserializer;
import com.codecool.buyourstuff.dao.implementation.file.serializer.Serializer;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoFile implements ProductDao {

    private List<Product> productsMemo = new ArrayList<>();
    private int idCounter;

    private final String url = "src/main/resources/product.json";
    private final Serializer<Product> serializer = new Serializer<Product>(url);
    private final Deserializer<Product> deserializer = new Deserializer<Product>(url, Product.class);

    public ProductDaoFile() {
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
        productsMemo.clear();
        productsMemo = deserializer.deserializeAll();
    }

    private void setIdCounter() {
        if (productsMemo.size() == 0) {
            idCounter = 1;
        } else {
            idCounter = productsMemo.get(productsMemo.size() - 1).getId() + 1;
        }
    }
    @Override
    public void add(Product product) {
        product.setId(idCounter++);
//        data.add(product);
        serializer.serializeOne(product);
    }

    @Override
    public Product find(int id) {
        loadFileDataToMemory();
        return productsMemo
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such product"));
    }

    @Override
    public void remove(int id) {
        loadFileDataToMemory();
        productsMemo.remove(find(id));
        serializer.serializeAll(productsMemo);
    }

    @Override
    public void clear() {
        productsMemo = new ArrayList<>();
        serializer.serializeAll(productsMemo);
    }

    //@Override
    public List<Product> getAll() {
        loadFileDataToMemory();
        return new ArrayList<>(productsMemo);
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        loadFileDataToMemory();
        return productsMemo
                .stream()
                .filter(
                        t -> t.getSupplier().getId() == (supplier.getId())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        loadFileDataToMemory();
        return productsMemo
                .stream()
                .filter(
                        t -> t.getProductCategory().getId() == (productCategory.getId())
                )
                .collect(Collectors.toList());
    }
}
