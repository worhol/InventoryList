package wgu.softwareone.samircokic.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.*;

import java.io.IOException;

/**
 * @author Samir Cokic
 * <p>This is the main class of the Inventory managment system application.<br>
 *  <b>FUTURE ENHANCEMENT</b><br>
 *  In the future the further refactoring of the code throughout the project would be necessary in order to avoid redundancy.<br>
 *  Possible addition of the another class or two which would hold the redundant methods like save or delete.<br>
 *  It would be also good to have an inventory more programmatic so when a user adds the part to the product, the inventory reflects it by removing that part from inventory.
 * </p>

 */
public class InventoryManagementSystem extends Application {
    /**
     *<p>This method displays the main menu. It creates the object of FXMLLoader that pulls the values from MainMenu fxml document</p>
     *
     * @param stage
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementSystem.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <p>This is the main method of the InventoryManagementSystem class where we can create the objects contained in that class and execute the methods associated with them.
     * launch method starts the application</p>
     *
     * @param args contains the command line arguments as a String object in array
     */
    public static void main(String[] args) {
        Part part = new InHouse(1, "Wheel", 22.56, 35, 5, 15, 123332);
        Part part2 = new Outsourced(2, "Break",34.78, 12,5, 19, "Toyota");
        Inventory.addPart(part);
        Inventory.addPart(part2);
        Product product = new Product(1,"Bike",334.45,8,2,16);
        Product product2 = new Product(2,"Scooter",534.45,8,2,16);
        Inventory.addProduct(product);
        Inventory.addProduct(product2);
        launch();
    }
}