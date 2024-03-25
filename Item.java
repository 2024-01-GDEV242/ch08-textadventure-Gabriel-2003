
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
