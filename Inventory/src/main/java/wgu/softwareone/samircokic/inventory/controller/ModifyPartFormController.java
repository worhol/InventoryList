package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.InHouse;
import wgu.softwareone.samircokic.inventory.model.Inventory;
import wgu.softwareone.samircokic.inventory.model.Outsourced;
import wgu.softwareone.samircokic.inventory.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>This class is a blueprint for modify part form logic</p>
 *
 * @author Samir Cokic
 */
public class ModifyPartFormController implements Initializable {
    @FXML
    private ToggleGroup madein;
    @FXML
    private RadioButton modifyInRBtn;
    @FXML
    private RadioButton modifyOutRBtn;
    @FXML
    private Label modifyPartMode;
    @FXML
    private TextField modifyPartId;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartInv;
    @FXML
    private TextField modifyMachineIDOrCompanyName;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartName;

    Stage stage;
    Parent scene;

    /**
     *<p>Called to initialize a controller after its root element has been completely processed.
     * Refer to <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">Oracle</a> </p>
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     * <p>This method that takes the ActionEvent object as the argument,
     * when called switches the label in AddPart GUI,based on whether the radio button
     * for in house or outsourced was chosen.It checks the whether the modifyOutRBtn or modifyInRBtn was selected and then switches the label value
     * between Company Name for outsourced product,
     * and Machine ID for in house made product</p>
     *
     * @param actionEvent the event that calls the method
     */
    @FXML
    public void inHouseOrOutsourcedMode(ActionEvent actionEvent) {
        if (modifyOutRBtn.isSelected()) {
            modifyPartMode.setText("Company Name");
        } else if (modifyInRBtn.isSelected()) {
            modifyPartMode.setText("Machine ID");
        }

    }

    /**
     *<p>This method serves as the bridge between add part form and modify part. It sets the values of the part and when called displays
     * the values in modify part form. It checks whether the part is instance of InHouse Part or Outsourced part and adds appropriate values</p>
     *
     * @param part part to be used
     */
    public void sendPart(Part part){
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        if (part instanceof InHouse){
            modifyInRBtn.setSelected(true);
            inHouseOrOutsourcedMode(new ActionEvent());
            modifyMachineIDOrCompanyName.setText(String.valueOf(((InHouse) part).getMachineId()));
        }else if (part instanceof Outsourced){
            modifyOutRBtn.setSelected(true);
            inHouseOrOutsourcedMode(new ActionEvent());
            modifyMachineIDOrCompanyName.setText(((Outsourced) part).getCompanyName());
        }
    }

    /**
     * <p>This method takes the user back to the Main Menu</p>
     * @param actionEvent the event that calls the method
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * <p>This method upon receiving ActionEvent object as the argument, saves the modified values of the part and moves to the main menu.
     * it searches the selected part and adds the selected value to index variable.
     * Then it checks which radio button is called. If it's a modifyInRBtn that means part is being made in house so it
     * takes the value of id, name, inventory, price min and max to create a part, but since it's a in house created part
     * it also takes the value of machine ID. Then it calls the minIsLessThanMax() method and if that method returns true
     * modified part is created and updated as in allParts Inventory list at the place of index value. After the save button was clicked, the method takes the user to main menu
     * If the modifyOutRBtn was chosen then the outsourced part was created, which works the same as the in house method except instead of int machine id
     * method uses company name String.
     * The method also warns user if the inventory value is not in between min and max values, and if the min is larger than max.<br>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException checks for the IllegalArgumentException if the user entered invalid values.
     */
    @FXML
    public void saveModifiedPart(ActionEvent actionEvent) throws IOException {
        int index = Inventory.getAllParts().indexOf(Inventory.lookupPart(Integer.parseInt(modifyPartId.getText())));
                try {
            if (modifyInRBtn.isSelected()) {
                int id = Integer.valueOf(modifyPartId.getText());
                String name = modifyPartName.getText();
                int inventory = Integer.parseInt(modifyPartInv.getText());
                double price = Double.parseDouble(modifyPartPrice.getText());
                int max = Integer.parseInt(modifyPartMax.getText());
                int min = Integer.parseInt(modifyPartMin.getText());
                int machineId = Integer.parseInt(modifyMachineIDOrCompanyName.getText());
                if (minIsLessThanMax()) {
                    Part newPart = new InHouse(id, name, price, inventory, min, max, machineId);
                    Inventory.updatePart(index,newPart);

                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else if (min>max){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max!");
                    alert.showAndWait();
                }else if (inventory>max||inventory<min){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value should be between min and max values!");
                    alert.showAndWait();
                }

            } else if (modifyOutRBtn.isSelected()) {
                int id = Integer.valueOf(modifyPartId.getText());
                String name = modifyPartName.getText();
                int inventory = Integer.parseInt(modifyPartInv.getText());
                double price = Double.parseDouble(modifyPartPrice.getText());
                int max = Integer.parseInt(modifyPartMax.getText());
                int min = Integer.parseInt(modifyPartMin.getText());
                String companyName = modifyMachineIDOrCompanyName.getText();
                if (minIsLessThanMax()) {
                    Part newPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                    Inventory.updatePart(index, newPart);
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else if (min>max){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max!");
                    alert.showAndWait();
                }else if (inventory>max||inventory<min){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value should be between min and max values!");
                    alert.showAndWait();
                }

            }

        } catch (IllegalArgumentException illegalArgumentException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Data");
            alert.setContentText("Please enter valid data for each field");
            alert.showAndWait();

        }
    }
    /**
     * <p>This method checks if the min value is less than max value and that inventory value is in between those two.
     * It returns true if condition is right and false otherwise.<br>
     * <b>RUNTIME ERROR</b> The issues with this method were if the inappropriate data was entered for example
     * String value instead of int value. In order to avoid that, the exception handling was used that warns the user
     * about the inappropriate data entered</p>
     *
     * @return the boolean true or false
     */
    public boolean minIsLessThanMax() {
        try {
            int min = Integer.valueOf(modifyPartMin.getText());
            int max = Integer.valueOf(modifyPartMax.getText());
            int inventory = Integer.valueOf(modifyPartInv.getText());
            if (min >= max || min >= inventory || max <= inventory) {
                return false;
            }

        } catch (NumberFormatException numberFormatException) {

        }
        return true;
    }

}
