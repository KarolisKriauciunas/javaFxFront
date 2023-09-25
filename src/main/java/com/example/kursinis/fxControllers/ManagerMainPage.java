package com.example.kursinis.fxControllers;

import com.example.kursinis.utilities.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ManagerMainPage {

    @FXML
    public TextField redirect;

    public void openUsers(ActionEvent actionEvent) {
        FxUtils.openFxPage("view-user-page.fxml", redirect);
    }

    public void openVehicles(ActionEvent actionEvent) {
        FxUtils.openFxPage("vehicle-page.fxml", redirect);
    }

    public void openCargo(ActionEvent actionEvent) {
        FxUtils.openFxPage("cargo-page.fxml", redirect);
    }

    public void openTrips(ActionEvent actionEvent) {
        FxUtils.openFxPage("trip-page.fxml", redirect);
    }

    public void openForum(ActionEvent actionEvent) {
        FxUtils.openFxPage("forum-page-managers.fxml", redirect);
    }

    public void logOut(ActionEvent actionEvent) {
        FxUtils.openFxPage("login-page.fxml", redirect);
    }
}
