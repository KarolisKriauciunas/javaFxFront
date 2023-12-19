package com.example.kursinis.fxControllers;

import com.example.kursinis.utilities.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainPage {

    @FXML
    public TextField redirect;

    public void openReservations(ActionEvent actionEvent) {
        FxUtils.openFxPage("reservations-page.fxml", redirect);
    }

    public void openVehicles(ActionEvent actionEvent) {
        FxUtils.openFxPage("vehicle-page.fxml", redirect);
    }


    public void openProfile(ActionEvent actionEvent) {
        FxUtils.openFxPage("edit-user-profile.fxml", redirect);
    }

    public void logOut(ActionEvent actionEvent) {
        FxUtils.openFxPage("login-page.fxml", redirect);
    }

    public void openFAQ(ActionEvent actionEvent) {FxUtils.openFxPage("FAQ.fxml", redirect);}
}
