module com.metropaths {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens com.metropaths to javafx.fxml;
    exports com.metropaths;
}
