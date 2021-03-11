package com.codecool.buyourstuff.json;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Export {

    public void exportJson() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export data to JSON file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON file", "*.json"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Serializer.serialize(); //!!!
            try {
                Serializer.writeToFile(file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Wrong output or filepath!");
            }
        }
    }
}
