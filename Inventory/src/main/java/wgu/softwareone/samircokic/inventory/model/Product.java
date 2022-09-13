package wgu.softwareone.samircokic.inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author Samir Cokic
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the minimum to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part the part to set
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart the part to set
     * @return boolean true or false
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
     * @return all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
