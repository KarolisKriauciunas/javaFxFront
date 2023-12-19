package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Account.AccountType;
import com.example.kursinis.model.Account.DtoUser;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.kursinis.utilities.FxUtils.checkAdminPrivileges;

public class UserPage implements Initializable {
    public TableColumn<DtoUser, Integer> id;
    public TableColumn<DtoUser, String> firstName;
    public TableColumn<DtoUser, String> lastName;
    public TableColumn<DtoUser, String> email;
    public TableColumn<DtoUser, String> salary;
    public TableColumn<DtoUser, AccountType> accountType;
    public TableView<DtoUser> userTable;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField salaryField;
    public TextField positionField;
    public TextField statusField;
    public TextField idField;
    public List<DtoUser> users;
    public ObjectMapper mapper = new ObjectMapper();
    public TextField usernameField;

    String response = CallEndpoints.Get("http://localhost:8080/users");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getInitialUsers();
        fillTable();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("view-user-page.fxml"));
    }

    void getInitialUsers() {
        try {
            users = Arrays.asList(mapper.readValue(response, DtoUser[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        accountType.setCellValueFactory(new PropertyValueFactory<>("type"));

        userTable.getItems().setAll(users);
    }

    @FXML
    public void rowClicked(MouseEvent event) {
        DtoUser clickedUser = userTable.getSelectionModel().getSelectedItem();

        if (clickedUser != null) {
            idField.setText(String.valueOf(clickedUser.getEmployeeID()));
            firstNameField.setText(clickedUser.getFirstName());
            lastNameField.setText(clickedUser.getLastName());
            emailField.setText(clickedUser.getEmail());
            positionField.setText(clickedUser.getType().toString());
        }
    }

    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("main-page.fxml", firstNameField);
    }

    public void submit(ActionEvent actionEvent) {
        if (!checkAdminPrivileges(LoginPage.session)) {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "You do not have admin privileges", "");
            return;
        }
        ObservableList<DtoUser> currentUserTable = userTable.getItems();
        int currentUserId = Integer.parseInt(idField.getText());

        for (DtoUser user : currentUserTable) {
            if (user.getEmployeeID() == currentUserId) {
                user.setFirstName(firstNameField.getText());
                user.setLastName(lastNameField.getText());
                user.setEmail(emailField.getText());
                user.setType(AccountType.valueOf(positionField.getText()));

                JSONObject json = new JSONObject();
                String urlBase = "http://localhost:8080/update/user/" + currentUserId + "?"
                        + "firstName=" + firstNameField.getText()
                        + "&lastName=" + lastNameField.getText()
                        + "&email=" + emailField.getText()
                        + "&salary=" + salaryField.getText()
                        + "&type=" + positionField.getText()
                        + "&salary=" + salaryField.getText();
                if(CallEndpoints.Put(urlBase, "").equals("200")) {
                    FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Update complete", "");
                    userTable.setItems(currentUserTable);
                    userTable.refresh();
                }
                else
                    FxUtils.alert(Alert.AlertType.ERROR, "Error", "Update failed", "");
                break;
            }
        }
    }

    public void delete(ActionEvent actionEvent) {
        if (!checkAdminPrivileges(LoginPage.session)) {
            FxUtils.alert(Alert.AlertType.ERROR, "Error", "You do not have admin privileges", "");
            return;
        }

        ObservableList<DtoUser> currentUserTable = userTable.getItems();

        CallEndpoints.Delete("http://localhost:8080/api/user/delete?id=" + Integer.parseInt(idField.getText()));

        FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Deletion complete", "");

        currentUserTable.removeAll();
        userTable.setItems(currentUserTable);

        fillTable();
    }
}
