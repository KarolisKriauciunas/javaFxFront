package com.example.kursinis.fxControllers;

import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class PasswordReset {

    @FXML
    TextField resetEmail;
    public void reset(ActionEvent actionEvent) {
        if(resetEmail.getText().isBlank())
        {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Missing fields", "Prašau užpildyti visus laukus");
            return;
        }
    String email = resetEmail.getText();
        String newPassword = CallEndpoints.PutWithMessage("http://localhost:8080/reset?email=" + email, "");
        FxUtils.alert(Alert.AlertType.INFORMATION,"Naujas slaptažodis", "Jusu naujas slaptažodis yra: " + newPassword, "Prisijungus pakeiskite slaptažodį");
    }

    public void cancel(ActionEvent actionEvent) {
        FxUtils.openFxPage("login-page.fxml", resetEmail);
    }
}
