package wgu.softwareone.samircokic.inventory.controller;
/**
 * @author Samir Cokic
 */
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

public class AddPartFormController implements Initializable {
    @FXML
    private RadioButton outRBtn;
    @FXML
    private ToggleGroup madein;
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
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * @param actionEvent
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
     *
     * @return
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
     * @return
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

        }
        return true;
    }

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

    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
