import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
/**
 * This is where item's parameters are created.
 * There are also methods in this class that control different
 * aspects of items so you can get their name and description.
 *
 * @author Jeremiah Curtis
 * @version 3.27.22
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item.
     */
    public Item(String name, String itemDescription, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    public String getItemName()
    {
        return name;    
    }
    public String getItemDescription()
    {
        return description;
    }
}
