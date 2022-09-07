package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyProductFormController implements Initializable {

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @javafx.fxml.FXML
    public void removeAssociatedPart(ActionEvent actionEvent) {

    }
}
