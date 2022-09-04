package wgu.softwareone.samircokic.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.InHouse;
import wgu.softwareone.samircokic.inventory.model.Inventory;
import wgu.softwareone.samircokic.inventory.model.Outsourced;
import wgu.softwareone.samircokic.inventory.model.Part;

import java.io.IOException;

/**
 *
 */
public class InventoryManagementSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementSystem.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Part part = new InHouse(3, "wheel", 22.56, 35, 5, 15, 123332);
        Part part2 = new Outsourced(5, "break",34.78, 12,5, 19, "Toyota");
        Inventory.addPart(part);
        Inventory.addPart(part2);

        launch();
    }
}