package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    public void sendPart(InHouse part){
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        modifyMachineIDOrCompanyName.setText(String.valueOf(part.getMachineId()));
    }

//    public void sendPart(Outsourced part){
//        modifyPartId.setText(String.valueOf(part.getId()));
//        modifyPartName.setText(part.getName());
//        modifyPartInv.setText(String.valueOf(part.getStock()));
//        modifyPartPrice.setText(String.valueOf(part.getPrice()));
//        modifyPartMax.setText(String.valueOf(part.getMax()));
//        modifyPartMin.setText(String.valueOf(part.getMin()));
//        modifyMachineIDOrCompanyName.setText(String.valueOf(part.getCompanyName()));
//    }

}
