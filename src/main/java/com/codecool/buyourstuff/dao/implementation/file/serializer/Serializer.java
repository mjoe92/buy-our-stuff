package com.codecool.buyourstuff.dao.implementation.file.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Serializer<T> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String filename;

    public Serializer(String filename) {
        this.filename = filename;
    }

    public void serializeOne(T item) {
        String JSONString = null;
        try {
            JSONString = mapper.writeValueAsString(item);
            writeFile(JSONString, true);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void serializeAll(List<T> list) {
        StringBuilder JSONString = new StringBuilder();
        try {
            String lineSeparator = "";
            for (T item : list) {
                JSONString.append(mapper.writeValueAsString(item));
                JSONString.append(lineSeparator);
                lineSeparator = "\n";
            }
                writeFile(JSONString.toString(), false);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private void writeFile(String JSONString, boolean appendMode) throws IOException {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(this.filename, appendMode);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(JSONString);
            printWriter.close();
        } catch (IOException IOE) {
            throw new IOException("Error while writing", IOE);
        }
    }
}
