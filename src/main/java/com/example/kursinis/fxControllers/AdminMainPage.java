package com.example.kursinis.fxControllers;

import com.example.kursinis.utilities.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminMainPage {
    @FXML
    public TextField redirect;
    public void editReservations(ActionEvent actionEvent) {FxUtils.openFxPage("reservations-page.fxml", redirect);}

    public void logOut(ActionEvent actionEvent) {FxUtils.openFxPage("login-page.fxml", redirect);}

    public void editUsers(ActionEvent actionEvent) {FxUtils.openFxPage("view-user-page.fxml", redirect);}

    public void editParkingLots(ActionEvent actionEvent) {FxUtils.openFxPage("parking-lot-page.fxml", redirect);}
}
