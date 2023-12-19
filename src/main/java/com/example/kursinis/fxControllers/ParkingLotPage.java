package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Reservation.ParkingLot;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ParkingLotPage implements Initializable {

    public TableView<ParkingLot> cargoTable;
    public TableColumn<ParkingLot, String> idField;
    public TableColumn<ParkingLot, String> valueField;
    public TableColumn<ParkingLot, String> descriptionField;
    public TextField submitValue;
    public TextField submitDesc;
    public TextField editValue;
    public TextField editDescr;
    public ObjectMapper mapper = new ObjectMapper();
    public List<ParkingLot> cargos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getInitialCargo();
        fillTable();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("forum-page-managers.fxml"));
    }

    public void fillTable() {
        idField.setCellValueFactory(new PropertyValueFactory<>("id"));
        valueField.setCellValueFactory(new PropertyValueFactory<>("value"));
        descriptionField.setCellValueFactory(new PropertyValueFactory<>("description"));
        cargoTable.getItems().addAll(cargos);
    }

    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("main-page.fxml", submitValue);
    }

    public void getInitialCargo() {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/cargos");
        try {
            cargos = Arrays.asList(mapper.readValue(response, ParkingLot[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public void submitCargo(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();
        json.put("value", Long.parseLong(submitValue.getText()));
        json.put("description", submitDesc.getText());
        if(CallEndpoints.Post("http://localhost:8080/api/v1/createcargo", json.toString()).equals("200")) {
            cargoTable.getItems().clear();
            getInitialCargo();
            fillTable();
            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Cargo created successfully","");
        }
        else {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Cargo creation failed", "");
        }

    }

    public void updateCargo(ActionEvent actionEvent) {
        String Url = "http://localhost:8080/api/v1/update/cargo/" +cargoTable.getSelectionModel().getSelectedItem().getParkingLotId();
        Url+= "?value=" + editValue.getText();
        Url+= "&description=" + editDescr.getText().replace(" ", "%20");
        if(CallEndpoints.Put(Url, "").equals("200")) {
            cargoTable.getItems().clear();
            getInitialCargo();
            fillTable();
            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Cargo edited successfully","");
        }
        else {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Cargo edit failed", "");
        }
    }

    @FXML
    public void rowClicked(MouseEvent event) {
        ParkingLot clickedCargo = cargoTable.getSelectionModel().getSelectedItem();

        if (clickedCargo != null) {
            editValue.setText(clickedCargo.getValue().toString());
            editDescr.setText(clickedCargo.getDescription());
        }
    }


}
