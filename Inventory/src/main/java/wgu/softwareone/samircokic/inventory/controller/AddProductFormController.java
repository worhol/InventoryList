package wgu.softwareone.samircokic.inventory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import wgu.softwareone.samircokic.inventory.model.Outsourced;
import wgu.softwareone.samircokic.inventory.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    Stage stage;
    Parent scene;
    @javafx.fxml.FXML
    private TableColumn addProductPartInvCol;
    @javafx.fxml.FXML
    private TableColumn addProductPartPriceCol;
    @javafx.fxml.FXML
    private TableColumn addProductPartIdCol;
    @javafx.fxml.FXML
    private TableView addProductPartsTable;
    @javafx.fxml.FXML
    private TableColumn addProductPartNameCol;
    @javafx.fxml.FXML
    private TableColumn associatedPartPrice;
    @javafx.fxml.FXML
    private TableView associatedPartTable;
    @javafx.fxml.FXML
    private TableColumn associatedPartName;
    @javafx.fxml.FXML
    private TableColumn associatedPartId;
    @javafx.fxml.FXML
    private TableColumn associatedPartInv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartsTable.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(getPartsInProduct());

        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @javafx.fxml.FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    ObservableList<Part> partsInProduct = FXCollections.observableArrayList();
    public ObservableList<Part> getPartsInProduct() {
        return partsInProduct;
    }

    @javafx.fxml.FXML
    public void addPartToProduct(ActionEvent actionEvent) throws IOException {
        int index = Inventory.getAllParts().indexOf(addProductPartsTable.getSelectionModel().getSelectedItem());
        Part part = Inventory.getAllParts().get(index);
        partsInProduct.add(part);
    }


}
