import java.util.Comparator;

public class Orderings{

        static class PhoneNumberComparator implements Comparator<Phone>{        //Orders a list of Phones by the phone numbers in increasing order
            @Override
            public int compare(Phone p1, Phone p2){                             //Overrides compare method
                if (p1.number().getNumber() > p2.number().getNumber())          //If first phone number is greater than second, it will be sorted higher
                    return 1;
                else if (p1.number().getNumber() < p2.number().getNumber())     //If second phone number is less than second, it will be sorted lower
                    return -1;
                else                                                            //If they're equal, nothing happens to their ordering
                    return 0;
            }
        }
        static class PhoneNameComparator implements Comparator<Phone> {     //Orders a list of Phones by the owners names in alphabetical order
            @Override
            public int compare(Phone p1, Phone p2) {
                return p1.getOwner().compareToIgnoreCase(p2.getOwner());    //Since names can be ordered in natural order, a compareTo method is sufficient
            }
        }
        static class ComputerScreenSizeComparator implements Comparator<Computer>{  //Orders a list of Computers by the screen size in increasing order
            @Override
            public int compare(Computer c1, Computer c2){                           //Both SmartPhones and Laptops have screen sizes so there is no need to differentiate between the two
                if (c1.getScreenSize() > c2.getScreenSize())                        //If first screen size is greater than second, it will be sorted higher
                    return 1;
                else if (c1.getScreenSize() < c2.getScreenSize())                   //If first screen size is less than second, it will be sorted lower
                    return -1;
                else                                                                //If they're equal, nothing happens to their ordering
                    return 0;
            }
        }
        static class ComputerBrandComparator implements Comparator<Computer>{   //Orders a list of Computers by the brand in alphabetical order
            @Override
            public int compare(Computer c1, Computer c2){
                if(c1.getClass() == SmartPhone.class)                           //If the fist Computer is a SmartPhone, it will automatically be sorted lower
                    return -1;
                else if(c2.getClass() == SmartPhone.class)                      //If the second Computer is a SmartPhone, the first will automatically be sorted higher
                    return 1;
                else {                                                          //Reaches if both Computers are Laptops
                    Laptop l1 = (Laptop) c1;                                    //Casts c1 to a Laptop instance
                    Laptop l2 = (Laptop) c2;                                    //Casts c2 to a Laptop instace
                    return l1.getBrand().compareToIgnoreCase(l2.getBrand());    ////Since brands can be ordered in natural order, a compareTo method is sufficient
                }
            }
        }
        static class ComputerRamComparator implements Comparator<Computer>{ //Orders a list of Computers by the RAM in increasing order
            @Override
            public int compare(Computer c1, Computer c2){                   //Both SmartPhones and Laptops have RAM so there is no need to differentiate between the two
                if(c1.getRAM() > c2.getRAM())                               //If first RAM is greater than second, it will be sorted higher
                    return 1;
                else if (c1.getRAM() < c2.getRAM())                         //If first RAM is less than second, it will be sorted lower
                    return -1;
                else                                                        //If they're equal, nothing happens to their ordering
                    return 0;
            }
        }
}
