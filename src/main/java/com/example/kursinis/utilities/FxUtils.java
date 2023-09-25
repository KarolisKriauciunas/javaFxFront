package com.example.kursinis.utilities;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Account.AccountType;
import com.example.kursinis.model.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FxUtils {
    public static void alert(Alert.AlertType alertType, String title, String header, String context) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public static void openFxPage(String pageName, TextField textField) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ParcelApplication.class.getResource(pageName));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.setTitle("Parcel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean checkAdminPrivileges(Session session){
        return session.getType().equals(AccountType.ADMIN);
    }
}
