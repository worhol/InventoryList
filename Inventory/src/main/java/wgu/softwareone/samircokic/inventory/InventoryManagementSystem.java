package wgu.softwareone.samircokic.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.*;

import java.io.IOException;

/**
 * @author Samir Cokic
 */

/**
 *
 */
public class InventoryManagementSystem extends Application {
    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementSystem.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Part part = new InHouse(3, "wheel", 22.56, 35, 5, 15, 123332);
        Part part2 = new Outsourced(5, "break",34.78, 12,5, 19, "Toyota");
        Inventory.addPart(part);
        Inventory.addPart(part2);
        Product product = new Product(1,"Bike",334.45,8,2,16);
        Product product2 = new Product(2,"MountainBike",534.45,8,2,16);
        Inventory.addProduct(product);
        Inventory.addProduct(product2);
        launch();
    }
}