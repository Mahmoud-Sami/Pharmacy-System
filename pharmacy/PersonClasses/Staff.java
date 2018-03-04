package pharmacy.PersonClasses;

import pharmacy.ExtraClasses.DateTime;

public class Staff extends Person {

    //Attributes
    private int confirmedOrders;

    //Default Constructor
    public Staff() {
        this.confirmedOrders = 0;
    }

    //Constructor
    public Staff(String username, String password, String Fname, String Lname, DateTime dt, String address, String phone, String gender) {
        super(username, password, Fname, Lname, dt, address, phone, gender);
        confirmedOrders = 0;
    }

    //Mutator
    public void setConfirmedOrders(int confirmedOrders) {
        this.confirmedOrders = confirmedOrders;
    }

    //Accessor
    public int getConfirmedOrders() {
        return confirmedOrders;
    }

    //Other Methods
    public void confirmNewOrder(){
        this.confirmedOrders++;
    }

}
