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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    public void inHouseOrOutsourcedMode(ActionEvent actionEvent) {
        if (modifyOutRBtn.isSelected()) {
            modifyPartMode.setText("Company Name");
        } else if (modifyInRBtn.isSelected()) {
            modifyPartMode.setText("Machine ID");
        }

    }
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

    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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
