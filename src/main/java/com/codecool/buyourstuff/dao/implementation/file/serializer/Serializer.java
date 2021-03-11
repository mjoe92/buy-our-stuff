package com.codecool.buyourstuff.dao.implementation.file.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Serializer<T> {

    public final ObjectMapper mapper = new ObjectMapper();
    public final String filename;

    public Serializer(String filename) {
        this.filename = filename;
    }

    public void serializeOne(T item) {
        String JSONString = null;
        try {
            JSONString = mapper.writeValueAsString(item);
            writeFile(JSONString, true);
        } catch (JsonProcessingException je) {
            System.out.println(je.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        System.out.println(JSONString);
    }

    public void serializeAll(List<T> list) {
        String JSONString = null;
        try {
            JSONString = mapper.writerFor(new TypeReference<List<T>>(){}).writeValueAsString(list);
            writeFile(JSONString, false);
        } catch (JsonProcessingException je) {
            System.out.println(je.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        System.out.println(JSONString);
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
