package wgu.softwareone.samircokic.inventory.model;

/**
 *@author  Supplied class Part.java
 *
 * <p>This class is the abstract class and is used as a blueprint for creation of Part object.</p>
 */

public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id This is part's unique id number
     * @param name This is part's name
     * @param price This is part's price
     * @param stock This is the amount of parts available in inventory
     * @param min This is the minimum available parts
     * @param max This is the maximum available parts
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * <p>This method returns the int value of the part's id</p>
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * <p>This method takes the int value of the id and sets it as a part's id</p>
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *<p>This method returns the String value of the part's name</p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *  <p>This method takes the String as the argument and sets it as a part's name</p>
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  <p>This method returns the price of the part</p>
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p>This method takes the double as the argument and sets it as a price of the part</p>
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <p>This method returns the number of available parts in the inventory</p>
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * <p>This method takes the int as the argument and sets it as a inventory value</p>
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * <p>this method returns minimum availability of the part</p>
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * <p>This method takes the integer as the argument and sets it as a minimum availability of the part</p>
     *
     * @param min the min to set
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
     * <p>This method takes the integer as the argument and sets it as a maximum availability of the part</p>
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}