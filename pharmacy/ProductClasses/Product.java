package pharmacy.ProductClasses;

//Abstract Class 
public abstract class Product {

    //Attributes
    protected String name;
    protected int code;
    protected double price;
    protected int quantity;

    //Constructor
    public Product(String name, int code, double price, int quantity) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.quantity = quantity;
    }

    //Mutators
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //Accessors
    public String getName() {
        return name;
    }
    public int getCode() {
        return code;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

   //Other Methods
    public double  getTotalPrice(){
        return quantity*price;
    }
    @Override
    public String toString(){
        return this.code + ", " + this.name + ", " + this.price + ", " + this.quantity;
    }
    @Override
    public boolean equals(Object p){
        if (!(p instanceof Product)) return false;
        if (((Product)p).getCode() == this.getCode())
            return true;
        else 
            return false;
    }
}