package pharmacy.ProductClasses;

import pharmacy.ExtraClasses.DateTime;

public abstract class AbstractMedication extends Product {

    //Attributes
    protected String purpose;
    protected String adultDose;
    protected String childDose;
    protected String activeIngredient;
    protected DateTime expiredDate;
    protected String manufacturer;

    //Constructor
    public AbstractMedication(String name, int code
            , double price, int quantity, String purpose
            , String adultDose, String childDose
            , String activeIngredient, DateTime expiredDate
            , String manufacturer) 
    {
        super(name, code, price, quantity);
        this.purpose = purpose;
        this.adultDose = adultDose;
        this.childDose = childDose;
        this.activeIngredient = activeIngredient;
        this.expiredDate = expiredDate;
        this.manufacturer = manufacturer;
    }

    //Mutators
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    public void setAdultDose(String adultDose) {
        this.adultDose = adultDose;
    }
    public void setChildDose(String childDose) {
        this.childDose = childDose;
    }
    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }
    public void setExpiredDate(DateTime expiredDate) {
        this.expiredDate = expiredDate;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    //Accessors
    public String getPurpose() {
        return purpose;
    }
    public String getAdultDose() {
        return adultDose;
    }
    public String getChildDose() {
        return childDose;
    }
    public String getActiveIngredient() {
        return activeIngredient;
    }
    public DateTime getExpiredDate() {
        return expiredDate;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    
}