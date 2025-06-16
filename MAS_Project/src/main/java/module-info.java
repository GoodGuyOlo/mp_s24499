module com.wypozyczalnia.mas_project {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.wypozyczalnia.mas_project to javafx.fxml;
    exports com.wypozyczalnia.mas_project;
    opens com.wypozyczalnia.mas_project.controller to javafx.fxml;

}