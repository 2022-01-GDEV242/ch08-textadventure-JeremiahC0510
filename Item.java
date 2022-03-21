import java.util.HashMap;
/**
 * This is where items are declared and they're parameters are created here.
 *
 * @author (Jeremiah Curtis)
 * @version (3.18.22)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private HashMap<String, Item> itemList;
    private String name;
    private String itemDescription;
    private int weight;

    /**
     * Constructor for objects of class Item.
     */
    public Item(String name, String itemDescription, int weight)
    {
        this.name = name;
        this.itemDescription = itemDescription;
        this.weight = weight;
        itemList = new HashMap<String,Item>();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    private void addItems()
    {
        itemList.put("golden_key", new Item("golden_key", "a_golden_key_is_the_key_for_the_golden_door", 1));
        itemList.put("silver_key", new Item("silver_key", "a_silver_key_is_the_key_for_the_silver_door", 1));
        itemList.put("bronze_key",new Item("bronze_key", "a_bronze_key_is_the_key_for_the_bronze_door", 1));
        itemList.put("machete",new Item("manchete", "a_melee_weapon", 5));
        itemList.put("golden_apple", new Item("golden_apple", "a_golden_apple_is_an_apple_for_health_regen", 2));
        itemList.put("backpack", new Item("backpack", "a_backpack_increases_your_inventory_capacity", 2));
    }
    
     public String getItemDescription()
    {
        return itemDescription;
    }
}
