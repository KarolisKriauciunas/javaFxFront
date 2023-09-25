package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Account.AccountType;
import com.example.kursinis.model.Account.DtoUser;
import com.example.kursinis.model.Trip.Cargo;
import com.example.kursinis.model.Trip.Trip;
import com.example.kursinis.model.Trip.TripStatus;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TripPage implements Initializable {

    public TableView tripList;
    public TableColumn<Trip, String> DestinationCollumn;
    public TableColumn<Trip, Timestamp> StartDCollumn;
    public TableColumn<Trip, Timestamp> EndDCollumn;
    public TableColumn<Trip, TripStatus> statusCollumn;
    public TableColumn<Trip, Long> MerchCollumn;
    public TableColumn<Trip, Long> DriverCollumn;
    public TableColumn<Trip, Long> idCollumn;
    public List<Cargo> cargos;
    public List<Trip> trips;
    public TableView cargoList;
    public TableColumn<Cargo, Long> CIdCollumn;
    public TableColumn<Cargo, String> CDescriptionCollumn;
    public TableColumn<Cargo, Float> CValueCollumn;
    public DatePicker StartDateField;
    public DatePicker endDField;
    public ChoiceBox<DtoUser> driverChoice;
    public TextField destiantionField;
    public ObjectMapper mapper = new ObjectMapper();
    public Cargo selectedCargo;
    public List<DtoUser> users;


    public void fillTripList() {
        DestinationCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        StartDCollumn.setCellValueFactory(new PropertyValueFactory<>("tripStartDate"));
        EndDCollumn.setCellValueFactory(new PropertyValueFactory<>("tripEndDate"));
        statusCollumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        MerchCollumn.setCellValueFactory(new PropertyValueFactory<>("cargoId"));
        DriverCollumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        idCollumn.setCellValueFactory(new PropertyValueFactory<>("tripId"));
        tripList.getItems().setAll(trips);
    }

    public void fillCargoList() {
        CIdCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        CDescriptionCollumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        CValueCollumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        cargoList.getItems().setAll(cargos);
    }

    public void getInitialCargo() {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/cargos");
        try {
            cargos = Arrays.asList(mapper.readValue(response, Cargo[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public void getDrivers()
    {
        String response = CallEndpoints.Get("http://localhost:8080/users");
        try {
            users = Arrays.asList(mapper.readValue(response, DtoUser[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public void getInitialTrips() {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/trips");
        try {
            trips = Arrays.asList(mapper.readValue(response, Trip[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void submitTrip(ActionEvent actionEvent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("destination", destiantionField.getText());
        jsonObject.put("tripStartDate", StartDateField.getValue().atStartOfDay());
        jsonObject.put("tripEndDate", endDField.getValue().atStartOfDay());
        jsonObject.put("merchandiseID", selectedCargo.getId());
        jsonObject.put("driverID", driverChoice.getValue().getEmployeeID());
        jsonObject.put("status", "0");
        System.out.println(jsonObject.toString());
        String response = CallEndpoints.Post("http://localhost:8080/api/v1/createtrip", jsonObject.toString());
        System.out.println(response);
        getInitialTrips();
        fillTripList();
    }

    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("manager-main-page.fxml", destiantionField);
    }
    public void selectCargo(MouseEvent event) {
        selectedCargo = (Cargo) cargoList.getSelectionModel().getSelectedItem();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getInitialCargo();
        getInitialTrips();
        getDrivers();
        fillTripList();
        fillCargoList();
        for(DtoUser user : users)
        {
            if(user.getType() == AccountType.DRIVER)
            driverChoice.getItems().add(user);
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("trip-page.fxml"));
    }


}
