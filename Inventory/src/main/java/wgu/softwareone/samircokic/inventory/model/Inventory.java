package wgu.softwareone.samircokic.inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Samir Cokic
 *
 * <p>This class is class contains two obesrvable lists that serve as the storage for the created parts and products
 * in the inventory managment system application. It also contain the methods used for the manipulation of mentioned lists.</p>
 */
public class Inventory {
    /**
     * This observable list contains the created parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * This observable list contains the created products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * <p>This method takes the object of Part and adds it to the allParts list.</p>
     *
     * @param newPart new part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * <p>This method takes the object of Product and adds it to the allProducts list.</p>
     *
     * @param newProduct new product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * <p>This method takes the int as the argument and compares it to the parts ids contained in the allParts list.
     * If the argument matches the available id number, the method returns the part object associated with the id.
     * If the argument doesn't matches the available ids, the method returns null</p>
     *
     * @param partId partId to lookup.
     * @return the part.
     *
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
     *<p>This method takes the int as the argument and compares it to the products ids contained in the allProducts list.
     * If the argument matches the available id number, the method returns the product object associated with the id.
     * If the argument doesn't matches the available ids, the method returns null</p>
     *
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
     *<p>This method takes the String as the argument and compares it to the parts names contained in the allParts list.
     * If the argument matches the available name or part of the name, the method returns the observable list of part objects associated with the argument name.</p>
     *
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
     * <p>This method takes the String as the argument and compares it to the product names contained in the allProducts list.
     * If the argument matches the available name or part of the name, the method returns the observable list of product objects associated with the argument name.</p>
     *
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
     * <p>This method takes two arguments int index which is the location in the allParts list and Part object to be set at the index location.</p>
     *
     * @param index the index place in allParts list to be set
     * @param selectedPart the part to be updated
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * <p>This method takes two arguments int index which is the location in the allParts list and Product object to be set at the index location.</p>
     *
     * @param index the index place in allProducts list to be set
     * @param newProduct the product to be updated
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * <p>This method takes the Part object as the argument and if its present in the allParts list removes it from the list.
     * It then returns boolean true. If part is not present in the list, the method returns false.</p>
     *
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
     * <p>This method takes the Product object as the argument and if its present in the allProducts list removes it from the list.
     * It then returns boolean true. If product is not present in the list, the method returns false.</p>
     *
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
     * <p>This method returns observable list and all parts contained in it</p>
     *
     * @return all parts contained in allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * <p>This method returns observable list and all products contained in it</p>
     *
     * @return all products contained in allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
