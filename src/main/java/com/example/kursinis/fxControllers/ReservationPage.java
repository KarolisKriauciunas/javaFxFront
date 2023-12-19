package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.City;
import com.example.kursinis.model.Reservation.ParkingLot;
import com.example.kursinis.model.Reservation.ParkingSpace;
import com.example.kursinis.model.Reservation.Reservation;
import com.example.kursinis.model.Reservation.ReservationStatus;
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
    public List<Reservation> reservationsFull;
    public TableView<ParkingLot> parkingLotsList;
    public TableColumn<ParkingLot, String> pCityColumn;
    public TableColumn<ParkingLot, String> pAddressColumn;
    public List<ParkingLot> parkingLotsFull;

    public TableView<ParkingSpace> parkingSpaceList;
    public TableColumn<ParkingSpace, String> psStatusColumn;
    public TableColumn<ParkingSpace, String> psNameColumn;
    public TableColumn<ParkingSpace, Float> psPriceColumn;
    public List<ParkingSpace> parkingSpaces;

    public DatePicker StartDateField;
    public DatePicker endDField;

    public ObjectMapper mapper = new ObjectMapper();
    //city choice needs to be values from City enum
    public ChoiceBox<City> cityChoice;
    public Button createReservationButton;
    public TextField redirect;

    public Long selectedParkingSpaceId;
    public Long selectedParkingLotId;



    //fill reservation list
    public void fillReservationList(List <Reservation> reservations) {
        rCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        rAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        rStatusColumn.setCellValueFactory(new PropertyValueFactory<>("reservationStatus"));
        rPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        rStartDColumn.setCellValueFactory(new PropertyValueFactory<>("reservationStartDate"));
        rEndDColumn.setCellValueFactory(new PropertyValueFactory<>("reservationEndDate"));

        reservationList.getItems().setAll(reservations);
    }

    public void fillParkingLotsList(List <ParkingLot> parkingLots) {
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
        if(parkingSpaces.isEmpty())
        {
            createReservationButton.setDisable(true);
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Error", "No parking spaces available");
        }
        else
        {
            createReservationButton.setDisable(false);
        }
    }

    public void getInitialParkingLots() {
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/parkinglot");
        try {
            parkingLotsFull = Arrays.asList(mapper.readValue(response, ParkingLot[].class));
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
        String response = CallEndpoints.Get("http://localhost:8080/api/v1/reservations");
        try {
            reservationsFull = Arrays.asList(mapper.readValue(response, Reservation[].class));
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

    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("main-page.fxml", redirect);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getInitialReservations();
        updateReservationStatus();
        getInitialParkingLots();
        fillReservationList(getUserReservations(reservationsFull));
        fillParkingLotsList(parkingLotsFull);
        cityChoice.getItems().addAll(City.values());
        cityChoice.setValue(null);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("reservations-page.fxml"));
    }
    public void choiceChanged(ActionEvent actionEvent) {
        if(cityChoice.getValue() != null)
        {
            List<ParkingLot> parkingLotsFiltered = parkingLotsFull.stream().filter(p -> p.getCity().equals(cityChoice.getValue())).toList();
            fillParkingLotsList(parkingLotsFiltered);
            parkingSpaceList.getItems().clear();
        }
    }

    public void cancelReservation(ActionEvent actionEvent) {
    }

public void createReservation(ActionEvent actionEvent) {

    if(StartDateField.getValue().isBefore(java.time.LocalDate.now()) || endDField.getValue().isBefore(StartDateField.getValue()))
    {
        FxUtils.alert(Alert.AlertType.ERROR, "Error", "Error", "Please select valid dates");
        return;
    }
    else if(StartDateField.getValue().plusDays(5).isBefore(endDField.getValue()) || StartDateField.getValue().plusDays(1).isAfter(endDField.getValue()))
    {
        FxUtils.alert(Alert.AlertType.ERROR, "Error", "Error", "Reservation can be made for 1-5 days");
        return;
    }
        if(selectedParkingSpaceId != null && StartDateField.getValue() != null && endDField.getValue() != null)
        {
            if (!checkReservationCount(getUserReservations(reservationsFull))) {
                FxUtils.alert(Alert.AlertType.ERROR, "Error", "Error", "You already have 2 active reservations, please cancel one to create new");
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("parkingSpaceId", selectedParkingSpaceId);
            jsonObject.put("reservationStartDate", StartDateField.getValue());
            jsonObject.put("reservationEndDate", endDField.getValue());
            jsonObject.put("userId", LoginPage.session.employeeID);
            String response = CallEndpoints.Post("http://localhost:8080/api/v1/reservations/create", jsonObject.toString());
            System.out.println(response);
            getInitialReservations();
            updateReservationStatus();
            reservationList.getItems().clear();
            fillReservationList(getUserReservations(reservationsFull));
        }
        else
        {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "Error", "Please select parking space, parking lot and date");
        }
    }
    public List<Reservation> getUserReservations(List<Reservation> reservations) {
        return reservations.stream().filter(p -> p.getUserId().equals(LoginPage.session.employeeID)).toList();
    }
    public boolean checkReservationCount(List<Reservation> reservations) {
        return reservations.stream().filter(p -> p.getReservationStatus().equals(ReservationStatus.UPCOMING)).count() < 2;
    }
    public void selectParkingSpace(MouseEvent event) {
        selectedParkingSpaceId = parkingSpaceList.getSelectionModel().getSelectedItem().getParkingSpaceId();
        selectedParkingLotId = parkingSpaceList.getSelectionModel().getSelectedItem().getParkingLotId();
    }
    public void updateReservationStatus() {
        for (Reservation reservation : reservationsFull) {
            if (reservation.getReservationStatus() != ReservationStatus.UPCOMING) {
                continue;
            }
            if (reservation.getReservationEndDate().before(new Timestamp(System.currentTimeMillis()))) {
                reservation.setReservationStatus(ReservationStatus.COMPLETED);
            }
        }
    }

}
