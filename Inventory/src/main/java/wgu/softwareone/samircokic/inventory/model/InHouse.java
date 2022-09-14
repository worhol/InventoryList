package wgu.softwareone.samircokic.inventory.model;

/**
 * @author Samir Cokic
 * <p>This is a subclass of the abstract class Part that inherits Parts atributes and has its own methods that creates the part in the house giving it machine ID number</p>
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * @param id This is in house produced part's unique id number
     * @param name This is in house produced part's name
     * @param price This is in house produced part's price
     * @param stock This is the amount of in house produced parts available in inventory
     * @param min This is the minimum of in house produced available parts
     * @param max This is the maximum of in house produced available parts
     * @param machineId This the machine ID number for the part produced in house.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *<p>This method returns the machine ID number</p>
     *
     * @return machine id number
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * <p>This method takes the int as the argument and sets it as the machine id number</p>
     *
     * @param machineId This sets the machine id number
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
