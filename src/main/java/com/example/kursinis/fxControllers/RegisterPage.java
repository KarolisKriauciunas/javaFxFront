package com.example.kursinis.fxControllers;

import com.example.kursinis.model.Account.AccountType;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import javafx.scene.control.*;

import java.util.Objects;
import org.json.JSONObject;

public class RegisterPage {

    public TextField name;
    public TextField surname;
    public TextField email;
    public TextField login;
    public PasswordField password;
    public PasswordField repeatPassword;

    public void cancel() {
        FxUtils.openFxPage("login-page.fxml", name);
    }

    public void submit() {

        if (name.getText().isBlank()
                || surname.getText().isBlank()
                || email.getText().isBlank()
                || login.getText().isBlank()
                || password.getText().isBlank()) {
            FxUtils.alert(Alert.AlertType.WARNING, "Warning", "Missing fields", "Please fill out all fields");
        } else if (!Objects.equals(password.getText(), repeatPassword.getText())) {
            FxUtils.alert(Alert.AlertType.WARNING, "Warning", "Passwords must match", "");
        }
        else
        {

                JSONObject json = new JSONObject();

                json.put("firstName", name.getText());
                json.put("lastName", surname.getText());
                json.put("email", email.getText());
                json.put("userName", login.getText());
                json.put("password", password.getText());
                json.put("type", AccountType.DRIVER.toString());
                json.put("salary", 0);
                json.put("id", 0);

                CallEndpoints.Post("http://localhost:8080/registration", json.toString());

                FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Registration complete", "You will now we redirected to login page");
                FxUtils.openFxPage("login-page.fxml", login);
            }
        }
    }