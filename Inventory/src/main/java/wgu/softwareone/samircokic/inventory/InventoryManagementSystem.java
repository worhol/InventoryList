package wgu.softwareone.samircokic.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.*;

import java.io.IOException;

/**
 * @author Samir Cokic
 * <p>
 *  <b>FUTURE ENHANCEMENT</b><br>
 *  In the future the further refactoring of the code along the project would be necessary in order to avoid redundancy.<br>
 *  Possible addition of the another class or two which would hold the redundant methods like save or delete.<br>
 *  It would be also good to have an inventory more programmatic so when a user adds the part to the product, the inventory reflects it by removing that part from inventory.
 * </p>

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