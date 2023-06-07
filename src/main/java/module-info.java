module com.eamapp.almacen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.eamapp.almacen to javafx.fxml;
    exports com.eamapp.almacen;
    exports com.eamapp.almacen.controller;
    opens com.eamapp.almacen.controller to javafx.fxml;
}