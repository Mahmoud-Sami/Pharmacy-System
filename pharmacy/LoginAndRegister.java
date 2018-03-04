package pharmacy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.ArrayList;
import pharmacy.PersonClasses.*;
import pharmacy.ExtraClasses.*;
import pharmacy.PharmacyDatabase.*;
import pharmacy.ProductClasses.*;

public class LoginAndRegister extends Application {
    static public boolean filled = false;
    public static void fillDB(){

        //Fill Users Data
        PeopleTable.addPerson(new Staff("Admin","admin","Ahmed","Mohamed",new DateTime(1,1,1,20,12,1995),"34 Elsalam St. Maadi","011543736483","Male"));
        PeopleTable.addPerson(new Customer("Customer","customer","Mahmoud","Sami",new DateTime(1,1,1,20,6,1997),"13 Margani St. Heliopolis","01274373734","Male"));

        //Fill Prescription Medication Data
        ProductTable.addProduct(new PrescriptionMedication("Avipect", 1435, 6.5, 3, "Productive Cough","3 Times","2 Times","Y" , new DateTime(1,1,1,12,5,2019) ,"Polpharma Group"));
        ProductTable.addProduct(new PrescriptionMedication("Humex", 1436, 1.5, 6, "Fever","3 Times","2 Times","Y" , new DateTime(1,1,1,12,5,2020) ,"Roche"));
        ProductTable.addProduct(new PrescriptionMedication("Panadol", 1437, 2.5, 12, "Migraine","1 Times","1 Times","Y" , new DateTime(1,1,1,12,5,2021) ,"Polpharma Group"));
        ProductTable.addProduct(new PrescriptionMedication("Catafast", 1438, 15, 15, "Migraine","2 Times","1 Times","Y" , new DateTime(1,1,1,12,5,2018) ,"Teva Group"));
        ProductTable.addProduct(new PrescriptionMedication("Aldomet", 1439, 23.5, 1, "Fever","3 Times","1 Times","Y" , new DateTime(1,1,1,12,5,2019) ,"Biofarm"));
        ProductTable.addProduct(new PrescriptionMedication("Eucaphol", 1440, 8.5, 4, "Cough Sedative","2 Times","1 Times","Y" , new DateTime(1,1,1,9,3,2020) ,"GSK Pharma"));


        ProductTable.addProduct(new Medication("Congestal", 1441, 8.5, 20, "Multi-Symptom","2 Times","1 Times","Y" , new DateTime(1,1,1,9,3,2022) ,"Polpharma Group"));
        ProductTable.addProduct(new Medication("Disflatyl", 1442, 9.5, 5, "Cold","2 Times","1 Times","Y" , new DateTime(1,1,1,9,5,2022) ,"UCB"));
        ProductTable.addProduct(new Medication("Librax", 1443, 8.5, 13, "Flu","2 Times","1 Times","Y" , new DateTime(1,1,1,9,3,2021) ,"Biofarm"));
        ProductTable.addProduct(new Medication("Kaptin", 1444, 7.5, 9, "Cold and Flu","3 Times","2 Times","Y" , new DateTime(1,1,1,16,3,2018) ,"Roche"));
        ProductTable.addProduct(new Medication("Comtrex", 1445, 3.5, 1, "Acute Head Cold","2 Times","1 Times","Y" , new DateTime(1,1,1,9,3,2020) ,"Teva Group"));
        ProductTable.addProduct(new Medication("Actifed", 1446, 17.5, 1, "Wet Cough","2 Times","1 Times","Y" , new DateTime(1,1,1,9,3,2020) ,"Teva Group"));


        ProductTable.addProduct(new Cosmetics("Tooth brush", 1447, 4.5, 4, "Health Care"));
        ProductTable.addProduct(new Cosmetics("Gel Haircode 50ml", 1448, 9.5, 2 , "Hair Care"));
        ProductTable.addProduct(new Cosmetics("Pantine Hair Cream 100ml", 1449, 10.5, 8, "Hair Care"));
        ProductTable.addProduct(new Cosmetics("Clear Shampoo 400ml", 1450, 17.5, 10 , "Hair Care"));
        ProductTable.addProduct(new Cosmetics("Signal Toothpaste", 1451, 2.75, 6, "Health Care"));
        ProductTable.addProduct(new Cosmetics("Nivea Protect & Care", 1452, 23, 16 , "Skin & Body Care"));


        //Fill Prescription List Data
        ArrayList <PrescriptionMedication> MedicationList;

        //Prescription with code 11111111 contains medications with code {1435 , 1436}
        MedicationList = new ArrayList<>();
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1435));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1436));
        PrescriptionTable.addPrescription(new Prescription(11111111, MedicationList));


        //Prescription with code 22222222 contains medications with code {1437 , 1438}
        MedicationList = new ArrayList<>();
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1437));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1438));
        PrescriptionTable.addPrescription(new Prescription(22222222, MedicationList));


        //Prescription with code 33333333 contains medications with code {1439 , 1440}
        MedicationList = new ArrayList<>();
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1437));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1438));
        PrescriptionTable.addPrescription(new Prescription(33333333, MedicationList));


        //Prescription with code 12345678 allows you to buy any or all medications
        MedicationList = new ArrayList<>();
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1435));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1436));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1437));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1438));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1439));
        MedicationList.add((PrescriptionMedication) ProductTable.getProduct(1440));
        PrescriptionTable.addPrescription(new Prescription(12345678, MedicationList));


        filled = true;

    }

    public static boolean userExist(String username){
        int user_n = PeopleTable.getPersons_Number();
        for (int i = 0; i < user_n; i++){
            if (PeopleTable.getPersonIndex(i).getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public static boolean CheckLogin(String username, String password){
        int user_n = PeopleTable.getPersons_Number();
        for (int i = 0; i < user_n; i++){
            if (userExist(username)){
                if (PeopleTable.getPersonIndex(i).getPassword().equals(password)){
                   return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (!filled) fillDB();
        Stage window;
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Pharmacy - Login");

        //Start Login

        Image logo = new Image (getClass().getResourceAsStream("icons/1.png"));
        ImageView img1 = new ImageView(logo);
        img1.setFitHeight(200);
        img1.setPreserveRatio(true);
        img1.setLayoutX(50);
        img1.setLayoutY(90);

        Image user = new Image (getClass().getResourceAsStream("icons/user.png"));
        ImageView userIcon = new ImageView(user);
        userIcon.setFitHeight(35);
        userIcon.setPreserveRatio(true);
        userIcon.setLayoutX(290);
        userIcon.setLayoutY(110);


        Image pass = new Image (getClass().getResourceAsStream("icons/pass.png"));
        ImageView passIcon = new ImageView(pass);
        passIcon.setFitHeight(35);
        passIcon.setPreserveRatio(true);
        passIcon.setLayoutX(290);
        passIcon.setLayoutY(175);


        TextField username = new TextField ();
        username.setPromptText("Enter your Username");
        username.setLayoutX(280);
        username.setLayoutY(100);
        username.setMinWidth(300);
        username.setId("TextField");


        final PasswordField password = new PasswordField ();
        password.setPromptText("Enter your Password");
        password.setLayoutX(280);
        password.setLayoutY(165);
        password.setMinWidth(300);


        Button register = new Button("Register");
        register.setLayoutX(280);
        register.setLayoutY(234);
        register.setMinWidth(144);
        register.setId("Register");


        Button login = new Button("Login");
        login.setLayoutX(435);
        login.setLayoutY(234);
        login.setMinWidth(144);
        login.setId("Login");

        //End Login



        //Start Register

        Image FN_Img = new Image (getClass().getResourceAsStream("icons/firstname.png"));
        ImageView FN_Icon = new ImageView(FN_Img);
        FN_Icon.setFitHeight(33);
        FN_Icon.setPreserveRatio(true);
        FN_Icon.setLayoutX(90);
        FN_Icon.setLayoutY(98);


        Image LN_Img = new Image (getClass().getResourceAsStream("icons/lastname.png"));
        ImageView LN_Icon = new ImageView(LN_Img);
        LN_Icon.setFitHeight(33);
        LN_Icon.setPreserveRatio(true);
        LN_Icon.setLayoutX(350);
        LN_Icon.setLayoutY(98);


        Image user2 = new Image (getClass().getResourceAsStream("icons/UserIcon.png"));
        ImageView userIcon2 = new ImageView(user2);
        userIcon2.setFitHeight(35);
        userIcon2.setPreserveRatio(true);
        userIcon2.setLayoutX(90);
        userIcon2.setLayoutY(170);


        Image pass2 = new Image (getClass().getResourceAsStream("icons/pass.png"));
        ImageView passIcon2 = new ImageView(pass2);
        passIcon2.setFitHeight(35);
        passIcon2.setPreserveRatio(true);
        passIcon2.setLayoutX(350);
        passIcon2.setLayoutY(170);

        Image phoneImg = new Image (getClass().getResourceAsStream("icons/phone.png"));
        ImageView phoneIcon = new ImageView(phoneImg);
        phoneIcon.setFitHeight(34);
        phoneIcon.setPreserveRatio(true);
        phoneIcon.setLayoutX(100);
        phoneIcon.setLayoutY(242);


        Image genderImg = new Image (getClass().getResourceAsStream("icons/gender.png"));
        ImageView genderIcon = new ImageView(genderImg);
        genderIcon.setFitHeight(30);
        genderIcon.setPreserveRatio(true);
        genderIcon.setLayoutX(355);
        genderIcon.setLayoutY(245);


        Image addressImg = new Image (getClass().getResourceAsStream("icons/address.png"));
        ImageView addressIcon = new ImageView(addressImg);
        addressIcon.setFitHeight(26);
        addressIcon.setPreserveRatio(true);
        addressIcon.setLayoutX(355);
        addressIcon.setLayoutY(315);




        TextField FN = new TextField ();
        FN.setPromptText("Enter your first name");
        FN.setLayoutX(80);
        FN.setLayoutY(88);
        FN.setMinWidth(240);


        TextField LN = new TextField ();
        LN.setPromptText("Enter your last name");
        LN.setLayoutX(340);
        LN.setLayoutY(88);
        LN.setMinWidth(240);



        TextField username2 = new TextField ();
        username2.setPromptText("Enter your Username");
        username2.setLayoutX(80);
        username2.setLayoutY(160);
        username2.setMinWidth(240);

        final PasswordField password2 = new PasswordField ();
        password2.setPromptText("Enter your Password");
        password2.setLayoutX(340);
        password2.setLayoutY(160);
        password2.setMinWidth(240);



        TextField phone = new TextField ();
        phone.setPromptText("Enter your Phone");
        phone.setLayoutX(80);
        phone.setLayoutY(230);
        phone.setMinWidth(240);



        ChoiceBox gender = new ChoiceBox(FXCollections.observableArrayList("Male","Female"));
        gender.getSelectionModel().selectFirst();
        gender.setId("gender");
        gender.setLayoutX(340);
        gender.setLayoutY(230);
        gender.setMinWidth(240);


        ChoiceBox day = new ChoiceBox(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"));
        day.getSelectionModel().selectFirst();
        day.setMaxWidth(70);
        day.setMinWidth(70);
        day.setLayoutX(80);
        day.setLayoutY(300);

        ChoiceBox month = new ChoiceBox(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12"));
        month.getSelectionModel().selectFirst();
        month.setMaxWidth(78);
        month.setMinWidth(78);
        month.setLayoutX(160);
        month.setLayoutY(300);

        ChoiceBox year = new ChoiceBox(FXCollections.observableArrayList("1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018"));
        year.getSelectionModel().selectFirst();
        year.setMaxWidth(70);
        year.setMinWidth(70);
        year.setLayoutX(248);
        year.setLayoutY(300);


        TextField address = new TextField ();
        address.setPromptText("Enter your address");
        address.setLayoutX(340);
        address.setLayoutY(300);
        address.setMinWidth(240);



        Button submit = new Button("Sing up");
        submit.setLayoutX(80);
        submit.setLayoutY(380);
        submit.setMinWidth(500);
        submit.setId("submit");


        Label backLogin = new Label("Back to Login");
        backLogin.setLayoutX(286);
        backLogin.setLayoutY(472);

        //End Register

        Pane pLogin = new Pane();
        pLogin.getChildren().setAll(img1, username, password , register, login , userIcon, passIcon);


        Pane pRegister = new Pane();
        pRegister.getChildren().setAll(FN , LN , username2, password2 , phone, gender , day , month , year , address , submit, FN_Icon, LN_Icon , userIcon2, passIcon2, phoneIcon, genderIcon, addressIcon , backLogin);


        Pane Login = new Pane();
        Login.getChildren().setAll(pLogin);


        Pane Register = new Pane();
        Register.getChildren().setAll(pRegister);



        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        username.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                pLogin.requestFocus();
                firstTime.setValue(false);
            }
        });

        final BooleanProperty firstTime2 = new SimpleBooleanProperty(true);
        FN.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime2.get()){
                pRegister.requestFocus();
                firstTime2.setValue(false);
            }
        });


        Scene LoginScene = new Scene(Login , 660 , 400);
        Scene RegisterScene = new Scene(Register, 660,550);
        LoginScene.getStylesheets().add("pharmacy/style.css");
        RegisterScene.getStylesheets().add("pharmacy/style.css");
        Font.loadFont(LoginAndRegister.class.getResource("Cairo.ttf").toExternalForm(),12);
        window.setScene(LoginScene);
        window.show();

        //Database

        //Events Start
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (CheckLogin(username.getText(),password.getText())){
                    Person currentUser = PeopleTable.getPerson(username.getText());
                    if (currentUser instanceof Customer) {
                        Main.GUI(currentUser);
                        window.close();
                    } else if (currentUser instanceof Staff) {
                        Main.GUI(currentUser);
                        window.close();
                    } else if (currentUser instanceof Delivery) {
                        Main.GUI(currentUser);
                        window.close();
                    }
                }
                else{
                    MsgBox.ShowMsg("OPS!" , "Username or password is not correct!" , MsgBox.type.error);
                }
            }
        });

        register.setOnMouseClicked(event -> {
            window.setScene(RegisterScene);
            window.setTitle("Pharmacy - Register");
        });


        backLogin.setOnMouseClicked(event -> {
            window.setScene(LoginScene);
            window.setTitle("Pharmacy - Login");
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (FN.getText().equals("")) {
                    MsgBox.ShowMsg("OPS!" , "First name field is empty !" , MsgBox.type.error);
                    return;
                }

                if (LN.getText().equals("")) {
                    MsgBox.ShowMsg("OPS!" , "Last name field is empty !" , MsgBox.type.error);
                    return;
                }

                if (username2.getText().equals("")) {
                    MsgBox.ShowMsg("OPS!" , "Username field is empty !" , MsgBox.type.error);
                    return;
                }

                if (password2.getText().equals("")) {
                    MsgBox.ShowMsg("OPS!" , "Password field is empty !" , MsgBox.type.error);
                    return;
                }

                if (phone.getText().equals("")) {
                    MsgBox.ShowMsg("OPS!" , "Phone field is empty !" , MsgBox.type.error);
                    return;
                }


                if (address.getText().equals("")){
                    MsgBox.ShowMsg("OPS!" , "Address field is empty !" , MsgBox.type.error);
                    return;
                }

                if (userExist(username2.getText())){
                    MsgBox.ShowMsg("OPS!" , "This username taken already, try another one !" , MsgBox.type.error);
                    return;
                }
                Person temp = new Customer(username2.getText(),password2.getText(),FN.getText(),LN.getText()
                        , new DateTime(1,1,1,Integer.parseInt(day.getValue().toString()),Integer.parseInt(month.getValue().toString()),Integer.parseInt(year.getValue().toString()))
                        , address.getText(),phone.getText(), gender.getValue().toString()  );
                PeopleTable.addPerson(temp);
                MsgBox.ShowMsg("DONE" , "You have registered successfully !" , MsgBox.type.success);
                window.setScene(LoginScene);
                window.setTitle("Pharmacy - Login");

            }
        });

        //Events End


    }

}