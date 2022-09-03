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
    private TextField addPartInNameTxt;
    @FXML
    private TextField addPartInIdTxt;
    @FXML
    private TextField addPartInInvTxt;
    @FXML
    private TextField addPartInMaxTxt;
    @FXML
    private TextField addPartInPriceTxt;
    @FXML
    private TextField addPartInMachineIdTxt;
    @FXML
    private TextField addPartInMinTxt;
    @FXML
    private Label addPartMode;
    Stage stage;
    Parent scene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void inHouseOrOutsourcedMode(ActionEvent actionEvent) {
        if (outRBtn.isSelected()) {
            addPartMode.setText("Company Name");
        } else if (inRBtn.isSelected()) {
            addPartMode.setText("Machine ID");
        }
    }

    @FXML
    public void savePart(ActionEvent actionEvent) throws IOException {
        try {
            int id = 5;//Integer.valueOf(addPartInIdTxt.getText());
            String name = addPartInNameTxt.getText();
            int inventory = Integer.parseInt(addPartInInvTxt.getText());
            double price = Double.parseDouble(addPartInPriceTxt.getText());
            int max = Integer.parseInt(addPartInMaxTxt.getText());
            int min = Integer.parseInt(addPartInMinTxt.getText());
            int machineId = Integer.parseInt(addPartInMachineIdTxt.getText());

            Part newPart = new InHouse(id,name,price,inventory,min,max,machineId);
            Inventory.addPart(newPart);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Data");
            alert.setContentText("Please enter valid data for each field");
            alert.showAndWait();
        }
    }


}
