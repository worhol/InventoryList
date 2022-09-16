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

/**
 * This class runs the logic of adding a product in GUI. It also implements Initializable interface
 *
 * @author Samir Cokic
 */
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

    /**
     *<p>This method initialize a controller after its root element has been completely processed. Once initialized it
     * displays the two tables with their respective column values.
     * Refer to <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">Oracle</a> </p>
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */
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

    /**
     * <p>This method takes the user back to the Main Menu</p>
     * @param actionEvent the event that calls the method
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void displayMainMenu(ActionEvent actionEvent) throws IOException {
        partsInProduct.clear();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/wgu/softwareone/samircokic/inventory/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * List of parts that are being added to the product
     */
    static ObservableList<Part> partsInProduct = FXCollections.observableArrayList();

    /**
     *<p>This method waits for part to be selected and then adds that part to partsInProduct observable list.
     * It then populates the associatedPartTable with new values. If part was not select user gets alerted.
     * <b>RUNTIME ERROR</b> The method was not checking for null value, that was creating an issue that method was adding blank space to
     * the table. Issue was resolved by adding an if statement that checks for null value and alerts the user
     * if part was not selected.</p>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void addPartToProduct(ActionEvent actionEvent) throws IOException {
        Part part = addProductPartsTable.getSelectionModel().getSelectedItem();
        if (part==null)   {
            partNotFoundDialogBox();
            return;
        }
        partsInProduct.add(part);
        associatedPartTable.setItems(partsInProduct);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /**
     * <p>This method removes the selected part associated with the product. It checks that ok button was selected and that there are parts in a
     * partsInProduct to be removed. If partsInProduct is empty it alerts the user that part cannot be deleted.
     * <b>RUNTIME ERROR</b> The method was not checking for null value. Issue was resolved by adding an if statement that checks for null value and alerts the user
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
            Part part = associatedPartTable.getSelectionModel().getSelectedItem();
            if (part==null)   {
                partNotFoundDialogBox();
                return;
            }
            if (answer.isPresent() && answer.get() == ButtonType.OK && partsInProduct.size() > 0) {
                partsInProduct.remove(part);
            }else if( partsInProduct.size() == 0){
                Alert alertNotFound = new Alert(Alert.AlertType.ERROR, "Part not found");
                alertNotFound.show();
            }
        } catch (IndexOutOfBoundsException e) {
            Alert alertNotFound = new Alert(Alert.AlertType.ERROR, "Part not found");
            alertNotFound.show();
        }
    }

    /**
     * <p>This method search through the table that have available parts to add to the product.
     * If searchContent textfield is empty it returns all parts available in allParts observable list.
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

    /**
     * <p>this method displays the error message.</p>
     */
    public static void partNotFoundDialogBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
        alert.show();
    }

    /**
     * <p>This method runs through allProducts list and checks for the largest number of id in it.
     * Then it adds 1 to that largest number and assign the value to new id</p>
     *
     * @return the unique id
     */
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
    /**
     * <p>This method upon receiving ActionEvent object as the argument, saves the entered values of the created part and moves to the main menu.
     *  It takes the value of id, name, inventory, price min and max to create a product. Then it calls the minIsLessThanMax() method and if that method returns true
     * product is created, and then it loops through the partsInProduct list and associating all parts in that list with product by adding them
     * to addAssociatedPart observable list. After the save button was clicked, the method takes the user to main menu.
     * The method also warns user if the inventory value is not in between min and max values, and if the min is larger than max.<br>
     *
     * @param actionEvent the event that calls the method
     * @throws IOException checks for the IllegalArgumentException if the user entered invalid values.
     */
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
                for (Part part : partsInProduct) {
                    product.addAssociatedPart(part);
                }
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
}
