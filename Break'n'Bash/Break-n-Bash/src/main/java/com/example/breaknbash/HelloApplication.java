package com.example.breaknbash;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloApplication {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}