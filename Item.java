

public class Item
{
    private String nameOfItem;
    private String description;
    private double weight;
    

    public Item( String nameOfItem, String description, double weight)
    {
        this.nameOfItem = nameOfItem;
        this.description = description;
        this.weight = weight;
    }
    
    public String getItemDescription(){
        return description;
    }
    public String getNameOfItem(){
        return nameOfItem;
    }
    
    public double getWeight(){
        return weight;
    }
}
