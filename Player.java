import java.util.ArrayList;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    public ArrayList<Item>items;
    private Room currentRoom;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room spawnRoom)
    {
        items = new ArrayList<>();
        currentRoom = spawnRoom;
    }
    
     public void addItem(Item item)
    {
        items.add(item);
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public void setCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
}
