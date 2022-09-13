package wgu.softwareone.samircokic.inventory.controller;

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
    @FXML
    private TextField searchContent;
    @FXML
    private TableColumn productInvCol;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn productIdCol;
    @FXML
    private TableColumn productNameCol;
    @FXML
    private TableColumn productPriceCol;
    @FXML
    private TextField searchProductContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    public void exitApp(ActionEvent actionEvent) {
        System.exit(0);
    }


    @FXML
    public void deletePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (partsTable.getSelectionModel().getSelectedItem()==null) {
            partNotFoundDialogBox();
        }
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        }
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

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyPartForm.fxml"));
        fxmlLoader.load();

        ModifyPartFormController modifyPartFormController = fxmlLoader.getController();
        modifyPartFormController.sendPart(partsTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    public void searchParts(ActionEvent actionEvent) throws IOException {
        if (searchContent.getText().isEmpty()) {
            partsTable.getSelectionModel().clearSelection();
            partsTable.setItems(Inventory.getAllParts());
        }
        try {
            ObservableList<Part> parts = Inventory.lookupPart(searchContent.getText());
            if (parts.size() == 0) {
                int idNum = Integer.parseInt(searchContent.getText());
                Part part = Inventory.lookupPart(idNum);
                if (part != null) {
                    partsTable.getSelectionModel().clearSelection();
                    partsTable.getSelectionModel().select(part);
                } else if (part == null) {
//                    partsTable.getSelectionModel().clearSelection();
                    partsTable.setItems(Inventory.getAllParts());
                    partNotFoundDialogBox();
                }
            } else if (parts.size() > 0) {
                partsTable.getSelectionModel().clearSelection();
                partsTable.setItems(parts);
            } else {
                partsTable.getSelectionModel().clearSelection();
                partsTable.setItems(Inventory.getAllParts());
            }
        } catch (NumberFormatException numberFormatException) {
            partNotFoundDialogBox();
        }
    }

    public static void partNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
        alert.show();
    }
    public static void productNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");
        alert.show();
    }

    @FXML
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyProductForm.fxml"));
        fxmlLoader.load();

        ModifyProductFormController modifyProductFormController = fxmlLoader.getController();
        modifyProductFormController.sendProduct(productsTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    public void addProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void removeProduct(ActionEvent actionEvent) {
        Alert deleteProduct = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
        Optional<ButtonType> answer = deleteProduct.showAndWait();
        if ( productsTable.getSelectionModel().getSelectedItem()==null) {
            productNotFoundDialogBox();
        }
        if (answer.isPresent() && answer.get() == ButtonType.OK
                &&productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().size()==0) {
            Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
        }else {
            Alert partPresent = new Alert(Alert.AlertType.WARNING, "Can't delete product associated with part");
            Optional<ButtonType> answerPart = partPresent.showAndWait();
        }

    }

    @FXML
    public void searchProduct(ActionEvent actionEvent) throws IOException{
        if (searchProductContent.getText().isEmpty()) {
            productsTable.getSelectionModel().clearSelection();
            productsTable.setItems(Inventory.getAllProducts());
        }
        try {
            ObservableList<Product> products = Inventory.lookupProduct(searchProductContent.getText());
            if (products.size() == 0) {
                int idNum = Integer.parseInt(searchProductContent.getText());
                Product product = Inventory.lookupProduct(idNum);
                if (product != null) {
                    productsTable.getSelectionModel().clearSelection();
                    productsTable.getSelectionModel().select(product);
                } else if (product == null) {
                    productsTable.setItems(Inventory.getAllProducts());
                    productNotFoundDialogBox();
                }
            } else if (products.size() > 0) {
                productsTable.getSelectionModel().clearSelection();
                productsTable.setItems(products);
            } else {
                productsTable.getSelectionModel().clearSelection();
                productsTable.setItems(Inventory.getAllProducts());
            }
        } catch (NumberFormatException numberFormatException) {
            productNotFoundDialogBox();
            System.out.println(numberFormatException);
        }catch (NullPointerException e){
            System.out.println(e);
            productNotFoundDialogBox();
        }
    }


}