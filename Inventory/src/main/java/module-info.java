module wgu.softwareone.samircokic.inventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens wgu.softwareone.samircokic.inventory to javafx.fxml;
    exports wgu.softwareone.samircokic.inventory;
}