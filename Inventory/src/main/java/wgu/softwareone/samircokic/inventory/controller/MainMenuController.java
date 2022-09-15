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

/**
 * <p>This class contains methods that control behaviour of the main menu</p>
 *
 * @author Samir Cokic
 */
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

    /**
     *<p>Called to initialize a controller after its root element has been completely processed. Once initialized it
     * displays the two tables with their respective column values.
     * Refer to <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">Oracle</a> </p>
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */
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

    /**
     * <p>this method closes the app and exits the program</p>
     *
     * @param actionEvent the event that calls the method
     */
    @FXML
    public void exitApp(ActionEvent actionEvent) {
        System.exit(0);
    }


    /**
     * <p>This method removes the selected part from the main menu table. It checks that ok button was selected and that there are parts in a inventory
     * to be removed. If parts are not available it alerts the user. If user choose to cancel request it sets the scene to original position</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException Thrown to indicate that an index of some sort (such as to an array, to a string, or to a vector) is out of range.
     */
    @FXML
    public void deletePart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (partsTable.getSelectionModel().getSelectedItem()==null) {
            partNotFoundDialogBox();
        }
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        }else if (answer.isPresent() && answer.get() == ButtonType.CANCEL){
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * <p>This method displays the add part form </p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException Thrown to indicate that an index of some sort (such as to an array, to a string, or to a vector) is out of range.
     */
    @FXML
    public void addPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * <p>This method displays the modify part form and populate the respective tables with the values received after part was saved.
     * It creates the object of ModifyPartFormController class, then it calls that object's method sendPart() which populates the part table with
     * selected part's values.
     * <b>RUNTIME ERROR</b> The issues that were coming with this method were ClassCastException.
     * Basically the method savePart() was trying to pass the instance of InHouse object or a Outsource one and add it to the allParts
     * which was looking for a Part object.
     * The solution was to use polymorphism and create the Part object that would downcast as either InHouse or Outsourced object
     * once its being added to allParts Inventory</p>
     *
     * @param actionEvent the event that calls the method
     * @throws NullPointerException catches the inappropriate values.
     */
    @FXML
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyPartForm.fxml"));
            fxmlLoader.load();

            ModifyPartFormController modifyPartFormController = fxmlLoader.getController();
            modifyPartFormController.sendPart(partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = fxmlLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (NullPointerException e){
            partNotFoundDialogBox();
        }


    }
    /**
     * <p>This method search through the table that have available parts and if available displays them accordingly.
     * If searchContent textfield is empty it returns all parts available in allParts observable list.
     * This method utilizes two methods lookupPart. One method is looking for the part based on int argument and returns the part
     * the part whose id matches the argument. The other lookupPart method takes the String argument and returns the Part object
     * which matches the String value in a whole or partially.
     * If the part is not available in the list the method warns user.
     * <b>RUNTIME ERROR</b> The error that was coming running this method was that search content was not properly displaying
     * parts after the consequent search because previous search would stay in the parts observable list. In order to avoid that each time
     * method was called it also called the clearSelection() method which would clear the previous search content.</p>
     *
     * @param actionEvent the event that calls the method
     * @throws NumberFormatException if inappropriate data was entered
     */
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
    /**
     * <p>this method displays the error message.</p>
     */
    public static void partNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
        alert.show();
    }
    /**
     * <p>this method displays the error message.</p>
     */
    public static void productNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");
        alert.show();
    }
    /**
     * <p>This method displays the modify product form and populate the respective tables with the values received after part was saved.
     * It creates the object of ModifyProductFormController class then it calls that object method which populates the products table with
     * selected product's values.</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException NullPointerException catches the inappropriate values.
     */
    @FXML
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/wgu/softwareone/samircokic/inventory/ModifyProductForm.fxml"));
            fxmlLoader.load();

            ModifyProductFormController modifyProductFormController = fxmlLoader.getController();
            modifyProductFormController.sendProduct(productsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = fxmlLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (NullPointerException e){
                productNotFoundDialogBox();
        }


    }

    /**
     * <p>This method displays the add product form </p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException Thrown to indicate that an index of some sort (such as to an array, to a string, or to a vector) is out of range.
     */
    @FXML
    public void addProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * <p>This method removes the selected product from the main menu table if that product is not associated with any part.
     * Otherwise it prompts the user that product can't be removed.
     * It checks that ok button was selected and that there are products in a product table to be removed and that they are not associated with the part.
     * If user choose to cancel request it sets the scene to original position</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException Thrown to indicate that an index of some sort (such as to an array, to a string, or to a vector) is out of range.
     */
    @FXML
    public void removeProduct(ActionEvent actionEvent) throws IOException {
        Alert deleteProduct = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
        Optional<ButtonType> answer = deleteProduct.showAndWait();
        if ( productsTable.getSelectionModel().getSelectedItem()==null) {
            productNotFoundDialogBox();
        }
        if (answer.isPresent() && answer.get() == ButtonType.OK
                &&productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().size()==0) {
            Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
        }else if (answer.isPresent() && answer.get() == ButtonType.CANCEL){
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert partPresent = new Alert(Alert.AlertType.WARNING, "Can't delete product associated with part");
            Optional<ButtonType> answerPart = partPresent.showAndWait();
        }

    }


    /**
     * <p>This method search through the table that have available products and if available displays them accordingly.
     * If searchProductContent textfield is empty it returns all products available in allProducts observable list.
     * This method utilizes two methods lookupProduct. One method is looking for the part based on int argument and returns the product
     * whose id matches the argument. The other lookupProduct method takes the String argument and returns the Product object
     * which matches the String value in a whole or partially.
     * If the product is not available in the list the method warns user.</p>
     *
     * @param actionEvent the event that calls the method
     * @throws NumberFormatException if inappropriate data was entered
     */
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