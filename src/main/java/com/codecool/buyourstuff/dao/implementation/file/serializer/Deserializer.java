package com.codecool.buyourstuff.dao.implementation.file.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Deserializer<T> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String filename;
    private final Class<T> type;

    public Deserializer(String filename, Class<T> type) {
        this.filename = filename;
        this.type = type;
    }
    public List<T> deserializeAll() {
        List<T> deserializedList = new ArrayList<>();
        try {
            List<String> JSONList = readFile();
            for (String JSONItem : JSONList) {
                deserializedList.add(mapper.readValue(JSONItem, this.type));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return deserializedList;
    }

    private List<String> readFile() throws IOException {
        List<String> JSONList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.filename));
            String nextLine = reader.readLine();
            while (nextLine != null) {
                JSONList.add(nextLine);
                nextLine = reader.readLine();
            }
            reader.close();
        }  catch (IOException IOE) {
            throw new IOException("Error while reading", IOE);
        }
        return JSONList;
    }
}
