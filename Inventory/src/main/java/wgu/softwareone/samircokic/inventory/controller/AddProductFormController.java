package wgu.softwareone.samircokic.inventory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.softwareone.samircokic.inventory.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {
    @FXML
    private TableColumn addProductPartInvCol;
    @FXML
    private TableColumn addProductPartPriceCol;
    @FXML
    private TableColumn addProductPartIdCol;
    @FXML
    private TableView<Part> addProductPartsTable;
    @FXML
    private TableColumn addProductPartNameCol;
    @FXML
    private TableColumn associatedPartPrice;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn associatedPartName;
    @FXML
    private TableColumn associatedPartId;
    @FXML
    private TableColumn associatedPartInv;
    @FXML
    private TextField searchContent;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductId;
    @FXML
    private TextField addProductPrice;

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartsTable.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        partsInProduct.clear();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    static ObservableList<Part> partsInProduct = FXCollections.observableArrayList();
    @FXML
    public void addPartToProduct(ActionEvent actionEvent) throws IOException {
        Part part = addProductPartsTable.getSelectionModel().getSelectedItem();
        partsInProduct.add(part);
        associatedPartTable.setItems(partsInProduct);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    @FXML
    public void removeAssociatedPart(ActionEvent actionEvent) throws IndexOutOfBoundsException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        Optional<ButtonType> answer = alert.showAndWait();
        try {
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                partsInProduct.remove(associatedPartTable.getSelectionModel().getSelectedItem());
            }
        } catch (IndexOutOfBoundsException e) {
            Alert alertNotFound = new Alert(Alert.AlertType.ERROR, "Part not found");
            alertNotFound.show();
        }
    }

    @FXML
    public void searchPartForProduct(ActionEvent actionEvent) {

        if (searchContent.getText().isEmpty()) {
            addProductPartsTable.getSelectionModel().clearSelection();
            addProductPartsTable.setItems(Inventory.getAllParts());
        }
        try {
            ObservableList<Part> parts = Inventory.lookupPart(searchContent.getText());
            if (parts.size() == 0) {
                int idNum = Integer.parseInt(searchContent.getText());
                Part part = Inventory.lookupPart(idNum);
                if (part != null) {
                    addProductPartsTable.getSelectionModel().clearSelection();
                    addProductPartsTable.getSelectionModel().select(part);
                } else if (part == null) {
                    addProductPartsTable.setItems(Inventory.getAllParts());
                    partNotFoundDialogBox();
                }
            } else if (parts.size() > 0) {
                addProductPartsTable.getSelectionModel().clearSelection();
                addProductPartsTable.setItems(parts);
            } else {
                addProductPartsTable.getSelectionModel().clearSelection();
                addProductPartsTable.setItems(Inventory.getAllParts());
            }
        } catch (NumberFormatException numberFormatException) {
            partNotFoundDialogBox();
        }
    }

    public static void partNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
        alert.show();
    }

    public static int createID() {
        int id = 1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            int max = Inventory.getAllProducts().get(i).getId();
            if (max >= id) {
                id = max + 1;
            }
        }
        return id;
    }

    public boolean minIsLessThanMax() {
        try {
            int min = Integer.valueOf(addProductMin.getText());
            int max = Integer.valueOf(addProductMax.getText());
            int inventory = Integer.valueOf(addProductInv.getText());
            if (min >= max || min >= inventory || max <= inventory) {
                return false;
            }

        } catch (NumberFormatException numberFormatException) {

        }
        return true;
    }

    @FXML
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        try {
            int id = createID();
            String name = addProductName.getText();
            int inventory = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());



            if (minIsLessThanMax()) {
                Product product = new Product(id, name, price, inventory, min, max);
                Inventory.addProduct(product);
                for (Part part:partsInProduct){
                    product.addAssociatedPart(part);
                }
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max; and Inv should be between those two values.");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Data");
            alert.setContentText("Please enter valid data for each field");
            alert.showAndWait();

        }

    }
}
