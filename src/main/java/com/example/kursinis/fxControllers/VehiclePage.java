package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;

import com.example.kursinis.model.Vehicle;
import com.example.kursinis.model.VehicleType;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    //public TableColumn<Vehicle, Long> EmployeeId;
    public TableColumn<Vehicle, String> manufacturer;
   // public TableColumn<Vehicle, VehicleType> type;
    public TableColumn<Vehicle, String> value;
  //  public TableColumn<Vehicle, Long> vID;
    public ObservableList<String> statusChoices = FXCollections.observableArrayList("TRUCK", "VAN", "PICKUP", "SEDAN");
    public TableView<Vehicle> vehicleTableView;
    public ChoiceBox<String> types;
    public TextField manField;
    public TextField yearField;
    public TextField valueField;
    public List<Vehicle> vehicles;
    public ObjectMapper mapper = new ObjectMapper();

    String response = CallEndpoints.Get("http://localhost:8080/api/v1/vehicles");

    public void getInitialVehicles()
    {
        try {
            vehicles = Arrays.asList(mapper.readValue(response, Vehicle[].class));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void fillTable() {
        //EmployeeId.setCellValueFactory(new PropertyValueFactory<>("assignedId"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
      //  type.setCellValueFactory(new PropertyValueFactory<>("type"));
        value.setCellValueFactory(new PropertyValueFactory<>("Value"));
      //  vID.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        vehicleTableView.getItems().setAll(vehicles);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        types.setItems(statusChoices);
        types.setValue("TRUCK");
        getInitialVehicles();
        fillTable();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("vehicle-page.fxml"));
    }

    public void submitVehicle()
    {
        JSONObject json = new JSONObject();
        json.put("manufacturer", manField.getText());
        json.put("creationYear", "1999-12-12");
        json.put("value", valueField.getText());
        json.put("type", types.getValue());
        json.put("assignedId", LoginPage.session.getEmployeeID());
        json.put("completedTrips", 0L);
        json.put("lastService", "2020");
        if(CallEndpoints.Post("http://localhost:8080/api/v1/createvehicle", json.toString()).equals("200"))
        {
            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Vehicle created", "");
        }
        else
        {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Vehicle not created", "");
        }
        response = CallEndpoints.Get("http://localhost:8080/api/v1/vehicles");
        getInitialVehicles();
        fillTable();
    }
    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("main-page.fxml",manField);
    }
}
