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
import wgu.softwareone.samircokic.inventory.model.Inventory;
import wgu.softwareone.samircokic.inventory.model.Part;
import wgu.softwareone.samircokic.inventory.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyProductFormController implements Initializable {


    @FXML
    private TextField modifyProductMax;
    @FXML
    private TableColumn modifyProductAssociatedPartInv;
    @FXML
    private TextField modifyProductId;
    @FXML
    private TableColumn modifyProductAssociatedPartId;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TableView<Part> modifyProductAddPartTable;
    @FXML
    private TableColumn modifyProductAddPartName;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TableColumn modifyProductAddPartInv;
    @FXML
    private TableColumn modifyProductAddPartId;
    @FXML
    private TableView<Part> modifyProductAssociatedPartTable;
    @FXML
    private TableColumn modifyProductAssociatedPartPrice;
    @FXML
    private TableColumn modifyProductAssociatedPartName;
    @FXML
    private TableColumn modifyProductAddPartPrice;
    Stage stage;
    Parent scene;
    @FXML
    private TextField searchPartInModfyProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductAddPartTable.setItems(Inventory.getAllParts());
        modifyProductAddPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAddPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAddPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAddPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductAssociatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void removeAssociatedPart(ActionEvent actionEvent) throws IndexOutOfBoundsException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        Optional<ButtonType> answer = alert.showAndWait();
        try {
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                Part part = modifyProductAssociatedPartTable.getSelectionModel().getSelectedItem();
                partsInModifyProduct.remove(part);
              Product product =  Inventory.lookupProduct(Integer.valueOf(modifyProductId.getText()));
               product.deleteAssociatedPart(part);

            }
        } catch (IndexOutOfBoundsException e) {
            Alert alertNotFound = new Alert(Alert.AlertType.ERROR, "Part not found");
            alertNotFound.show();
        }

    }
    public void sendProduct(Product product) {
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyProductAssociatedPartTable.setItems(product.getAllAssociatedParts());
    }

    @FXML
    public void saveModifiedProduct(ActionEvent actionEvent) throws IOException{
        int index = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(Integer.parseInt(modifyProductId.getText())));
        try {
            int id = Integer.valueOf(modifyProductId.getText());
            String name = modifyProductName.getText();
            int inventory = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            if (minIsLessThanMax()){
                Product product = new Product(id,name,price,inventory,min,max);
                for (Part part:partsInModifyProduct){
                    product.addAssociatedPart(part);
                }
                Inventory.updateProduct(index,product);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max; and Inv should be between those two values.");
                alert.showAndWait();
            }

        }catch (IllegalArgumentException illegalArgumentException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Data");
            alert.setContentText("Please enter valid data for each field");
            alert.showAndWait();
        }

    }
    public boolean minIsLessThanMax() {
        try {
            int min = Integer.valueOf(modifyProductMin.getText());
            int max = Integer.valueOf(modifyProductMax.getText());
            int inventory = Integer.valueOf(modifyProductInv.getText());
            if (min >= max || min >= inventory || max <= inventory) {
                return false;
            }

        } catch (NumberFormatException numberFormatException) {

        }
        return true;
    }
    static ObservableList<Part> partsInModifyProduct = FXCollections.observableArrayList();
    @FXML
    public void addPartToModifyProduct(ActionEvent actionEvent) {

        Part part = modifyProductAddPartTable.getSelectionModel().getSelectedItem();
        partsInModifyProduct.add(part);
        modifyProductAssociatedPartTable.setItems(partsInModifyProduct);
    }


    @FXML
    public void searchForPart(ActionEvent actionEvent) {

        if (searchPartInModfyProduct.getText().isEmpty()) {
            modifyProductAddPartTable.getSelectionModel().clearSelection();
            modifyProductAddPartTable.setItems(Inventory.getAllParts());
        }
        try {
            ObservableList<Part> parts = Inventory.lookupPart(searchPartInModfyProduct.getText());
            if (parts.size() == 0) {
                int idNum = Integer.parseInt(searchPartInModfyProduct.getText());
                Part part = Inventory.lookupPart(idNum);
                if (part != null) {
                    modifyProductAddPartTable.getSelectionModel().clearSelection();
                    modifyProductAddPartTable.getSelectionModel().select(part);
                } else if (part == null) {
                    modifyProductAddPartTable.setItems(Inventory.getAllParts());
                    partNotFoundDialogBox();
                }
            } else if (parts.size() > 0) {
                modifyProductAddPartTable.getSelectionModel().clearSelection();
                modifyProductAddPartTable.setItems(parts);
            } else {
                modifyProductAddPartTable.getSelectionModel().clearSelection();
                modifyProductAddPartTable.setItems(Inventory.getAllParts());
            }
        } catch (NumberFormatException numberFormatException) {
            partNotFoundDialogBox();
        }
    }
    public static void partNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
        alert.show();
    }
}
