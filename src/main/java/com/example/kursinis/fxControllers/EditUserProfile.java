package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Account.DtoUser;
import com.example.kursinis.model.Session;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserProfile implements Initializable {

    public TextField name;
    public TextField email;
    public TextField password;
    public TextField username;
    public DtoUser user;
    private static String getUserData() {
        return CallEndpoints.Get("http://localhost:8080/users?userName=" + LoginPage.session.userName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String response = getUserData();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             user = objectMapper.readValue(response.replace("[","").replace("]", ""), DtoUser.class);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillUserFields(user);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("edit-user-profile.fxml"));
    }
    public void fillUserFields(DtoUser user)
    {
        name.setText(user.getFirstName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        username.setText(user.getUserName());
    }

    public void cancel() {
        FxUtils.openFxPage("main-page.fxml", name);
    }
    public void submit()
    {
        if(name.getText().isBlank() || email.getText().isBlank() || password.getText().isBlank() || username.getText().isBlank())
        {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Missing fields", "Please fill all fields");
            return;
        }
        if(!password.getText().matches("^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{2,}$"))
        {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Password must contain at least one number and one uppercase letter", "Please check your password");
            return;
        }
        String urlBase = "http://localhost:8080/update/user/" + LoginPage.session.getEmployeeID() + "?"
                + "firstName=" + name.getText()
                + "&email=" + email.getText()
                + "&password=" + password.getText()
                + "&userName=" + username.getText();
        String responseTmp = CallEndpoints.Put(urlBase, "{}");
        if(responseTmp.equals("200"))
        {
            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "User updated successfully", "User updated successfully");
            LoginPage.session.userName = username.getText();
            FxUtils.openFxPage("main-page.fxml", name);
        }
        else
        {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "User update failed", "Please check your fields and try again");
        }
    }

}
