
/**
 * Write a description of class Item here.
 *
 * @author Jose Gabriel Torres
 * @version 2024.03.25
 */
public class Item
{
    // instance variables - replace the example below with your own
    private int weight;
    private String descr;

    /**
     * Constructor for objects of class Item
     */
    public Item(String descr, int weight)
    {
        // initialise instance variables
        this.weight = weight;
        this.descr = descr;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getDescr()
    {
        return descr;
    }

}
