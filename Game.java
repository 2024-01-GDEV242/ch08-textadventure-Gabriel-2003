/**
 *  This class in the main game I have with is basically a walking school simmulator
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Jose Gabriel Torres
 * @version 2024.03.25
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room lastRoom;
    private Room npcClassroom;
    private Player me;
    private Player NPC;
    private long limit;
    private long start;
    private long end;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        limit = 420000;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, hallway, hallway1, hallway2, hallway3, lab, storage, classroom, classroom1, classroom2, classroom3, janitor, 
        parking, office, window;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        theater.addItem(new Item("flyer", 1));
        hallway = new Room("You are in the hallway to get to classrooms");
        hallway1 = new Room("You have gone deeper into the hallway");
        hallway2 = new Room("You are almost at the end of the hallway");
        hallway3 = new Room("You have reached the end of the hallway");
        office = new Room("You have entered the Teacher's Office. Oh no we can't get out. Look, an open window at the corner of his office.");
        office.addItem(new Item("paperwork", 4));
        office.addItem(new Item("your expulsion", 1));
        classroom = new Room("You have entered one of the math classrooms");
        npcClassroom = classroom;
        classroom1 = new Room("You have entered the other math classrooms");
        classroom2 = new Room("You have entered a boring lecture, please leave");
        classroom3 = new Room("You have entered an empty classroom");
        janitor = new Room("This is the janitor's closet. Love the smell of clean");
        janitor.addItem(new Item("hand sanitizer", 2));
        parking = new Room("You are in the parking lot you now could go to the main out side or back in the building");
        lab = new Room("in a computing lab");
        lab.addItem(new Item("pen", 1));
        lab.addItem(new Item("computer", 5));
        storage = new Room("You are in the computer storage room");
        storage.addItem(new Item("wires", 3));
        window = new Room("Alright, out of his office. Can't go back there. Let's go back to the enterance.");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", hallway);
        outside.setExit("north", parking);


        theater.setExit("west", outside);

        hallway.setExit("east", outside);
        hallway.setExit("north", hallway1);
        hallway.setExit("west", office);
        
        hallway1.setExit("south", hallway);
        hallway1.setExit("west", classroom1);
        hallway1.setExit("east", classroom);
        hallway1.setExit("north", hallway2);
        
        hallway2.setExit("south", hallway1);
        hallway2.setExit("west", classroom2);
        hallway2.setExit("east", classroom3);
        hallway2.setExit("north", hallway3);
        
        hallway3.setExit("south", hallway2);
        hallway3.setExit("west", janitor);
        hallway3.setExit("east", parking);
        
        office.setExit("south", window);
        
        window.setExit("east", outside);
        
        classroom1.setExit("east", hallway1);
        
        classroom.setExit("west", hallway1);
        
        classroom2.setExit("east", hallway2);
        
        classroom3.setExit("west", hallway2);
        
        janitor.setExit("east", hallway3);
        
        parking.setExit("west", hallway3);
        parking.setExit("south", outside);
        
        lab.setExit("north", outside);
        lab.setExit("east", storage);

        storage.setExit("west", lab);
        
        //Creates an NPC.
        NPC = new Player(lab);

        
        currentRoom = outside;  // start game outside
        lastRoom = null;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        start = System.currentTimeMillis();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        me = new Player(currentRoom);
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
             if(limit < 30000 && NPC.getCurrentRoom() != npcClassroom)
           {
               NPC.setCurrentRoom(npcClassroom);
           }
           
             if(limit <= 0)
           {
               finished = true;
               System.out.println("You ran out of time, Goodbye.");
           }
        }
        
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome To The College Campus!");
        System.out.println("This is just a college simmulator");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
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
                limit = limit - System.currentTimeMillis() - start;
                break;
            
            case BACK:
                goBack(command);
                limit = limit - System.currentTimeMillis() - start;
                break;
                
            case EAT:
                System.out.println("You ate the food.");
                
            case LOOK:
                System.out.println(currentRoom.getLongDescription());
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
            System.out.println("Nope, can't go there.");
        }
        else {
            lastRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
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
    
    private void goBack(Command command)
    {
        if(lastRoom != null)
        {
            Room nextRoom = lastRoom;
            lastRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        else
        {
            System.out.println("Nah, can't do that.");
        }
    }
}
