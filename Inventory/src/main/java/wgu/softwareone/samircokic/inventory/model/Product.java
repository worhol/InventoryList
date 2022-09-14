package wgu.softwareone.samircokic.inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Samir Cokic
 *
 * <p>The class Product is the blueprint for product object that user is creating.</p>
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * @param id  This is product's unique id number
     * @param name This is product's name
     * @param price This is product's price
     * @param stock This is quantity of the product's in inventory
     * @param min This is minimum quantity of the product
     * @param max This is maximum quantity of the product
     *
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * <p>This method returns the int value of the product's id</p>
     *
     * @return the unique id number
     */
    public int getId() {
        return id;
    }

    /**
     *  <p>This method takes the int value of the id and sets it as a product id</p>
     *
     * @param id the id number to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *  <p>This method returns the String value of the product's name</p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>This method takes the String as the argument and sets it as a product's name</p>
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>This method returns the price of the product</p>
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p>This method takes the double as the argument and sets it as a price of the product</p>
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <p>This method returns the number of available products in the inventory</p>
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * <p>This method takes the int as the argument and sets it as a inventory value</p>
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * <p>this method returns minimum availability of the product</p>
     *
     * @return minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * <p>This method takes the integer as the argument and sets it as a minimum availability of the product</p>
     *
     * @param min the minimum to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * <p>This method returns the maximum availability of the product</p>
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * <p>This method takes the integer as the argument and sets it as a maximum availability of the product</p>
     * @param max the maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * <p>This method takes as the argument the object of class Part and adds it to the list associatedParts of parts associated with the product </p>
     * @param part the part to set
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * <p>This method takes as the argument the object of class Part and removes it from the list associatedParts of parts associated with the product </p>
     * @param selectedAssociatedPart the part to be removed from the list of associated parts
     * @return boolean true if the part was sucesufully removed from the list or false if part was not removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part part : associatedParts) {
            if (part.equals(selectedAssociatedPart)) {
                associatedParts.remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>This method returns all parts associated with the product object</p>
     *
     * @return all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
