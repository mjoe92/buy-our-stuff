package com.codecool.buyourstuff.json;

import com.codecool.buyourstuff.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Deserializer {

    public static final ObjectMapper converter = new ObjectMapper();
    public static String cartJSON;
    public static String lineItemJSON;
    public static String productCategoryJSON;
    public static String productJSON;
    public static String supplierJSON;
    public static String userJSON;

    public static Cart deserializedCart;
    public static LineItem deserializedLineItem;
    public static ProductCategory deserializedProductCategory;
    public static Product deserializedProduct;
    public static Supplier deserializedSupplier;
    public static User deserializedUser;

    public static void deserialize() {
        cartDeserialize();
        lineItemDeserialize();
        productCategoryDeserialize();
        productDeserialize();
        supplierDeserialize();
        userDeserialize();
    }

    public static void readFile(String fileName) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            cartJSON = reader.readLine();
            lineItemJSON = reader.readLine();
            productCategoryJSON = reader.readLine();
            productJSON = reader.readLine();
            supplierJSON = reader.readLine();
            userJSON = reader.readLine();
            reader.close();
        }  catch (IOException e) {
            throw new IOException("Error while reading");
        }
    }

    public static void cartDeserialize() {
        try {
            deserializedCart = converter.readValue(cartJSON, Cart.class);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void lineItemDeserialize() {
        try {
            deserializedLineItem = converter.readValue(lineItemJSON, LineItem.class);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void productCategoryDeserialize() {
        try {
            deserializedProductCategory = converter.readValue(productCategoryJSON, ProductCategory.class);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void productDeserialize() {
        try {
            deserializedProduct = converter.readValue(productJSON, Product.class);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void supplierDeserialize() {
        try {
            deserializedSupplier = converter.readValue(supplierJSON, Supplier.class);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void userDeserialize() {
        try {
            deserializedUser = converter.readValue(userJSON, User.class);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
