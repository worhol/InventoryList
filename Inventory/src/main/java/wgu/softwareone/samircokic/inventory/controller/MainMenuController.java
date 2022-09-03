package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.InHouse;
import wgu.softwareone.samircokic.inventory.model.Inventory;
import wgu.softwareone.samircokic.inventory.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn pricePartsCol;
    @FXML
    private TableColumn partIdCol;
    @FXML
    private TableColumn partNameCol;
    @FXML
    private TableColumn inventoryCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    public void exitApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void onActionDeleteProduct(ActionEvent actionEvent) {
    }

    @FXML
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionSearchProducts(ActionEvent actionEvent) {
    }

    @FXML
    public void deletePart(ActionEvent actionEvent) {
        Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void addPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionSearchParts(ActionEvent actionEvent) {
    }
}