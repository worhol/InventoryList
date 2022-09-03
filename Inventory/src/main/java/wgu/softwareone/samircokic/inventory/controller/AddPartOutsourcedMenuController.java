package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartOutsourcedMenuController implements Initializable {
    @FXML
    private RadioButton outRBtn;
    @FXML
    private ToggleGroup madein;
    @FXML
    private RadioButton inRBtn;

    Stage stage;
    Parent scene;
    @FXML
    private TextField addPartOutMinTxt;
    @FXML
    private TextField addPartOutCompanyNameTxt;
    @FXML
    private TextField addPartOutInvTxt;
    @FXML
    private TextField addPartOutIdTxt;
    @FXML
    private TextField addPartOutNameTxt;
    @FXML
    private TextField addPartOutPriceTxt;
    @FXML
    private TextField addPartOutMaxTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onActionDisplayAddPartOutsourcedMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((RadioButton) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddPartOutsourcedMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionDisplayAddPartMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((RadioButton) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
