public class OldLandline implements Phone{
    private String owner;
    private PhoneNumber number;
    private boolean inCall = false;                                 //Boolean value used to check if this instance of OldLandline  is currently in a call
    private Phone callWith = null;                                  //Phone value to keep track of who this OldLandline instance is on the phone with

    public OldLandline(String inputOwner, Long inputNumber) {
        this.owner = inputOwner;                                    //Sets this instance's owner to the given inputOwner String
        PhoneNumber newNumber = new PhoneNumber(inputNumber);       //Creates a PhoneNumber instance using the given inputNumber Long
        this.number = newNumber;                                    //Sets this instance's number to the PhoneNumber instance created earlier
    }
    public boolean getInCall(){                                     //Returns a boolean value telling whether of not this OldLandline is in a call
        return this.inCall;
    }
    public void setInCall(boolean inputInCall){                     //Sets a boolean value telling wether of not this OldLandline is in a call
        this.inCall = inputInCall;
    }
    public Phone getCallWith(){                                     //Getter for Phone this instance of OldLandline is in a call with
        return this.callWith;
    }
    public void setCallWith(Phone phone){                           //Setter for Phone this instance of OldLandline is in a call with
        this.callWith = phone;
    }
    public String getOwner(){                                       //Returns String object of who the owner of this instance is
        return this.owner;
    }
    public void call(Phone phone){
        if(this.inCall == true){
            if(this.callWith == phone)                              //If this instance is attempting to call someone they're already in a call with, prints appropriate message
                System.out.println(this.getOwner() + " is already in a call with " + phone.getOwner() + ".");
            else                                                    //If this instance is attempting to call someone else while in a call, prints appropriate message
                System.out.println(this.getOwner() + " is already in a call. Unable to call " + phone.getOwner() + ".");
            return;
        }
        if(this == phone){                                          //If this instance is attempting to call themself but is not in a call, prints appropriate message
            System.out.println(this.getOwner() + " is attempting to call themself, Unable to call.");
            return;
        }
        //Regardless of whether the phone being called is busy or not, it still receives the call from this phone so that the phone being called can handle it appropriately as some phones have voicemail functions
        if(phone.isBusy()){
            phone.receive(this);
        }
        else{                                   //If the other phone isn't busy, this phone is now in a call and the appropriate variables will be changed and an appropriate message is printed
            phone.receive(this);
            this.inCall = true;
            this.callWith = phone;
            System.out.println(this.getOwner() + " is on the phone with " + phone.getOwner() + ".");
        }
    }
    public void end(){
        if(inCall == false){                        //If a phone attempts to end a call but is not in one, an appropriate message is printed
            System.out.println(this.getOwner() + " is not on the phone with anyone.");
            return;
        }
        //If this phone is in a call with someone, prints appropriate message
        System.out.println(this.getOwner() + " has ended the call with " + this.callWith.getOwner());
        this.callWith.receiveEndSignal(this);   //The other phone receives an end signal as ending a phone call on one end also ends it for the other persono
        this.inCall = false;                        //Sets inCall to false as this phone is no longer in a call
        this.callWith = null;                       //Sets callWith to null as this phone is no longer in a call with anyone
    }
    public void receive(Phone from){
        if(this.isBusy()){                          //If this phone is busy while receiving a call, will print appropriate message
            System.out.println(from.getOwner() + " is unable to call " + this.getOwner() + ". Line is currently busy.");
        }
        else{
            this.inCall = true;                     //Sets inCall to true if this phone isn't busy when receiving a call
            this.callWith = from;                   //Sets callWith to the phone calling if this phone isn't busy when receiving a call
        }
    }
    public boolean isBusy(){                    //Returns whether or not this instance is busy in a call
        return this.inCall;
    }
    public void receiveEndSignal(Phone from){   //Will set inCall to false and callWith to null for this instance when phone call is ended
        this.inCall = false;
        this.callWith = null;
    }
    public PhoneNumber number() {               //Returns the PhoneNumber instance associated with this OldLandLine instance
        return this.number;
    }
}
