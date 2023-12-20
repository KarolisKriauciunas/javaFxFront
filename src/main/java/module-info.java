module com.example.kursinis {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.json;
    requires java.net.http;
    requires javaee.api;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    exports com.example.kursinis.model.Account to com.fasterxml.jackson.databind;
    exports com.example.kursinis.model.Reservation to com.fasterxml.jackson.databind;
    opens com.example.kursinis.model.Reservation to com.fasterxml.jackson.databind, javafx.graphics, javafx.fxml, javafx.base;
    opens com.example.kursinis.model.Account to javafx.graphics, javafx.fxml, javafx.base;
    exports com.example.kursinis.model.Forumas to com.fasterxml.jackson.databind;
    exports com.example.kursinis.model to com.fasterxml.jackson.databind;
    opens com.example.kursinis.model.Forumas to javafx.graphics, javafx.fxml, javafx.base;
    opens com.example.kursinis.fxControllers to javafx.fxml;
    exports com.example.kursinis.fxControllers;
    opens com.example.kursinis.utilities to javafx.fxml;
    exports com.example.kursinis;
    opens com.example.kursinis.model to com.fasterxml.jackson.databind, javafx.base, javafx.fxml, javafx.graphics;
}