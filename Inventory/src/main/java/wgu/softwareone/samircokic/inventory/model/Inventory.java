package wgu.softwareone.samircokic.inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Samir Cokic
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart new part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }


    /**
     * @param newProduct new product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * @param partId partId to lookup
     * @return the part
     */
    public static Part lookupPart(int partId){
        for (Part part: allParts){
            if (part.getId()==partId){
                return part;
            }
        }
        return null;
    }

    /**
     * @param productId product id to lookup
     * @return the product if its present in allProducts or return null if not present
     */
    public static Product lookupProduct(int productId){
        for (Product product: allProducts){
            if (product.getId()==productId){
                return product;
            }
        }
        return null;
    }

    /**
     * @param partName name of the part to lookup
     * @return list of parts if it or they match the name of the part in allParts or null if not
     */
    public static ObservableList<Part> lookupPart(String partName){
        for (Part part: allParts){
            if (part.getName().contains(partName)){
                return allParts;
            }
        }
        return null;
    }

    /**
     * @param productName name of the product to lookup
     * @return list of products if it or they match the name of the part in allParts or null if not
     */
    public static ObservableList<Product> lookupProduct(String productName){
        for (Product product: allProducts){
            if (product.getName().contains(productName)){
                return allProducts;
            }
        }
        return null;
    }



}
