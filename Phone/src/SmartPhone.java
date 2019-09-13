import java.util.ArrayList;

public class SmartPhone extends Landline implements Computer{
    private State state;
    private int screenSize;
    private int RAM;
    private int processorSpeeed;
    private ArrayList<String> games = new ArrayList<>();
    private int numberOfGames = 0;

    public SmartPhone(String inputOwner, Long inputNumber, String inputState, int inputScreenSize, int inputRAM, int inputProcessorSpeed){
        super(inputOwner, inputNumber);
        this.setState(inputState);                  //Uses setState method to check whether or not inputState is a valid state before constructing this SmartPhone instance
        this.screenSize = inputScreenSize;
        this.RAM = inputRAM;
        this.processorSpeeed = inputProcessorSpeed;
    }
    public int getScreenSize(){             //Returns screen size of this SmartPhone instance
        return this.screenSize;
    }
    public int getRAM(){                    //Returns RAM of this SmartPhone instance
        return this.RAM;
    }
    public int getProcessorSpeeed(){        //Returns processor speed of this SmartPhone instance
        return this.processorSpeeed;
    }
    public State getState(){                //Returns state of this Smart Phone instance
        return this.state;
    }
    public void setState(String to) throws IllegalArgumentException{
        try {
            if (to.equals("on") || to.equals("ON")) {       //If inputState is "on" or "ON" will set state to the enum ON
                if(this.state == State.ON)                  //If SmartPhone instance is already on will print out appropriate message
                    System.out.println(this.getOwner() + "'s smart phone is already on.");
                this.state = State.ON;
            }
            else if (to.equals("off") || to.equals("OFF")) {    //If inputState is "off" or "OFF" will set state to the enum OFF
                if (this.state == State.OFF)                    //If SmartPhone instance is already off will print out appropriate message
                    System.out.println(this.getOwner() + "'s smart phone is already off.");
                this.state = State.OFF;
            }
            else
                throw new IllegalArgumentException();           //If input is invalid, an IllegalArgumentException is thrown and will print out appropriate message
        } catch(IllegalArgumentException e){
            System.out.println("ILLEGAL ARGUMENT: " + this.getOwner() + ", the state of your phone is invalid.");
        }
    }
    public void installGame(String gameName){
        if(this.state == State.OFF) {               //If SmartPhone instance is off, games cannot be installed and will print out appropriate message
            System.out.println(this.getOwner() + ", cannot install because smart phone is off.");
            return;
        }
        if(this.hasGame(gameName))                  //If game is already installed, nothing will happen
            return;
        if(numberOfGames == 5) {                    //If there are already 5 games installed, no more can be installed and an appropriate message will be printed
            System.out.println(this.getOwner() + ", cannot install " + gameName + " on your smart phone because you have the max number of games already.");
            return;
        }                                           //If all the previous checks are passed, game can be installed and an appropriate message will printed
        System.out.println("Installing " + gameName + " on " + this.getOwner() + "'s smart phone.");
        games.add(gameName);                        //Adds game to ArrayList associated with this SmartPhone instance
        numberOfGames++;                            //Increments number of games installed
    }
    public boolean hasGame(String gameName){    //Checks ArrayList if it contains a certain game
        return games.contains(gameName);
    }
    public void playGame(String gameName){
        if(this.state == State.OFF) {           //If SmartPhone is OFF, games cannot be played. An appropriate message will be printed.
            System.out.println(this.getOwner() + ", cannot play " + gameName + " because smart phone is off.");
            return;
        }
        if(this.hasGame(gameName))              //If SmartPhone has game. An appropriate message will be printed
            System.out.println(this.getOwner() + " is playing " + gameName + ".");
        else {
            if(numberOfGames == 5) {            //If SmartPhone does not have game installed and is at max number of games. An appropriate message will be printed.
                System.out.println("Cannot play " + gameName + " on " + this.getOwner() + "'s smart phone. Cannot install either, there is no more space.");
                return;
            }                                   //If SmartPhone does not have the game installed and isnt at max number of games. An appropriate message will be printed
            System.out.println("Cannot play " + gameName + " on " + this.getOwner() + "'s smart phone. Install it first.");
        }
    }
}
