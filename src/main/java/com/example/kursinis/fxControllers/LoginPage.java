package com.example.kursinis.fxControllers;

import com.example.kursinis.model.Account.AccountType;
import com.example.kursinis.model.Session;

import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginPage {

    public TextField loginField;
    public PasswordField passwordField;
    public static Session session;
    public ObjectMapper mapper = new ObjectMapper();

    public void validateLogin() throws IOException {

        if (!loginField.getText().isBlank() || !passwordField.getText().isBlank()) {

            String loginResponse = CallEndpoints.Get("http://localhost:8080/login?userName=" + loginField.getText() + "&password=" + passwordField.getText());
            if (succesfulLogin(loginResponse)) {
                session = mapper.readValue(loginResponse,Session.class);
                launchAccountOverview(session);
            } else {
                FxUtils.alert(Alert.AlertType.ERROR, "Error", "Incorrect login or password", "Please check your credentials");
            }
        }
        else {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Missing fields", "Login and Password cannot be empty");
        }
    }

    public void register() {
        FxUtils.openFxPage("register-page.fxml", loginField);
    }

    public void launchAccountOverview(Session session) {
        if (session.getType() == AccountType.MANAGER || session.getType() == AccountType.ADMIN) FxUtils.openFxPage("manager-main-page.fxml", loginField);
        else {
            FxUtils.openFxPage("user-main-page.fxml", loginField);
        }
    }

    public boolean succesfulLogin(String response) {
        return !response.equals("Invalid username or password");
    }

}
