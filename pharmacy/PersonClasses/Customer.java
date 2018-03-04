package pharmacy.PersonClasses;
import pharmacy.ExtraClasses.DateTime;
public class Customer extends Person {

    //Constructor
    public Customer(String username, String password, String Fname, String Lname, DateTime dt, String address, String phone, String gender) {
        super(username, password, Fname, Lname, dt, address, phone,gender);
    }

}