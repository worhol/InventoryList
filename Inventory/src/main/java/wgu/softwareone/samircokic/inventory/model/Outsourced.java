package wgu.softwareone.samircokic.inventory.model;

/**
 * @author Samir Cokic
 *
 * <p>This is a subclass of the abstract class Part that inherits Parts atributes and has its own methods that creates the part that is being outsourced.</p>
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * @param id This is outsourced part's unique id number
     * @param name This is outsourced part's name
     * @param price This is outsourced part's price
     * @param stock This is outsourced the amount of parts available in inventory
     * @param min This is the minimum available of outsourced parts
     * @param max This is the maximum available of outsourced parts
     * @param companyName This is the name ouf the company that produces the outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * <p>This method returns the name of the outsourcing company.</p>
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>This method takes the String argument and sets it as the name of the outsourcing company.</p>
     *
     * @param companyName sets the name of the company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
