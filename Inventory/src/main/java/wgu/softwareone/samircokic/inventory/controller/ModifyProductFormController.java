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

/**
 * <p>This class is a blueprint for modify product form logic</p>
 *
 * @author Samir Cokic
 */
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

    /**
     * <p>This method initialize a controller after its root element has been completely processed. Once initialized it
     * displays the two tables with their respective column values.
     * Refer to <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">Oracle</a> </p>
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */
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

    /**
     * <p>This method takes the user back to the Main Menu</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * <p>This method removes the selected part associated with the product. It checks that ok button was selected and that there are parts in a
     * partsInProduct to be removed. If partsInProduct is empty it alerts the user that part cannot be deleted.
     * <b>RUNTIME ERROR</b>There was an issue where user would choose to remove the associated part and after clicking the button
     * "Part not found" alert would pop up and part wouldn't be removed.
     *  The issue uncovered flaw in a code of the ModifiedProductFormController class. The issue was due to
     *  parallel persistence, where parts to be added to product were being stored in additional(unnecessary) observable list.
     *  This was causing the method to not access the observable list getAllAssociatedParts where added part were stored.
     *  The class logic was refactored, the unnecessary list was removed.
     *  The method was also not checking for null value. Issue was resolved by adding an if statement that checks for null value and alerts the user
     *  if part to be removed was not selected.</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IndexOutOfBoundsException Throws the exception if no object was selected
     */
    @FXML
    public void removeAssociatedPart(ActionEvent actionEvent) throws IndexOutOfBoundsException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
        Optional<ButtonType> answer = alert.showAndWait();
        try {
            Part part = modifyProductAssociatedPartTable.getSelectionModel().getSelectedItem();
            Product product = Inventory.lookupProduct(Integer.valueOf(modifyProductId.getText()));
            if (part==null)   {
                partNotFoundDialogBox();
                return;
            }
            if (answer.isPresent() && answer.get() == ButtonType.OK && product.getAllAssociatedParts().size() > 0) {
                product.deleteAssociatedPart(part);
            } else if (answer.isPresent() && answer.get() == ButtonType.OK && product.getAllAssociatedParts().size() == 0) {
                Alert alertNotFound = new Alert(Alert.AlertType.ERROR, "Part not found");
                alertNotFound.show();
            }
        } catch (IndexOutOfBoundsException e) {
            Alert alertNotFound = new Alert(Alert.AlertType.ERROR, "Part not found");
            alertNotFound.show();
        }

    }

    /**
     * <p>This method serves as the bridge between add product form and modify product. It sets the values of the product and when called displays
     * the values in modify product form.</p>
     *
     * @param product product to be used
     */
    public void sendProduct(Product product) {
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyProductAssociatedPartTable.setItems(product.getAllAssociatedParts());
    }

    /**
     * <p>This method upon receiving ActionEvent object as the argument, saves the modified values of the modified product and moves to the main menu.
     * It takes the value of id, name, inventory, price min and max to create a product. Then it calls the minIsLessThanMax() method and if that method returns true
     * product is created, and then it loops through the partsInModifyProduct list and associating all parts in that list with product by adding them
     * to addAssociatedPart observable list. After the save button was clicked, the method takes the user to main menu.
     * The method also warns user if the inventory value is not in between min and max values, and if the min is larger than max.<br>
     * <b>RUNTIME ERROR</b> The method was creating issue with not saving added parts. The issue was resolved making product adding parts from the modifyProductAssociatedPartTable</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException checks for the IllegalArgumentException if the user entered invalid values.
     */
    @FXML
    public void saveModifiedProduct(ActionEvent actionEvent) throws IOException {
        int index = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(Integer.parseInt(modifyProductId.getText())));
        try {
            int id = Integer.valueOf(modifyProductId.getText());
            String name = modifyProductName.getText();
            int inventory = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            if (minIsLessThanMax()) {
                Product product = new Product(id,name,price,inventory,min,max);
                for (Part part: modifyProductAssociatedPartTable.getItems()){
                    product.addAssociatedPart(part);
                }
                Inventory.updateProduct(index, product);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max!");
                alert.showAndWait();
            } else if (inventory > max || inventory < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value should be between min and max values!");
                alert.showAndWait();
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Data");
            alert.setContentText("Please enter valid data for each field");
            alert.showAndWait();
        }

    }

    /**
     * <p>This method checks if the min value is less than max value and that inventory value is in between those two.
     * It returns true if condition is right and false otherwise.<br>
     * <b>RUNTIME ERROR</b> The issues with this method were if the inappropriate data was entered for example
     * String value instead of int value. In order to avoid that, the exception handling was used that warns the user
     * about the inappropriate data entered</p>
     *
     * @return the boolean true or false
     */
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

    /**
     * <p>This method waits for part to be selected and then adds that part to partsInModifyProduct observable list.
     * It then populates the associatedPartTable with new values. If part was not select user gets alerted.<br>
     * <b>RUNTIME ERROR</b> The method was creating an issue previously due to it adding the associated part to parallel
     * observable list. That list was removed and part is now being added to associatedPart list.
     * Also method was not checking for null value, that was creating an issue that method was adding blank space to
     * the table. Issue was resolved by adding an if statement that checks for null value and alerts the user
     * if part was not selected. </p>
     *
     * @param actionEvent the event that calls the method
     */
    @FXML
    public void addPartToModifyProduct(ActionEvent actionEvent) {
        Product product = Inventory.lookupProduct(Integer.valueOf(modifyProductId.getText()));
        Part part = modifyProductAddPartTable.getSelectionModel().getSelectedItem();
        if (part==null)   {
            partNotFoundDialogBox();
            return;
        }
        product.addAssociatedPart(part);
        modifyProductAssociatedPartTable.setItems(product.getAllAssociatedParts());
    }

    /**
     * <p>This method search through the table that have available parts to add to the product.
     * If searchPartInModfyProduct textfield is empty it returns all parts available in allParts observable list.
     * This method utilizes two methods lookupPart. One method is looking for the part based on int argument and returns the part
     * the part whose id matches the argument. The other lookupPart method takes the String argument and returns the Part object
     * which matches the String value in whole or partially.
     * If the part is not available in the list the method warns user.
     * <b>RUNTIME ERROR</b> The error that was coming running this method was that search content was not properly displaying
     * parts after the consequent search because previous search would stay in the parts observable list. In order to avoid that each time
     * method was called it also called the clearSelection() method which would clear the previous search content.</p>
     *
     * @param actionEvent the event that calls the method
     */
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

    /**
     * <p>this method displays the error message.</p>
     */
    public static void partNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
        alert.show();
    }
}
