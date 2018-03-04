package pharmacy;

import java.util.ArrayList;
import pharmacy.PersonClasses.Customer;
import pharmacy.ExtraClasses.DateTime;
import pharmacy.ProductClasses.Product;

public class Order {

    //--------(Attributes)----------//
    private static int counterID = 0;
    private final int invoiceId;
    private double invoicePrice;
    private Customer invoiceCustomer;
    private String invoiceDate;
    private String invoiceTime;
    private String status;
    private ArrayList<Product> productList;

    //---------(Methods)-------------//
    private int generateId(){
        counterID++;
        return counterID;
    }

    //Constructor Methods
    public Order(double totalDue , Customer c , ArrayList <Product> productlist){
        invoiceId = generateId();
        invoicePrice = totalDue;
        invoiceCustomer = c;
        invoiceDate = new DateTime().getDate();
        invoiceTime = new DateTime().getTime();
        status = "Pending";
        productList = new ArrayList<Product>();
        this.productList = productlist;
    }

    //Mutator Methods
    public void setInvoiceCustomer(Customer c){ 
        invoiceCustomer = c; 
    }
    public void setInvoiceDate(String date){
        invoiceDate = date;
    }
    public void setInvoiceTime(String time){
        invoiceTime = time;
    }
    public void setStatus (String st) { status = st; }
    public void addProduct(Product p){
        productList.add(p);
        invoicePrice += p.getTotalPrice();
    }
    public void deleteProduct(int code){
        for (int i = 0; i < productList.size(); i++){
            if (productList.get(i).getCode() == code){
                productList.remove(i);
            }
        }
    }

    //Accessor Methods
    public int getInvoiceId() { return invoiceId; }
    public double getInvoicePrice() { return invoicePrice; }
    public Customer getInvoiceCustomer() { return invoiceCustomer; }
    public String getInvoiceDate() { return invoiceDate; }
    public String getInvoiceTime() { return invoiceTime; }
    public String getStatus() { return status; }
    public Product productIndex(int index){ 
        try{
            return productList.get(index); 
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.print(e.toString());
            return null;
        }
    }

    public ArrayList<Product> getProductList() { return productList; }

    @Override
    public String toString(){
        return this.invoiceId + ", " + this.invoiceCustomer.getFirstName() + ", " + this.invoicePrice + ", " + this.productList.size();
    }

    @Override
    public boolean equals(Object p){
        if (!(p instanceof Order)) return false;
        if (((Order)p).getInvoiceId()== this.getInvoiceId())
            return true;
        else 
            return false;
    }
}
