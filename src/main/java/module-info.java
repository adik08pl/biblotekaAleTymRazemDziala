module com.stempien.uwielbiamzaczynacodpoczatku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.mail;
    requires java.desktop;

    opens com.stempien.uwielbiamzaczynacodpoczatku to javafx.fxml;
    exports com.stempien.uwielbiamzaczynacodpoczatku;
}