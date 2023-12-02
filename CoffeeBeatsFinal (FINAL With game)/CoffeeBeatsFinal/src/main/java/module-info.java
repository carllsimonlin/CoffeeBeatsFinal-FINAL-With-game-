module com.example.coffeebeatsfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.coffeebeatsfinal to javafx.fxml;
    exports com.example.coffeebeatsfinal;
}