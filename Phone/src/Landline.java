import java.util.Scanner;
import java.util.HashMap;

public class Landline extends OldLandline {
    private HashMap<Integer,Messages> messages;
    private int numberOfMessages = 0;
    public enum MSG_STATUS {READ, UNREAD}

    public Landline(String inputOwner, Long inputNumber){   //Constructor for Landline instances
        super(inputOwner, inputNumber);                     //inputOwner and inputNumber can be changed using super as the Landline instance uses these
        this.messages = new HashMap<>();                    //Creates a hashmap to store and read messages
    }
    @Override                                               //Overrides Oldlandline's receive method as Landline has voicemail functions
    public void receive(Phone from){
        if(this.isBusy()){                                  //If this phone instance is busy, it allows the caller to leave a message
            System.out.println(from.getOwner() + " is unable to call " + this.getOwner() + ". Line is currently busy.");
            boolean validAnswer = false;
            Scanner input = new Scanner(System.in);
            String answer;
            while(!validAnswer) {
                System.out.println("Does " + from.getOwner() + " want to leave a message for " + this.getOwner() + "? [y/n]");
                answer = input.nextLine();                  //Records whether or not the caller would like to leave a message for this phone instance
                if (answer.equals("y")) {                   //If answer is "y", leaves a message and places it in a hashmap where the key is based on how many messages were left before
                    validAnswer = true;                     //Breaks loop if answer is valid
                    System.out.println("Please leave a message for " + this.getOwner());
                    answer = input.nextLine();
                    this.leaveMessage(answer, messages);    //Calls the leaveMessage method to leave a message for this instance
                    System.out.println(from.getOwner() + " left a message for " + this.getOwner() + ".");
                } else if (answer.equals("n")) {
                    validAnswer = true;                     //If answer is "n", prints an appropriate message and changes validAnswer to true to break the loop
                    System.out.println(from.getOwner() + " did not leave a message for " +this.getOwner() + ".");
                } else {                                    //If the answer is neither "y" or "n" then validAnswer is kept false and loops again to attempt to get a valid response from user
                    System.out.println("That is not a valid input, please try again.");
                }
            }
        }
        else{
            this.setInCall(true);           //If this phone instance isn't busy when getting called, inCall is set to true to reflect this instance picking up the phone
            this.setCallWith(from);         //Sets callWith so there's a variable keeping track of who
        }
    }
    public void readMessages(){             //Overloaded method, if no argument is given all messages are read
        if(this.getInCall()){               //If this phone is currently in call, messages cannot be checked and an appropriate message is printed
            System.out.println(this.getOwner() + ", you are currently in a call and can't check your messages.");
            return;
        }
        System.out.println(this.getOwner() + " is checking ALL their messages.");
        for(int i = 0; i<numberOfMessages; i++){            //Prints that this phone is checking all their messages and prints all messages
            System.out.println("     " + messages.get(i));  //Since messages are placed in hashmap in order by using a counter as the key, all messages can be accessed in order using a counter
            messages.get(i).messageRead();                  //If message is UNREAD, message will now be marked READ
        }
        if(numberOfMessages == 0)                           //If there are no messages, prints appropriate message
            System.out.println(this.getOwner() + ", there are no messages in your inbox.");
        else                                                //If there are messages, prints appropriate message when all messages have been printed
            System.out.println(this.getOwner() + ", all your messages have been printed.");
    }
    public void readMessages(MSG_STATUS status){            //Overloaded method WITH argument
        if(this.getInCall()){                               //If this phone is currently in call, messages cannot be checked and an appropriate message is printed
            System.out.println(this.getOwner() + ", you are currently in a call and can't check your messages.");
            return;
        }
        System.out.println(this.getOwner() + " is checking their messages.");
        boolean haveUnreadMessages = false;                 //Boolean check for if there are any UNREAD messages
        boolean haveReadMessages = false;                   //Boolean check for if there are any READ messages
        for(int i = 0; i<numberOfMessages; i++) {           //Iterates through all messages
            if(status == MSG_STATUS.READ){
                if(messages.get(i).status == MSG_STATUS.READ){
                    System.out.println("     " + messages.get(i));  //If user would only like to see READ messages, this will print out all messages that are marked READ
                    haveReadMessages = true;                        //If there are any READ messages, this becomes true
                }
            }
            else if(status == MSG_STATUS.UNREAD){
                if(messages.get(i).status == MSG_STATUS.UNREAD){    //If user would only like to see UNREAD messages, this will print out all messages that are marked UNREAD
                    System.out.println("     " + messages.get(i));
                    messages.get(i).messageRead();                  //Once an UNREAD message is read, it must be changed to READ
                    haveUnreadMessages = true;                      //If there are any UNREAD messages, this becomes true
                }
            }
        }
        if(status == MSG_STATUS.UNREAD && !haveUnreadMessages) {    //Prints appropriate message if there are no UNREAD messages
            System.out.println(this.getOwner() + ", you have no UNREAD messages in your inbox.");
            return;
        }
        else if(status == MSG_STATUS.UNREAD && haveUnreadMessages){ //Prints appropriate message when all UNREAD messages have been read
            System.out.println(this.getOwner() + ", all your UNREAD messages have been printed.");
            return;
        }
        if(status == MSG_STATUS.READ && !haveReadMessages) {    //Prints appropriate message if there are no READ messages
            System.out.println(this.getOwner() + ", you have no READ messages in your inbox.");
            return;
        }
        else if(status == MSG_STATUS.READ && haveReadMessages){ //Prints appropriate message when all READ messages have been read
            System.out.println(this.getOwner() + ", all your READ messages have been printed.");
            return;
        }
    }
    public void leaveMessage(String newMessage, HashMap<Integer,Messages> inbox){       //Places messaged inside the hashmap associated with this phone instance
        Messages input = new Messages(newMessage);      //Creates Message instance
        inbox.put(numberOfMessages, input);             //Places Message instance in hashmap in order where the key is a counter so messages are kept in order received, this is also in constant time
        this.numberOfMessages++;                        //Increase counter for number of messages

    }
    private class Messages{
        private MSG_STATUS status = MSG_STATUS.UNREAD;
        private String message;

        public Messages(String inputMessage) {
            this.message = inputMessage;        //Initially all Messages are UNREAD, so only the String message is changed
        }
        public void messageRead(){
            this.status = MSG_STATUS.READ;      //Changes MSG_STATUS to READ
        }
        @Override
        public String toString(){               //Overrides toString method so when printing a Message instance, only the String message of the instance is printed
            return this.message;
        }
    }
}
