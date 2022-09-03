package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartOutsourcedMenuController {
    @FXML
    private ToggleGroup modify;
    @FXML
    private RadioButton modifyInRBtn;
    @FXML
    private RadioButton modifyOutRBtn;

    Stage stage;
    Parent scene;

    @FXML
    public void onActionDisplayModifyPartOutsourcedMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((RadioButton) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyPartOutsourcedMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionDisplayModifyPartMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((RadioButton) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
