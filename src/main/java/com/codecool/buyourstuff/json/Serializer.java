package com.codecool.buyourstuff.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

    public static final ObjectMapper converter = new ObjectMapper();

    public static String cartJSON;
    public static String lineItemJSON;
    public static String productCategoryJSON;
    public static String productJSON;
    public static String supplierJSON;
    public static String userJSON;

    public static void serialize() {
        cartSerialize();
        lineItemSerialize();
        productCategorySerialize();
        productSerialize();
        supplierSerialize();
        userSerialize();
    }

    public static void writeToFile(String fileName) throws IOException {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.append(cartJSON)
                    .append(lineItemJSON)
                    .append(productCategoryJSON)
                    .append(productJSON)
                    .append(supplierJSON)
                    .append(userJSON)
                    .flush();
        } catch (IOException e) {
            throw new IOException("Error while writing");
        }
    }


    public static void cartSerialize() {
        try {
            cartJSON = converter.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(gameMapJSON);
    }

    public static void lineItemSerialize() {
        try {
            lineItemJSON = converter.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void productCategorySerialize() {
        try {
            productCategoryJSON = converter.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void productSerialize() {
        try {
            productJSON = converter.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void supplierSerialize() {
        try {
            supplierJSON = converter.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void userSerialize() {
        try {
            userJSON = converter.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
