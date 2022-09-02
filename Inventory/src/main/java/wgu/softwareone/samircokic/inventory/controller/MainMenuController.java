package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void onActionDeleteProduct(ActionEvent actionEvent) {
    }

    @FXML
    public void onActionModifyProduct(ActionEvent actionEvent) {
    }

    @FXML
    public void onActionSearchProducts(ActionEvent actionEvent) {
    }

    @FXML
    public void onActionDeletePart(ActionEvent actionEvent) {
    }

    @FXML
    public void onActionAddProduct(ActionEvent actionEvent) {
    }

    @FXML
    public void onActionAddPart(ActionEvent actionEvent) {

    }

    @FXML
    public void onActionModifyPart(ActionEvent actionEvent) {
    }

    @FXML
    public void onActionSearchParts(ActionEvent actionEvent) {
    }
}