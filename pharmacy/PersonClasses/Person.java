package pharmacy.PersonClasses;

import pharmacy.ExtraClasses.DateTime;

public abstract class Person {

    //Attributes
    private static int counterID = 0;
    protected int ID;
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String personAddress;
    protected String personPhone;
    protected String gender;
    protected DateTime dateOfBirth;

    //Method for generating new ID for new user
    private int generateID(){
        counterID++;
        return counterID;
    }

    //Default Constructor
    public Person() {
        ID = generateID();
        username = "";
        password = "";
        firstName = "";
        lastName = "";
        personAddress = "";
        gender = "";
        personPhone = "";
        dateOfBirth = new DateTime();
    }

    //Constructor
    public Person(String username, String password, String Fname, String Lname, DateTime dt, String address, String phone, String gender) {
        ID = generateID();
        this.username = username;
        this.password = password;
        this.firstName = Fname;
        this.lastName = Lname;
        this.personAddress = address;
        this.personPhone = phone;
        this.gender = gender;
        dateOfBirth = dt;
    }

    //Mutator
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setFirstName(String Fname) {
        this.firstName = Fname;
    }
    public void setLastName(String Lname) {
        this.lastName = Lname;
    }
    public void setAddress(String address) {
        this.personAddress = address;
    }
    public void setPhone(String phone) {
        this.personPhone = phone;
    }
    public void setDateOfBirth(int Day, int Month, int Year) {
        dateOfBirth.setDate(Day, Month, Year);
    }
    public void setDateOfBirth(DateTime dt) {
        dateOfBirth = dt;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    //Accessor
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getID() {
        return ID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() {
        return personAddress;
    }
    public String getPhone() {
        return personPhone;
    }
    public DateTime getDateOfBirth(){
        return dateOfBirth;
    }
    public String getGender() {
        return gender;
    }

    @Override
    public String toString(){
        return this.ID + ", " + this.username + ", " + this.firstName + ", " + this.gender;
    }
    @Override
    public boolean equals(Object p){
        if (!(p instanceof Person)) return false;
        if (((Person)p).getID() == this.getID())
            return true;
        else 
            return false;
    }

 }