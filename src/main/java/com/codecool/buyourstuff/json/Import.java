package com.codecool.buyourstuff.json;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Import {

    public void importJson() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import data from JSON file");
        //fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(new Stage());
        boolean isJSONFile = file.getName().toLowerCase().endsWith(".json");
        String cause, option1, option2;
        if (file == null) {
            cause = "No file has been chosen!\nYou OK with this?";
            option1 = "CANCEL";
            option2 = "OK";
            alertDisplay(cause, option1, option2);
        } else if (!isJSONFile) {
            cause = "Invalid file format!\nPlease choose the right JSON file!";
            option1 = "OK";
            option2 = "CANCEL";
            alertDisplay(cause, option1, option2);
        } else {
            try {
                Deserializer.readFile(file.getAbsolutePath());
            } catch (IOException IOE) {
                System.out.println(IOE.getMessage());
            }
            Deserializer.deserialize(); //!!!
        }

    }

    private void alertDisplay(String warning, String option1, String option2) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING!");
        alert.setHeaderText("IMPORT ERROR!");
        alert.setContentText(warning);
        ButtonType buttonTypeOk = new ButtonType(option1);
        ButtonType buttonTypeCancel = new ButtonType(option2);
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        try {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeOk) {
                importJson();
            } else if (result.get() == buttonTypeCancel) {
                alert.close();
            }
        } catch (NullPointerException ignored) {}
    }
}