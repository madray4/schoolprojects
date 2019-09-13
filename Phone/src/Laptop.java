import java.util.ArrayList;

public class Laptop implements Computer {
    private String owner;
    private State state;
    private int screenSize;
    private int RAM;
    private int processorSpeed;
    private ArrayList<String> games = new ArrayList<>();
    private int numberOfGames = 0;
    private String brand;
    private String hostname;

    public Laptop(String inputOwner, String inputState, int inputScreenSize, int inputRAM, int inputProcessorSpeed, String inputBrand){
        this.owner = inputOwner;
        this.setState(inputState);                  //Uses setState method to check whether or not inputState is a valid state before constructing this Laptop instance
        this.screenSize = inputScreenSize;
        this.RAM = inputRAM;
        this.processorSpeed = inputProcessorSpeed;
        this.brand = inputBrand;
        this.hostname = inputOwner + "'s " + inputBrand + " laptop";
    }
    public String getOwner(){       //Returns owner of this Laptop instance
        return this.owner;
    }
    public String getBrand(){       //Returns brand of this Laptop instance
        return this.brand;
    }
    public String getHostname(){    //Returns host name of this Laptop instance
        return this.hostname;
    }
    public int getScreenSize(){     //Returns screen size of this Laptop instance
        return this.screenSize;
    }
    public int getRAM(){            //Returns RAM of this Laptop instance
        return this.RAM;
    }
    public int getProcessorSpeeed(){    //Returns processor speed of this Laptop instance
        return this.processorSpeed;
    }
    public State getState(){        //Returns sate of this Laptop instance
        return this.state;
    }
    public void setState(String to){
        try {
            if (to.equals("on") || to.equals("ON")) {       //If inputState is "on" or "ON" will set state to the enum ON
                if(this.state == State.ON)                  //If Laptop instance is already on will print out appropriate message
                    System.out.println(this.getHostname() + "is already on.");
                this.state = State.ON;
            }
            else if (to.equals("off") || to.equals("OFF")) {    //If inputState is "off" or "OFF" will set state to the enum OFF
                if(this.state == State.OFF)                     //If Laptop instance is already off will print out appropriate message
                    System.out.println(this.getHostname() + "is already off.");
                this.state = State.OFF;
            }
            else
                throw new IllegalArgumentException();           //If input is invalid, an IllegalArgumentException is thrown and will print out appropriate message
        } catch(IllegalArgumentException e){
            System.out.println("ILLEGAL ARGUMENT: " + this.getOwner() + ", the state of your laptop is invalid.");
        }
    }
    public void installGame(String gameName){
        if(this.state == State.OFF) {               //If Laptop instance is off, games cannot be installed and will print out appropriate message
            System.out.println(this.getOwner() + ", cannot install because laptop is off.");
            return;
        }
        if(this.hasGame(gameName))                  //If game is already installed, nothing will happen
            return;
        if(numberOfGames == 5){                     //If there are already 5 games installed, no more can be installed and an appropriate message will be printed
            System.out.println(this.getOwner() + "'s laptop already has 5 games and cannot install anymore.");
            return;
        }                                           //If all the previous checks are passed, game can be installed and an appropriate message will printed
        System.out.println("Installing " + gameName + " on " + this.getHostname());
        games.add(gameName);                        //Adds game to ArrayList associated with this Laptop instance
        numberOfGames++;                            //Increments number of games installed
    }
    public boolean hasGame(String gameName){        //Checks ArrayList if it contains a certain game
        return games.contains(gameName);
    }
    public void playGame(String gameName){
        if(this.state == State.OFF) {               //If Laptop is OFF, games cannot be played. An appropriate message will be printed.
            System.out.println(this.getOwner() + ", cannot play because laptop is off.");
            return;
        }
        if(this.hasGame(gameName))                  //If Laptop has game. An appropriate message will be printed
            System.out.println(this.getOwner() + " is playing " + gameName + " on their " + this.getBrand() +" laptop.");
        else if(numberOfGames == 5)                 //If Laptop does not have game installed and is at max number of games. An appropriate message will be printed.
            System.out.println("Cannot play " + gameName + " on " + this.getOwner() + "'s smart phone. Cannot install either, there is no more space.");
        else                                        //If Laptop does not have the game installed and isnt at max number of games. An appropriate message will be printed
            System.out.println("Cannot play " + gameName + " on " + this.getHostname() + ". Install it first.");
    }

}
