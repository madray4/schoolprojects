import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Test {
    public static void main(String[] args){

        Orderings.PhoneNumberComparator phoneNumberComparator = new Orderings.PhoneNumberComparator();
        Orderings.PhoneNameComparator phoneNameComparator= new Orderings.PhoneNameComparator();
        Orderings.ComputerScreenSizeComparator computerScreenSizeComparator = new Orderings.ComputerScreenSizeComparator();
        Orderings.ComputerBrandComparator computerBrandComparator = new Orderings.ComputerBrandComparator();
        Orderings.ComputerRamComparator computerRamComparator = new Orderings.ComputerRamComparator();


//~~~~~~~~~~~~~~~~~TEST CASE: Different Real and Apparent Types - Call and End Methods~~~~~~~~~~~~~~~~~
        //The call and end methods are all part of the Phone interface and therefore no casting is required to compile properly

        System.out.println("\n" + "~~~~~~~~~~~~~~~~~TEST CASE: Different Real and Apparent Types - Call() and End()~~~~~~~~~~~~~~~~~" + "\n");
        OldLandline Michael = new Landline("MICHAEL", 7182913281L);
        Phone Raphael = new OldLandline("RAPHAEL", 3172812831L);
        Phone Gabriel = new Landline("GABRIEL",9172884932L);
        Phone Uriel = new SmartPhone("URIEL",9171234567L,"ON",5,20,50);

        OldLandline illegalArgsTest = new OldLandline("IAT", 7182L); //Will throw illegal args as the phone number is the incorrect amount of digits and print meaningful message
        SmartPhone illegalArgsTestSP = new SmartPhone("Smartphone", 1234567890L, "oN",5,20,50); //Will throw illegal args at input state is invalid

        ArrayList<Phone> phone1 = new ArrayList<Phone>();
        phone1.add(Michael);
        phone1.add(Raphael);
        phone1.add(Gabriel);
        phone1.add(Uriel);


        Raphael.call(Michael);
        Gabriel.call(Michael);  //Michael's actual type is a Landline so when methods are called on it, it will use methods associated to Landline rather than its apparent type of OldLandline
        Raphael.end();

        Michael.call(Raphael);
        Gabriel.call(Raphael);  //Raphaels actual type is an OldLandline, therefore it does not have voice mail functions
        Michael.end();

        Uriel.call(Raphael);
        Gabriel.call(Uriel);    //Ureils actual type is a Smartphone,it will use methods associated to SmartPhone rather than its apparent type of Phone
        Uriel.end();


//~~~~~~~~~~~~~~~~~TEST CASE: Different Real and Apparent Types - playGame, and Various Methods~~~~~~~~~~~~~~~~~
        //At compile time, the IDE checks the apparent type. So in order for Uriel (Apparent Type: Phone, Actual Type SmartPhone) to use methods from SmartPhone, it must
        //be cast to a SmartPhone.
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~TEST CASE: Different Real and Apparent Types - playGame and Various Methods~~~~~~~~~~~~~~~~~");
        ((SmartPhone) Uriel).installGame("Minecraft");
        ((SmartPhone) Uriel).installGame("League of Legends");
        ((SmartPhone) Uriel).playGame("Minecraft");


//~~~~~~~~~~~~~~~~~TEST CASES: VARIOUS FUNCTIONS~~~~~~~~~~~~~~~~~

        System.out.println("~~~~~~~~~~~~~~~~~TEST CASE: Various Functions~~~~~~~~~~~~~~~~~");
        OldLandline Brian = new OldLandline("BRIAN", 9999999999L);
        OldLandline Kyle = new OldLandline("KYLE", 3172832918L);
        Landline Michelle = new Landline("MICHELLE", 1234567890L);
        Landline Connor = new Landline("CONNOR", 1234567890L);
        SmartPhone Henry = new SmartPhone("HENRY", 7182885875L, "OFF", 5, 10, 50 );
        SmartPhone Nightfury = new SmartPhone("NIGHTFURY", 7182885875L, "ON", 4, 15, 45 );
        Laptop Soomin = new Laptop("SOOMIN", "OFF", 5, 8, 50, "DELL");
        Laptop Batman = new Laptop("BATMAN", "ON", 10, 20, 300, "XPS");

        //Call() and End() test cases
        Brian.call(Kyle);
        Michelle.call(Brian);   //Will output that Brian is busy and in another phone call and does not leave a message as an Oldlandline cannot record messages
        Brian.end();            //Will output that Brian has ended the call with Kyle
        Brian.call(Brian);      //Will output that Brian cannot call himself
        Brian.end();            //Will output that Brian is currently not in a call, so there is no call to end

        Henry.call(Michelle);
        Brian.call(Michelle);   //Will ask Connor if they wish to leave a message even though Brian is an instance of OldLandline. This is because Michelle is an instance of Landline, which has a voicemail feature
        Brian.call(Michelle);

        //Messages test cases
        Michelle.end();
        Michelle.readMessages(Landline.MSG_STATUS.UNREAD);      //Unread parameter will cause all unread messages for Michelle to print in order
        Michelle.readMessages(Landline.MSG_STATUS.READ);        //Read parameter will cause all read messages for Michelle to print in order
        Michelle.readMessages();                                //Empty parameter will cause all messaged for Michelle to print in order
        Michelle.call(Brian);
        Michelle.readMessages();                                //Will output that messages are unable to be read when in a call
        Michelle.end();
        Michelle.readMessages();                                //Will now output all messaged as Michelle is no longer in a call

//        SmartPhone games test cases
        System.out.println("\n" + "~~~~~~~~~~~~~~~~~TEST CASE: SmartPhone Games~~~~~~~~~~~~~~~~~" + "\n");
        Henry.installGame("Minecraft");     //Will output that installation is not possible as phone is currently off
        Henry.setState("ON");
        Henry.installGame("Minecraft");     //Will output that game is installing
        Henry.installGame("Minecraft");     //Will output nothing as minecraft is already installed
        Henry.installGame("Fortnite");
        Henry.installGame("League of Legends");
        Henry.installGame("Rust");
        Henry.installGame("COD");
        Henry.installGame("Candy Crush");   //Will output that the max amount of games have been installed and Candy Crush can't be installed
        Henry.playGame("Rust");             //Will output that the game is being played
        Henry.playGame("Mortal Kombat");    //Will output that this game is not yet installed, so cannot be played
        Henry.setState("off");
        Henry.playGame("Rust");             //Will output that the game can't be played because the device is off

        //Laptop games test cases. Is not an instance of Phone, so laptops cannot use phone features such as calling
        System.out.println("\n" + "~~~~~~~~~~~~~~~~~TEST CASE: Different Real and Apparent Types~~~~~~~~~~~~~~~~~" + "\n");
        Batman.installGame("Game 1");
        Batman.installGame("Game 2");
        Batman.installGame("Game 3");
        Batman.installGame("Game 4");
        Batman.installGame("Game 5");
        Batman.installGame("Game 6");   //Will output that this game is unable to be installed as max number of games are already installed
        Batman.installGame("Game 1");   //Will output nothing as Game 1 is already installed
        Batman.playGame("Game 6");      //Will output that Game 6 is not installed so game cannot be played
        Batman.playGame("Game 1");      //Will output that Game 1 is being played

        //Sorting using comparators
        ArrayList<Phone> phones = new ArrayList<Phone>();
        phones.add(Brian);
        phones.add(Kyle);
        phones.add(Michelle);
        phones.add(Connor);
        phones.add(Henry);
        phones.add(Nightfury);

        ArrayList<Computer> computers = new ArrayList<Computer>();
        computers.add(Henry);
        computers.add(Nightfury);
        computers.add(Soomin);
        computers.add(Batman);


        //Sorts phones by numbers in increasing order
        Collections.sort(phones, phoneNumberComparator);
        System.out.println("\n" + "~~~~PRINTING PHONE NUMBERS IN INCREASING ORDER.~~~~~" + "\n");
        //Prints list sorted using phoneNumberComparator
        for(int i =0; i< phones.size(); i++){
            System.out.println(phones.get(i).getOwner() + ": " + phones.get(i).number().getNumber());
        }

        //Sorts phones by names of owner in alphabetical order, case-insensitive
        Collections.sort(phones, phoneNameComparator);
        System.out.println("\n" + "~~~~PRINTING PHONE OWNERS IN ALPHABETICAL ORDER.~~~~~" + "\n");
        //Prints list sorted by phoneNameComparator
        for(int i =0; i< phones.size(); i++){
            System.out.println(phones.get(i).getOwner() + ": " + phones.get(i).number().getNumber());
        }

        //Sorts computers by screen size in increasing order
        Collections.sort(computers, computerScreenSizeComparator);
        System.out.println("\n" + "~~~~PRINTING COMPUTER SCREEn SIZE IN INCREASING ORDER.~~~~~" + "\n");
        //Prints list sorted by using computerScreenSizeComparator
        for(int i = 0; i<computers.size(); i++){
            Computer computer = computers.get(i);
            if(computer.getClass() == SmartPhone.class){
                SmartPhone smartphone = (SmartPhone)computer;
                System.out.println(smartphone.getOwner() + "'s smart phone - Screen Size: " + smartphone.getScreenSize() + ".");
            }
            else{
                Laptop laptop = (Laptop)computer;
                System.out.println(laptop.getOwner() + "'s laptop - Screen Size: " + laptop.getScreenSize() + ".");
            }
        }

        //Sorts computers by brand name, if any smartphones exist they will be first in the list and "NO BRAND" will be clearly printed
        Collections.sort(computers, computerBrandComparator);
        System.out.println("\n" + "~~~~PRINTING COMPUTER BRAND NAME IN ALPHABETICAL ORDER.~~~~~" + "\n");
        //Prints list sorted by using computerBrandComparator
            for(int i = 0; i<computers.size(); i++){
                Computer computer = computers.get(i);
                if(computer.getClass() == Laptop.class){
                    Laptop laptop = (Laptop)computer;
                    System.out.println(laptop.getBrand() + " laptop owned by " + laptop.getOwner());
                }
                else{
                    SmartPhone smartphone = (SmartPhone)computer;
                    System.out.println("NO BRAND: " + smartphone.getOwner() + "'s smart phone.");
                }
            }

        //Sorts computers by RAM in increasing order
        Collections.sort(computers, computerRamComparator);
        System.out.println("\n" + "~~~~PRINTING COMPUTER RAM IN INCREASING ORDER.~~~~~" + "\n");
        //Prints list sorted by using computerRamComparator
            for(int i = 0; i<computers.size(); i++){
                Computer computer = computers.get(i);
                if(computer.getClass() == Laptop.class){
                    Laptop laptop = (Laptop)computer;
                    System.out.println(laptop.getOwner() + "'s laptop - RAM: " + laptop.getRAM());
                }
                else{
                    SmartPhone smartphone = (SmartPhone)computer;
                    System.out.println(smartphone.getOwner() + "'s smart phone - RAM: " + smartphone.getRAM());
                }
            }
    }
}
