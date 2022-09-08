package wgu.softwareone.samircokic.inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author Samir Cokic
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart new part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * @param newProduct new product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId partId to lookup
     * @return the part
     * @RUNTIMEERROR
     * @FUTUREENCHANCMENTS
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * @param productId product id to lookup
     * @return the product if its present in allProducts or return null if not present
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * @param partName name of the part to lookup
     * @return list of parts if it or they match the name of the part in allParts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filter = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                filter.add(part);
            }
        }
        return filter;
    }

    /**
     * @param productName name of the product to lookup
     * @return list of products if it or they match the name of the part in allParts
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product>filter = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                filter.add(product);
            }
        }
        return filter;
    }

    /**
     * @param index        the index place in allParts list to be set
     * @param selectedPart the part to be updated
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * @param index      the index place in allProducts list to be set
     * @param newProduct the product to be updated
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart the part to be deleted if it is in the allParts list
     * @return true if part was deleted otherwise returns false
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : allParts) {
            if (part.equals(selectedPart)) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * @param selectedProduct the product to be deleted if it is in the allProducts list
     * @return true if product was deleted otherwise returns false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : allProducts) {
            if (product.equals(selectedProduct)) {
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * @return all parts contained in allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return all products contained in allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
