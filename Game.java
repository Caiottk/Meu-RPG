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
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game 
{
    private Parser parser;
    private Player player;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, cellar;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        outside.addItem(new Item("Key", "an old silver key with unknown inscriptions", 0.637)); 
        theatre = new Room("in a lecture theatre");
        theatre.addItem(new Item("Papers", "a passage from the original manuscript of Hamlet", 0.07));
        theatre.addItem(new Item("Mask", "an ornate mask from the venusian era", 1.76));
        pub = new Room("in the campus pub");
        pub.addItem(new Item("Ingredients", "hops, malt and yeast ingredients for a recipe", 5.73));
        lab = new Room("in a computing lab");
        lab.addItem(new Item("Book", "an old, dusty book bound in gray leather", 2.65));
        lab.addItem(new Item("DVD", "a worn out DVD with strange instructions", 0.330));
        office = new Room("in the computing admin office");
        office.addItem(new Item("Envelope", "letter addressed to the dean", 0.02));
        office.addItem(new Item("Book", "university textbook", 1.54));
        office.addItem(new Item("Calendar", "calendar with important events", 0.1));
        cellar = new Room("in the cellar");
        cellar.addItem(new Item("Box", "small unopened box with ancient runes", 4.81));
        cellar.addItem(new Item("Paper", "a paper that appears to contain instructions for a treasure", 0.01));

        // initialise room exits
        outside.setExits("east", theatre);
        outside.setExits("south", lab);
        outside.setExits("west", pub);
        theatre.setExits("west", outside);
        pub.setExits("east", outside);
        lab.setExits("north",outside);
        lab.setExits("east",office);
        office.setExits("west", lab);
        office.setExits("down", cellar);
        cellar.setExits("up", office);

        player.setCurrentRoom(outside);  // start game outside
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

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if (commandWord.equals("look"))
            look();
        else if(commandWord.equals("take"))
            player.pickUpItem(command);
        else if(commandWord.equals("drop"))
            player.dropItem(command);
        else if(commandWord.equals("myBag"))
            printMyBag();
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
        for(String command : parser.showCommands())
            System.out.print(command + " ");
        System.out.println();
    }
    
    private void printMyBag(){
        System.out.println(player.myBag());
    }

    private void look(){
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    
    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = player.getCurrentRoom().getExit("north");
        }
        if(direction.equals("east")) {
            nextRoom = player.getCurrentRoom().getExit("east");
        }
        if(direction.equals("south")) {
            nextRoom = player.getCurrentRoom().getExit("south");
        }
        if(direction.equals("west")) {
            nextRoom = player.getCurrentRoom().getExit("west");
        }
        if(direction.equals("down")) {
            nextRoom = player.getCurrentRoom().getExit("down");
        }
        if(direction.equals("up")) {
            nextRoom = player.getCurrentRoom().getExit("up");
        }


        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    private void printLocationInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
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
