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
 * @author Samir Cokic
 *
 * <p>This class runs the logic of adding a part in GUI. It also implements Initializable interface </p>
 */
public class AddPartFormController implements Initializable {
    @FXML
    private RadioButton outRBtn;
    @FXML
    private RadioButton inRBtn;
    @FXML
    private Label addPartMode;
    @FXML
    private TextField addPartPriceTxt;
    @FXML
    private TextField addPartInvTxt;
    @FXML
    private TextField addPartNameTxt;
    @FXML
    private TextField addPartIdTxt;
    @FXML
    private TextField addPartMaxTxt;
    @FXML
    private TextField addPartMinTxt;
    @FXML
    private TextField addPartMachineIdOrCompanyNameTxt;

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
     * for in house or outsourced was chosen.It checks the whether the outRbtn or inRBtn was selected and then switches the label value
     * between Company Name for outsourced product,
     * and Machine ID for in house made product</p>
     *
     * @param actionEvent the event that calls the method
     */
    @FXML
    public void inHouseOrOutsourcedMode(ActionEvent actionEvent) {
        if (outRBtn.isSelected()) {
            addPartMode.setText("Company Name");
            outRBtn.setSelected(true);
        } else if (inRBtn.isSelected()) {
            addPartMode.setText("Machine ID");
            inRBtn.setSelected(true);
        }
    }

    /**
     *<p>This method runs through allParts list and checks for the largest number of id in it.
     * Then it adds 1 to that largest number and assign the value to new id</p>
     * @return the id number
     */
    public static int createID() {
        int id = 1;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            int max = Inventory.getAllParts().get(i).getId();
            if (max >= id) {
                id = max + 1;
            }
        }
        return id;
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
            int min = Integer.valueOf(addPartMinTxt.getText());
            int max = Integer.valueOf(addPartMaxTxt.getText());
            int inventory = Integer.valueOf(addPartInvTxt.getText());
            if (min >= max || min >= inventory || max <= inventory) {
                return false;
            }

        } catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Data");
            alert.setContentText("Please enter valid data for each field");
            alert.showAndWait();
        }
        return true;
    }

    /**
     * <p>This method upon receiving ActionEvent object as the argument, saves the entered values of the created part and moves to the main menu.
     * It first checks which radio button is called. If it's a inRBtn that means part is being made in house so it
     * takes the value of id, name, inventory, price min and max to create a part, but since it's a in house created part
     * it also takes the value of machine ID. Then it calls the minIsLessThanMax() method and if that method returns true
     * part is created and added to allParts Inventory list. After the save button was clicked, the method takes the user to main menu
     * If the outRBtn was chosen then the outsourced part was created, which works the same as the in house method except instead of int machine id
     * method uses company name String.
     * The method also warns user if the inventory value is not in between min and max values, and if the min is larger than max.<br>
     *
     * <b>RUNTIME ERROR</b> The issues that were coming with this method were ClassCastException.
     * Basically the method was trying to pass the instance of InHouse object or a Outsource one and add it to the allParts
     * which was looking for a Part object.
     * The solution was to use polymorphism and create the Part object that would downcast as either InHouse or Outsourced object
     * once its being added to allParts Inventory</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException checks for the IllegalArgumentException if the user entered invalid values.
     */
    @FXML
    public void savePart(ActionEvent actionEvent) throws IOException {
        try {
            if (inRBtn.isSelected()) {
                int id = createID();
                String name = addPartNameTxt.getText();
                int inventory = Integer.parseInt(addPartInvTxt.getText());
                double price = Double.parseDouble(addPartPriceTxt.getText());
                int max = Integer.parseInt(addPartMaxTxt.getText());
                int min = Integer.parseInt(addPartMinTxt.getText());
                int machineId = Integer.parseInt(addPartMachineIdOrCompanyNameTxt.getText());
                if (minIsLessThanMax()) {
                    Part newPart = new InHouse(id, name, price, inventory, min, max, machineId);
                    Inventory.addPart(newPart);
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

            } else if (outRBtn.isSelected()) {
                int id = createID();
                String name = addPartNameTxt.getText();
                int inventory = Integer.parseInt(addPartInvTxt.getText());
                double price = Double.parseDouble(addPartPriceTxt.getText());
                int max = Integer.parseInt(addPartMaxTxt.getText());
                int min = Integer.parseInt(addPartMinTxt.getText());
                String companyName = addPartMachineIdOrCompanyNameTxt.getText();
                if (minIsLessThanMax()) {
                    Part newPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                    Inventory.addPart(newPart);
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
     * <p>This method takes the user back to the Main Menu</p>
     * @param actionEvent the event that calls the method
     * @throws IOException checks for the IllegalArgumentException if the user entered invalid values.
     */
    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
