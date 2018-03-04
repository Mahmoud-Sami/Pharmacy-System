package pharmacy;

import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.ProductClasses.*;
import pharmacy.PersonClasses.*;
import pharmacy.ExtraClasses.*;

public abstract class PharmacyDatabase {
    
    public static abstract class OrderTable {
        private static ObservableList<Order> orderTable = FXCollections.observableArrayList();

        //Methods
        public static boolean addOrder(Order o) {
            try{
                orderTable.add(o);
                return true;
            }catch(Exception e){
                return false;
            }
        }

        public static boolean deleteOrder(int orderID) {
            for (int i = 0; i < orderTable.size(); i++) {
                if (orderTable.get(i).getInvoiceId() == orderID) {
                    orderTable.remove(i);
                    return true;
                }
            }
            return false;
        }
        public static Order getOrder(int orderID) {
            for (int i = 0; i < orderTable.size(); i++) {
                if (orderTable.get(i).getInvoiceId() == orderID) {
                    return orderTable.get(i);
                }
            }
            return null;
        }
        public static Order getOrderIndex(int index){
            try{
                return orderTable.get(index);
            }catch(Exception e){
                return null;
            }
        }

        public static int getOrders_Number(){
            return orderTable.size();
        }
        public static boolean updateOrder_Customer(int orderID, Customer c){
            for (int i = 0; i < orderTable.size(); i++) {
                if (orderTable.get(i).getInvoiceId() == orderID) {
                    orderTable.get(i).setInvoiceCustomer(c);
                    return true;
                }
            }
            return false;
        }
        public static boolean updateOrder_AddNewProduct(int orderID, Product p){
            for (int i = 0; i < orderTable.size(); i++) {
                if (orderTable.get(i).getInvoiceId() == orderID) {
                    orderTable.get(i).addProduct(p);
                    return true;
                }
            }
            return false;
        }
    }
    public static abstract class PeopleTable {
        private static ArrayList < Person > peopleTable = new ArrayList < Person > ();
        //Methods
        public static boolean addPerson(Person p) {
            try{
                peopleTable.add(p);
                return true;
            }catch(Exception e){
                return false;
            } 
        }
        public static boolean deletePerson(int personID) {
            for (int i = 0; i < peopleTable.size(); i++) {
                if (peopleTable.get(i).getID() == personID) {
                    peopleTable.remove(i);
                    return true;
                }
            }
            return false;
        }
        public static Person getPerson(int personID){
            for (int i = 0; i < peopleTable.size(); i++) {
                if (peopleTable.get(i).getID() == personID) {
                    return peopleTable.get(i);
                }
            }
            return null;
        }
        public static Person getPerson(String username){
            for (int i = 0; i < peopleTable.size(); i++) {
                if (peopleTable.get(i).getUsername().equalsIgnoreCase(username)) {
                    return peopleTable.get(i);
                }
            }
            return null;
        }
        public static Person getPersonIndex(int index){
            try{
                return peopleTable.get(index);
            }catch(Exception e){
                return null;
            }
        }
        public static int getPersons_Number(){
            return peopleTable.size();
        }
        public static boolean updatePerson_firstName(int personID, String newName){
            for (int i = 0; i < peopleTable.size(); i++){
                if (peopleTable.get(i).getID() == personID){
                    peopleTable.get(i).setFirstName(newName);
                    return true;
                }
            }
            return false;
        }
        public static boolean updatePerson_lastName(int personID, String newName){
            for (int i = 0; i < peopleTable.size(); i++){
                if (peopleTable.get(i).getID() == personID){
                    peopleTable.get(i).setLastName(newName);
                    return true;
                }
            }
            return false; 
        }
        public static boolean updatePerson_Phone(int personID, String number){
            for (int i = 0; i < peopleTable.size(); i++){
                if (peopleTable.get(i).getID() == personID){
                    peopleTable.get(i).setPhone(number);
                    return true;
                }
            }
            return false;
        }
        public static boolean updatePerson_Adress(int personID, String ad){
            for (int i = 0; i < peopleTable.size(); i++){
                if (peopleTable.get(i).getID() == personID){
                    peopleTable.get(i).setAddress(ad);
                    return true;
                }
            }
            return false;
        }
        public static boolean updatePerson_DateOfBirth(int personID, DateTime dt){
            for (int i = 0; i < peopleTable.size(); i++){
                if (peopleTable.get(i).getID() == personID){
                    peopleTable.get(i).setDateOfBirth(dt);
                    return true;
                }
            }
            return false;
        }
    }
    public static abstract class ProductTable{
        private static ArrayList < Product > productTable = new ArrayList < Product > ();
        public static void Print(){
            for (int i = 0; i < productTable.size(); i++){
                System.out.println(productTable.get(i).toString());
            }
        }
        public static boolean addProduct(Product p){
            try{
                productTable.add(p);
                return true;
            }
            catch(Exception e){
                return false;
            }
        }
        public static boolean deleteProduct(int productCode){    
            for (int i = 0; i < productTable.size(); i++){
                if (productTable.get(i).getCode() == productCode){
                    productTable.remove(i);
                    return true;
                }
            }
            return false;
        }
        public static Product getProduct(int productCode){
            for (int i = 0; i < productTable.size(); i++){
                if (productTable.get(i).getCode() == productCode){
                    return productTable.get(i);
                }
            }
            return null; 
        }
        public static Product getProductIndex(int index){
            try{
              return productTable.get(index);
            }
            catch(Exception e){
              return null;
            }
        }
        public static int getProducts_Number(){
            return productTable.size();
        }
        public static Product getProductOfIndex(int index){
            try{
                return productTable.get(index);
            }
            catch(Exception e){
                return null;
            }
        }
        public static boolean updateProduct_name(int productCode, String name){
            for (int i = 0; i < productTable.size(); i++){
                if (productTable.get(i).getCode() == productCode){
                    productTable.get(i).setName(name);
                    return true;
                }
            }
            return false;
        }
        public static boolean updateProduct_price(int productCode, double price){
            if (price < 0) throw new ArithmeticException("Invalid Second input");
            for (int i = 0; i < productTable.size(); i++){
                if (productTable.get(i).getCode() == productCode){
                    productTable.get(i).setPrice(price);
                    return true;
                }
            }
            return false;
        }
        public static boolean updateProduct_quantity(int productCode, int q){
            if (q < 0) throw new ArithmeticException("Invalid Second input");
            for (int i = 0; i < productTable.size(); i++){
                if (productTable.get(i).getCode() == productCode){
                    productTable.get(i).setQuantity(q);
                    return true;
                }
            }
            return false;
        }
    }
    public static abstract class PrescriptionTable {
        private static ArrayList<Prescription> PrescriptionListData = new ArrayList<Prescription>();

        public static boolean addPrescription(Prescription newPrescription) {
            try {
                PrescriptionListData.add(newPrescription);
                return true;
            } catch (Exception e) {
                return false;
            }
        }



        public static boolean isValidPrescription(String PrescriptionCode , PrescriptionMedication SelectedMedicatin) {

            for (Prescription current : PrescriptionListData) {
                if (PrescriptionCode.equals(Integer.toString(current.getCode()))) {
                    if (current.isContainMedication(SelectedMedicatin.getCode())) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}