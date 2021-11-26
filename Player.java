import java.util.List;
import java.util.ArrayList;

/**
 * Escreva uma descrição da classe Player aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Player
{
    private double maxWeight = 10.0;
    private double baggageWeight = 0.0;
    private Room currentRoom;
    private Parser parser;
    private List<Item> collectedItems;

    public Player(){
        collectedItems = new ArrayList<>();
        parser = new Parser();
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    public double getMaxWeight(){
        return maxWeight;
    }

    public void pickUpItem(Command command){
        boolean caught = false;
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        Item caughtItem = null;
        String itemName = command.getSecondWord();
        for(Item item : currentRoom.getItems()){
            if(itemName.equals(item.getNameOfItem())){
                if(baggageWeight < maxWeight){
                    baggageWeight += item.getWeight();
                    collectedItems.add(item);
                    caughtItem = item;
                }
                else
                    System.out.println("FULL BAG!");
            }
            else
                System.out.println("Item not found in this room!");

        }
        currentRoom.removeItem(caughtItem);
    }

    public void dropItem(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
            return;
        }
        Item droppedItem = null;
        String itemName = command.getSecondWord();
        if(collectedItems.isEmpty())
            System.out.println("EMPTY BAG!");
        else{
            for(Item item : collectedItems){
                if(itemName.equals(item.getNameOfItem())){
                    baggageWeight -= item.getWeight();
                    currentRoom.addItem(item);
                    droppedItem = item;
                }
                else
                    System.out.println("Item not found!");
            }
        }
        collectedItems.remove(droppedItem);

    }
    
    public String myBag(){
        String items = "" ;
        if(collectedItems.isEmpty())
            items += "----Bag without items----\n";
        else
            for(Item item: collectedItems){
                items += "Name:" 
                + item.getNameOfItem() 
                + "\nDescription: " 
                + item.getItemDescription() 
                + "\nWeight: "
                + item.getWeight()
                + " pounds\n\n";
            }
        return items;
    }

}
