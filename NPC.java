
/**
 * This NPC class is handling the creation parameters of each NPC.
 *
 * @author Jeremiah Curtis
 * @version 3.27.22
 */
public class NPC
{
    private String name;
    private String description;

    public NPC(String name, String description)
    {
        this.description = description;
        this.name = name;
    }


    public String getDescription()
    {
        return description;
    }
    
    public String getName()
    {
        return name;
    }
}
