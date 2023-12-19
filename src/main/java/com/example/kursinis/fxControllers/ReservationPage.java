package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Account.DtoUser;
import com.example.kursinis.model.Reservation.ParkingLot;
import com.example.kursinis.model.Reservation.ParkingSpace;
import com.example.kursinis.model.Reservation.Reservation;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class ReservationPage implements Initializable {

    public TableView<Reservation> reservationList;
    public TableColumn<Reservation, Timestamp> rCityColumn;
    public TableColumn<Reservation, Timestamp> rStatusColumn;
    public TableColumn<Reservation, String> rAddressColumn;
    public TableColumn<Reservation, Timestamp> rCreatedAtColumn;
    public TableColumn<Reservation, Timestamp> rPriceColumn;
    public TableColumn<Reservation, Timestamp> rStartDColumn;
    public TableColumn<Reservation, Timestamp> rEndDColumn;
    public List<Reservation> reservations;
    public TableView<ParkingLot> parkingLotsList;
    public TableColumn<ParkingLot, String> pCityColumn;
    public TableColumn<ParkingLot, String> pAddressColumn;
    public List<ParkingLot> parkingLots;

    public TableView<ParkingSpace> parkingSpaceList;
    public TableColumn<ParkingSpace, String> psStatusColumn;
    public TableColumn<ParkingSpace, String> psNameColumn;
    public TableColumn<ParkingSpace, Float> psPriceColumn;
    public List<ParkingSpace> parkingSpaces;

    public DatePicker StartDateField;
    public DatePicker endDField;

    public ObjectMapper mapper = new ObjectMapper();
    public ParkingLot selectedCargo;
    public List<DtoUser> users;
    public ChoiceBox carChoice;
    public Button createReservationButton;
    public TextField redirect;


    public void fillReservationList() {
//        getInitialReservations();
//        rCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
//        rStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
//        rAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
//        rStartDColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        rEndDColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
//        rCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
//        rPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//        reservationList.getItems().setAll(reservations);
    }

    public void fillParkingLotsList() {
        getInitialParkingLots();
        pCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        pAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        parkingLotsList.getItems().setAll(parkingLots);
    }
    public void fillParkingSpaces(Long id) {
        getInitialParkingSpaces(id);
        //psStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        psNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        psPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        parkingSpaceList.getItems().setAll(parkingSpaces);
    }

    public void getInitialParkingLots() {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/parkinglot");
        try {
            parkingLots = Arrays.asList(mapper.readValue(response, ParkingLot[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void getInitialParkingSpaces(Long id)
    {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/parkingspace/parkingspaces?parkingLotId=" + id);
        try {
            parkingSpaces = Arrays.asList(mapper.readValue(response, ParkingSpace[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void getInitialReservations() {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/reservation/reservations");
        try {
            reservations = Arrays.asList(mapper.readValue(response, Reservation[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void rowClicked(MouseEvent event){
        ParkingLot clickedParkingLot = parkingLotsList.getSelectionModel().getSelectedItem();
        if(clickedParkingLot != null){
            fillParkingSpaces(clickedParkingLot.getParkingLotId());
        }
    }

//    public void submitTrip(ActionEvent actionEvent) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("destination", destiantionField.getText());
//        jsonObject.put("tripStartDate", StartDateField.getValue().atStartOfDay());
//        jsonObject.put("tripEndDate", endDField.getValue().atStartOfDay());
//        jsonObject.put("merchandiseID", selectedCargo.getParkingLotId());
//        jsonObject.put("driverID", driverChoice.getValue().getEmployeeID());
//        jsonObject.put("status", "0");
//        System.out.println(jsonObject.toString());
//        String response = CallEndpoints.Post("http://localhost:8080/api/v1/createtrip", jsonObject.toString());
//        System.out.println(response);
//        getInitialReservations();
//        fillReservationList();
//    }

    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("main-page.fxml", redirect);
    }
    public void selectCargo(MouseEvent event) {
        selectedCargo = (ParkingLot) parkingLotsList.getSelectionModel().getSelectedItem();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        getInitialReservations();
        getInitialParkingLots();

        fillReservationList();
        fillParkingLotsList();
//        for(DtoUser user : users)
//        {
//            if(user.getType() == AccountType.DRIVER)
//            driverChoice.getItems().add(user);
//        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("reservations-page.fxml"));
    }


    public void submitReservation(ActionEvent actionEvent) {
    }

    public void cancelReservation(ActionEvent actionEvent) {
    }
}
