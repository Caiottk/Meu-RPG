import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class Room 
{
    private String description;
    private HashMap <String, Room> exits;
    private List<Item> itemsInTheRoom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap <String,Room>();
        itemsInTheRoom = new ArrayList<Item>();
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }

    public void addItem(Item item){
        itemsInTheRoom.add(item);        
    }

    public void removeItem(Item item){
        itemsInTheRoom.remove(item);
    }

    public List<Item> getItems(){
        return itemsInTheRoom;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getExitString(){
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        return returnString;
    }

    public String getLongDescription(){
        String items = "" ;
        if(itemsInTheRoom.isEmpty())
            items += "----Room without items----\n";
        else
            for(Item item: itemsInTheRoom){
                items += "Name:" 
                + item.getNameOfItem() 
                + "\nDescription: " 
                + item.getItemDescription() 
                + "\nWeight: "
                + item.getWeight()
                + " pounds\n\n";
            }

        return "You are "
        + description + ".\n"
        +"Items: \n"
        + items
        + getExitString() + "\n";
    }

}
