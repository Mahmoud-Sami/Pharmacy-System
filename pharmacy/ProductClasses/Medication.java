package pharmacy.ProductClasses;

import pharmacy.ExtraClasses.DateTime;

public class Medication extends AbstractMedication {

    //Constructor
    public Medication(String name, int code, double price
            , int quantity, String purpose, String adultDose, 
            String childDose, String activeIngredient, 
            DateTime expiredDate, String manufacturer) {
        super(name, code, price, quantity, purpose
                ,adultDose ,childDose, activeIngredient
                ,expiredDate ,manufacturer);
    }
    
}