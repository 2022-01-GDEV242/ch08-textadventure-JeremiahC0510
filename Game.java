import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Jeremiah Curtis
 * @version 3.27.22
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom, lastRoom;
    private Room beamer;
    private int health = 5;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     * Also, creates Items and NPCs.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, gym, gymOffice, equipment, lockerRoom, hallway, backstage, cafeteria, principal, art, server, nurse;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        gym = new Room("in the gym");
        gymOffice = new Room("in the gym office");
        equipment = new Room("in the equipment room for the gym");
        lockerRoom = new Room("in the locker room");
        hallway = new Room("in the hallway");
        backstage = new Room("behind the curtain backstage of the theater");
        cafeteria = new Room("in the cafeteria");
        principal = new Room("in the principal's office");
        art = new Room("in the art room");
        server = new Room("in the server room");
        nurse = new Room("in the nurse's office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", hallway);
        outside.setExit("north", gym);
        theater.setExit("west", outside);
        theater.setExit("east", backstage);
        backstage.setExit("west", theater);
        pub.setExit("east", hallway);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        office.setExit("south", server);
        server.setExit("north", office);
        gym.setExit("south", outside);
        gym.setExit("east", gymOffice);
        gym.setExit("north", lockerRoom);
        gymOffice.setExit("west", gym);
        gymOffice.setExit("east", equipment);
        equipment.setExit("west", gymOffice);
        lockerRoom.setExit("south", gym);
        hallway.setExit("west", pub);
        hallway.setExit("north", cafeteria);
        hallway.setExit("east", outside);
        hallway.setExit("south", art);
        cafeteria.setExit("north", nurse);
        cafeteria.setExit("west", principal);
        cafeteria.setExit("south", hallway);
        principal.setExit("east", cafeteria);
        nurse.setExit("south", cafeteria);
        art.setExit("north", hallway);
        
        // placing items throughout the map
        outside.setItem("hallpass", "it is colorful and attached to a keychain");
        outside.setItem("schoolID", "you can use this school idea to enter the University");
        theater.setItem("$10", "you found this under the seats in the theater");
        lab.setItem("calculator", "this calculator has the name Paul on the back, maybe you can find him");
        hallway.setItem("glasses", "these glasses are broken");
        cafeteria.setItem("sandwhich", "this sandwich looks yummy");
        lockerRoom.setItem("clothes", "you see your gym clothes in your locker");
        
        // setting NPC spawns
        principal.addNPC("Principal O'Neil", "The principal of the University");
        
        currentRoom = outside;  // start game outside
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**WHAT I ADDED!!!!
     * healthTracker method is used to organize and keep track
     * of the player's health.
     */
    private String healthTracker(){
        String healthString = "Health: ";
        return healthString + health;
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription() + "\n" + healthTracker());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case BACK:
                back(command);
                break;
                
            case TELEPORTER:
                setBeamer(currentRoom);
                break;
                
            case TELEPORT:
                beam();
                break;
                
            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if(health <= 0){
            System.out.println("You are too hungry to move, try eating instead");
        }
        else{
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            lastRoom = nextRoom;
            health = health - 1;
            System.out.println(currentRoom.getLongDescription() + "\n" + healthTracker());
        }
    }
    
    /**WHAT I ADDED!!!!
     * Creating a look method that reprints the room the player
     * is in with details.
     */
    private void look(){
        System.out.println("You are " + currentRoom.getLongDescription() + "\n" + healthTracker()); 
    }
    
    /**WHAT I ADDED!!!!
     * Eat method that allows the player to eat.
     * A player must eat in order to be able to move.
     */
    private void eat(){
        if(health >= 0 && health <=4){
        health++;
        System.out.println("You have eaten, and your health has increased!" + "\n" + healthTracker());
        }
        else if (health >= 5){
            System.out.println("You're health is Full!");
        }
    }   
    
    /**WHAT I ADDED!!!!
     * setBeamer allows the player to set a teleporter in the 
     * current room they are in. This allows the player to 
     * teleport back whenever they want.
     */
    private void setBeamer(Room room)
    {
        beamer = currentRoom;
        System.out.println("You have set a teleporter device in this room.");
        System.out.println("You can teleport back here using 'Teleport'");
    }
    /**WHAT I ADDED!!!!
     * beam is the method that handles the teleporting as long
     * as the player set a room to teleport to.
     */
    private boolean beam()
    {
        if (beamer == null){
            System.out.println("You have not set your teleporter. You can do so by using 'Teleporter'");
            return false;
        }
        previousRoom = currentRoom;
        currentRoom = beamer;
        System.out.println("You have teleported! So, " + currentRoom.getLongDescription() + "\n" + healthTracker());
        return true;
    }
    
    /**WHAT I ADDED!
     * Back command allows the player to go back to the room
     * they were previously in.
     */
    private void back(Command command)
    {
        if(currentRoom != previousRoom){
        currentRoom = previousRoom;
        System.out.println(currentRoom.getLongDescription() + "\n" + healthTracker());
        }
        else{
        currentRoom = lastRoom;
        System.out.println(currentRoom.getLongDescription() + "\n" + healthTracker());
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}

