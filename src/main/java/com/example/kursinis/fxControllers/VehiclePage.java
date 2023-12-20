package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Vehicle;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class VehiclePage implements Initializable {
    public TableColumn<Vehicle, String> manufacturer;
    public TableColumn<Vehicle, String> value;
    public TableView<Vehicle> vehicleTableView;
    public TextField carNameField;
    public TextField numberPlateField;
    public List<Vehicle> vehicles;
    public ObjectMapper mapper = new ObjectMapper();
    public TextField manField1;

    String response = getUserCars();

    private static String getUserCars() {
        return CallEndpoints.Get("http://localhost:8080/api/v1/vehicle/user?userId=" + LoginPage.session.getEmployeeID());
    }

    public void getInitialVehicles() {
        try {
            vehicles = Arrays.asList(mapper.readValue(response, Vehicle[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("carName"));
        value.setCellValueFactory(new PropertyValueFactory<>("plateNumbers"));
        vehicleTableView.getItems().setAll(vehicles);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getInitialVehicles();
        fillTable();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("vehicle-page.fxml"));
    }

    public void submitVehicle() {
        if (carNameField.getText().isBlank() || numberPlateField.getText().isBlank()) {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Trūksta laukų", "Prašome užpildyti visus laukus");
            return;
        }
        JSONObject json = new JSONObject();
        json.put("carName", carNameField.getText());
        json.put("plateNumbers", numberPlateField.getText());
        json.put("assignedId", LoginPage.session.getEmployeeID());
        System.out.println(json);
        String responsetmp = CallEndpoints.Post("http://localhost:8080/api/v1/vehicle/create", json.toString());
        System.out.println(responsetmp);
        if (responsetmp.equals("200")) {
            FxUtils.alert(Alert.AlertType.INFORMATION, "Pavyko", "Automobilis pridėtas", "");
        } else {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Vartotojas jau turi 2 registruotus transporto priemones", "");
        }
        response = getUserCars();
        getInitialVehicles();
        fillTable();
    }

    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("main-page.fxml", carNameField);
    }
}
