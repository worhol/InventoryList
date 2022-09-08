package wgu.softwareone.samircokic.inventory.controller;

import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;


public class ModifyProductFormController implements Initializable {


    @javafx.fxml.FXML
    private TextField modifyProductMax;
    @javafx.fxml.FXML
    private TableColumn modifyProductAssociatedPartInv;
    @javafx.fxml.FXML
    private TextField modifyProductId;
    @javafx.fxml.FXML
    private TableColumn modifyProductAssociatedPartId;
    @javafx.fxml.FXML
    private TextField modifyProductName;
    @javafx.fxml.FXML
    private TableView<Part> modifyProductAddPartTable;
    @javafx.fxml.FXML
    private TableColumn modifyProductAddPartName;
    @javafx.fxml.FXML
    private TextField modifyProductPrice;
    @javafx.fxml.FXML
    private TextField modifyProductMin;
    @javafx.fxml.FXML
    private TextField modifyProductInv;
    @javafx.fxml.FXML
    private TableColumn modifyProductAddPartInv;
    @javafx.fxml.FXML
    private TableColumn modifyProductAddPartId;
    @javafx.fxml.FXML
    private TableView<Part> modifyProductAssociatedPartTable;
    @javafx.fxml.FXML
    private TableColumn modifyProductAssociatedPartPrice;
    @javafx.fxml.FXML
    private TableColumn modifyProductAssociatedPartName;
    @javafx.fxml.FXML
    private TableColumn modifyProductAddPartPrice;
    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        modifyProductAddPartTable.setItems(Inventory.getAllParts());
//        modifyProductAddPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        modifyProductAddPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        modifyProductAddPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        modifyProductAddPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

//        modifyProductAssociatedPartTable.setItems(Inventory.getAllParts());
//        modifyProductAssociatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        modifyProductAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        modifyProductAssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        modifyProductAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



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
    public void sendProduct(Product product) {
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
    }
    @javafx.fxml.FXML
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
}
