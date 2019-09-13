public class PhoneNumber {
    private long number;

    public PhoneNumber(Long inputNumber) throws IllegalArgumentException{
        String check = inputNumber.toString();              //Converts Long into a String object
        try {
            if(check.length() != 10){                       //Since this only takes Long argument, only checks if it is 10 digits long
                throw new IllegalArgumentException();       //If Long value isn't 10 digits long, throws an IllegalArgumentException
            }
        }catch(IllegalArgumentException e){
            System.out.println("ILLEGAL ARGUMENT: This is Not A Valid Phone Number");
            return;
            }
        this.number = inputNumber;                          //Sets the number of this Phone Number instance to the value in specified
    }
    public long getNumber(){
        return this.number;                                 //Getter method for the phone number
    }
}
