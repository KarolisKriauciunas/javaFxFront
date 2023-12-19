package com.example.kursinis.fxControllers;

import com.example.kursinis.utilities.FxUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class FAQPage {

    public Button goBackB;
    public TextArea text;


    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("FAQ.fxml", text);
    }
}
