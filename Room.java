import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Jeremiah Curtis
 * @version 3.27.22
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> items;
    private HashMap<String, NPC> npcs;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
        npcs = new HashMap<String, NPC>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    { 
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemString() + "\n" + getNPCString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**WHAT I ADDED!!!!
     * This method is handling the creation of Items.
     * It holds their name, description and weight and adds
     * them to the hash map items.
     */
    public void setItem(String name, String description)
    {
         Set<String> keys = items.keySet();
        for(String item : keys)
            if (item.equals(name))
                return;
        
        Item newItem = new Item(description, name, 1);
        items.put(name, newItem);
    }
    
    /**WHAT I ADDED!!!!
     * This method works directly with setItem method but is
     * primarily used when printing out the long description
     * of what is in a Room.
     */
    private String getItemString()
    {
        String returnString = "Items:";
        Set<String> keys = items.keySet();
        for(String item : keys)
            returnString += " " + item;
        return returnString;
    }
    
    public Item getItem(String name)
    {
        return items.get(name);
    }
    
    /**WHAT I ADDED!!!!
     * This method handles the creation of NPCs.
     * It holds the Name and Description of each NPC.
     * It adds these to the Hash Map npcs.
     */
    public void addNPC(String name, String description)
    {
        Set<String> keys = npcs.keySet();
        for(String npc : keys)
            if (npc.equals(name))
                return;
    
        NPC newNPC = new NPC(name, description);
        npcs.put(name, newNPC);
    }
    
    public NPC getNPC(String name)
    {
        return npcs.get(name);
    }
    
    /**WHAT I ADDED!!!!
     * This method works directly with addNPC method but is
     * primarily used when printing out the long description
     * of what is in a Room.
     */
    public String getNPCString()
    {
        String returnString = "People:";
        Set<String> keys = npcs.keySet();
        for(String npc : keys)
            returnString += " " + npc;
        return returnString;
    }
}

