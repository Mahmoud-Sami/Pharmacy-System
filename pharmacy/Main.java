package pharmacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import pharmacy.PersonClasses.Customer;
import pharmacy.PersonClasses.Person;
import pharmacy.ProductClasses.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Main {
     //Edited
    public static ObservableList<Product> pCart_tvOrder_data = FXCollections.observableArrayList(); //Edited List My Cart
    public static ObservableList<Order> CustomerOrders = FXCollections.observableArrayList();

    enum ProductType {
        Cosmetics, Medication, PM, All
    };

    public static void ChangeDB(ObservableList<Product> Cart) {
        for (int j = 0; j < Cart.size(); j++) {
            int qty = PharmacyDatabase.ProductTable.getProduct(Cart.get(j).getCode()).getQuantity();
            PharmacyDatabase.ProductTable.updateProduct_quantity(Cart.get(j).getCode(), qty-Cart.get(j).getQuantity());
        }
    }

    public static double getTotalDue() {
        double total = 0.0;
        for (Product item : pCart_tvOrder_data) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public static void FillTableViewByProduct(ObservableList<Product> data, ProductType type){
        for (int i = 0; i < PharmacyDatabase.ProductTable.getProducts_Number(); i++){
            Product temp = PharmacyDatabase.ProductTable.getProductOfIndex(i);
            if (temp.getQuantity() == 0)
                continue;
            if (type == type.All) data.add(temp);
            else if (type == type.PM){
                if (temp instanceof PrescriptionMedication){
                    data.add(temp);
                }
            }
            else if (type == type.Medication){
                if (temp instanceof Medication){
                    data.add(temp);
                }
            }
            else if (type == type.Cosmetics){
                if (temp instanceof Cosmetics){
                    data.add(temp);
                }
            }

        }
    }


    public static boolean AddToCart(ObservableList<Product> data,Product p) {
        boolean flag = true;
        int Index = 0;
        for (Product current : pCart_tvOrder_data) {
            if (current.getName().equalsIgnoreCase(p.getName())) {
                if (pCart_tvOrder_data.get(Index).getQuantity() > 1 && p.getQuantity() - pCart_tvOrder_data.get(Index).getQuantity() < 8 && (p instanceof  PrescriptionMedication || p instanceof  Medication)) {
                    String msg = "You can't order more than " + pCart_tvOrder_data.get(Index).getQuantity() + " of this product";
                    MsgBox.ShowMsg("Error", msg , MsgBox.type.error);
                    return false;
                } else {
                    if (p instanceof PrescriptionMedication)
                        pCart_tvOrder_data.set(Index, new PrescriptionMedication(p.getName(), p.getCode(), p.getPrice(), current.getQuantity() + 1, ((PrescriptionMedication)p).getPurpose(), ((PrescriptionMedication)p).getAdultDose(), ((PrescriptionMedication)p).getChildDose(), ((PrescriptionMedication)p).getActiveIngredient(), ((PrescriptionMedication)p).getExpiredDate(), ((PrescriptionMedication)p).getManufacturer()));
                    else if (p instanceof Medication)
                        pCart_tvOrder_data.set(Index, new Medication(p.getName(), p.getCode(), p.getPrice(), current.getQuantity() + 1, ((Medication)p).getPurpose(), ((Medication)p).getAdultDose(), ((Medication)p).getChildDose(), ((Medication)p).getActiveIngredient(), ((Medication)p).getExpiredDate(), ((Medication)p).getManufacturer()));
                    else if (p instanceof Cosmetics)
                        pCart_tvOrder_data.set(Index, new Cosmetics(p.getName(), p.getCode(), p.getPrice(), current.getQuantity() + 1, ((Cosmetics) p).getCategory()));
                    if (p.getQuantity() - pCart_tvOrder_data.get(Index).getQuantity() == 0) {
                        data.remove(p);
                    }
                    return true;
                }
            }
            Index++;
        }
        if (p instanceof PrescriptionMedication)
            pCart_tvOrder_data.add(new PrescriptionMedication(p.getName(), p.getCode(), p.getPrice(), 1, ((PrescriptionMedication)p).getPurpose(), ((PrescriptionMedication)p).getAdultDose(), ((PrescriptionMedication)p).getChildDose(), ((PrescriptionMedication)p).getActiveIngredient(), ((PrescriptionMedication)p).getExpiredDate(), ((PrescriptionMedication)p).getManufacturer()));
        else if (p instanceof Medication)
             pCart_tvOrder_data.add(new Medication(p.getName(), p.getCode(), p.getPrice(), 1, ((Medication)p).getPurpose(), ((Medication)p).getAdultDose(), ((Medication)p).getChildDose(), ((Medication)p).getActiveIngredient(), ((Medication)p).getExpiredDate(), ((Medication)p).getManufacturer()));
        else if (p instanceof Cosmetics)
            pCart_tvOrder_data.add(new Cosmetics(p.getName(), p.getCode(), p.getPrice(), 1, ((Cosmetics) p).getCategory()));
        if (p.getQuantity() - pCart_tvOrder_data.get(Index).getQuantity() == 0) {
            data.remove(p);
        }
        return true;
    }

    public static void GUI(Person currentuser) {
        Stage window = new Stage();
        window.setResizable(false);
        window.setTitle("Pharmacy - Prescription Medications");


        //Start Sidebar

        Image UserImg = new Image (Main.class.getResourceAsStream("icons/UserIcon.png"));
        ImageView UserIcon = new ImageView(UserImg);
        UserIcon.setPreserveRatio(true);
        UserIcon.setFitHeight(25);
        UserIcon.setLayoutX(25);
        UserIcon.setLayoutY(25);

        Label Username = new Label(currentuser.getFirstName() + " " + currentuser.getLastName());
        Username.setId("username");
        Username.setLayoutX(70);
        Username.setLayoutY(25);

        Pane panel_user = new Pane();
        panel_user.setId("UserArea");
        panel_user.setLayoutX(0);
        panel_user.setLayoutY(0);
        panel_user.getChildren().setAll(UserIcon , Username);


        Pane panel_selected = new Pane();
        panel_selected.setMinHeight(45);
        panel_selected.setMinWidth(6);
        panel_selected.setStyle("-fx-background-color: #e2574c;");
        panel_selected.setLayoutY(100);


        Button label1 = new Button("Prescription Medications");
        label1.setId("cat");
        label1.setLayoutX(70);
        label1.setLayoutY(95);
        label1.setMinWidth(180);
        label1.setMinHeight(55);


        Button label2 = new Button("Medications");
        label2.setId("cat");
        label2.setLayoutX(70);
        label2.setLayoutY(155);
        label2.setMinWidth(180);
        label2.setMinHeight(55);

        Button label3 = new Button("Cosmetics");
        label3.setId("cat");
        label3.setLayoutX(70);
        label3.setLayoutY(215);
        label3.setMinWidth(180);
        label3.setMinHeight(55);

        Button label4 = new Button("Orders History");
        label4.setId("cat");
        label4.setLayoutX(70);
        label4.setLayoutY(275);
        label4.setMinWidth(180);
        label4.setMinHeight(55);

        Button label5 = new Button("Settings");
        label5.setId("cat");
        label5.setLayoutX(70);
        label5.setLayoutY(335);
        label5.setMinWidth(180);
        label5.setMinHeight(55);


        Button label6 = new Button("Logout");
        label6.setId("cat");
        label6.setLayoutX(70);
        label6.setLayoutY(395);
        label6.setMinWidth(180);
        label6.setMinHeight(55);


        Image img1 = new Image (Main.class.getResourceAsStream("icons/1.png"));
        ImageView Icon1 = new ImageView(img1);
        Icon1.setPreserveRatio(true);
        Icon1.setFitHeight(25);
        Icon1.setLayoutX(25);
        Icon1.setLayoutY(110);


        Image img2 = new Image (Main.class.getResourceAsStream("icons/2.png"));
        ImageView Icon2 = new ImageView(img2);
        Icon2.setPreserveRatio(true);
        Icon2.setFitHeight(25);
        Icon2.setLayoutX(25);
        Icon2.setLayoutY(170);


        Image img3 = new Image (Main.class.getResourceAsStream("icons/3.png"));
        ImageView Icon3 = new ImageView(img3);
        Icon3.setPreserveRatio(true);
        Icon3.setFitHeight(25);
        Icon3.setLayoutX(25);
        Icon3.setLayoutY(230);

        Image img4 = new Image (Main.class.getResourceAsStream("icons/4.png"));
        ImageView Icon4 = new ImageView(img4);
        Icon4.setPreserveRatio(true);
        Icon4.setFitHeight(25);
        Icon4.setLayoutX(25);
        Icon4.setLayoutY(290);

        Image img5 = new Image (Main.class.getResourceAsStream("icons/5.png"));
        ImageView Icon5 = new ImageView(img5);
        Icon5.setPreserveRatio(true);
        Icon5.setFitHeight(25);
        Icon5.setLayoutX(25);
        Icon5.setLayoutY(350);

        Image img6 = new Image (Main.class.getResourceAsStream("icons/6.png"));
        ImageView Icon6 = new ImageView(img6);
        Icon6.setPreserveRatio(true);
        Icon6.setFitHeight(25);
        Icon6.setLayoutX(25);
        Icon6.setLayoutY(410);


        Pane panel_menu = new Pane();
        panel_menu.getChildren().addAll(panel_selected, panel_user, label1 , label2 , label3 , label4 , label5 , label6 , Icon1 , Icon2 , Icon3 , Icon4 , Icon5 , Icon6);
        panel_menu.setId("Sidebar");
        panel_menu.setMinWidth(250);
        panel_menu.setMinHeight(600);
        panel_menu.setLayoutX(0);
        panel_menu.setLayoutY(0);

        //End Sidebar


        //Start Body


        //Start Page1

        //New
        Button btnAdd_PM = new Button("Add To Cart");
        btnAdd_PM.setId("addtocartBtn");
        btnAdd_PM.setLayoutX(20);
        btnAdd_PM.setLayoutY(396);
        btnAdd_PM.setMinWidth(200);
        btnAdd_PM.setMaxHeight(40);
        btnAdd_PM.setMinHeight(40);
        btnAdd_PM.setDisable(true);
        //End New
        Label Title1 = new Label("Prescription Medications");
        Title1.setId("Title");
        Title1.setLayoutY(25);

        Pane topPage1 = new Pane();
        topPage1.setId("topPage");
        topPage1.getChildren().setAll(Title1);
        Title1.layoutXProperty().bind(topPage1.widthProperty().subtract(Title1.widthProperty()).divide(2));
        ObservableList<Product> p1_data  = FXCollections.observableArrayList();
        FillTableViewByProduct(p1_data, ProductType.PM); // Edited ///////////////////////////////////////////////////////////////

        TableView p1_table = new TableView();
        p1_table.setId("productsTable");
        p1_table.setPlaceholder(new Label("There is no products !"));
        p1_table.setLayoutX(20);
        p1_table.setLayoutY(100);
        p1_table.setMinWidth(624); //Edited
        p1_table.setMaxWidth(624); //Edited
        p1_table.setMinHeight(280); //Edited
        p1_table.setMaxHeight(280); //Edited
        p1_table.setEditable(true);



        TableColumn name = new TableColumn("Product name");
        name.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("name"));
        name.setMinWidth(110);
        name.setMaxWidth(110);

        TableColumn code = new TableColumn("Code");
        code.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, Integer>("code"));
        code.setMinWidth(102);
        code.setMaxWidth(102);


        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, Double>("price"));
        price.setMinWidth(102);
        price.setMaxWidth(102);

        /*TableColumn quantity = new TableColumn("Quantity");
        quantity.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, Integer>("quantity"));
        quantity.setMinWidth(66);
        quantity.setMaxWidth(66);*/

        TableColumn purpose = new TableColumn("Purpose");
        purpose.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("purpose"));
        purpose.setMinWidth(102);
        purpose.setMaxWidth(102);

        TableColumn adultDose = new TableColumn("Adult dose");
        adultDose.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("adultDose"));
        adultDose.setMinWidth(102);
        adultDose.setMaxWidth(102);

        TableColumn childDose = new TableColumn("Child dose");
        childDose.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("childDose"));
        childDose.setMinWidth(102);
        childDose.setMaxWidth(102);

        /*TableColumn activeIngredient = new TableColumn("Active Ingredient");
        activeIngredient.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("activeIngredient"));
        activeIngredient.setMinWidth(75);
        activeIngredient.setMaxWidth(75);*/

        /*TableColumn expiredDateS = new TableColumn("Expired");
        expiredDateS.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("expiredDateS"));
        expiredDateS.setMinWidth(80);
        expiredDateS.setMaxWidth(80);*/

        /*TableColumn manufacturer = new TableColumn("Manufacturer");
        manufacturer.setCellValueFactory(new PropertyValueFactory<PrescriptionMedication, String>("manufacturer"));
        manufacturer.setMinWidth(75);
        manufacturer.setMaxWidth(75);*/


        p1_table.getColumns().setAll(name,code,price,purpose,adultDose,childDose);
        p1_table.setItems(p1_data);


        Image PrescriptionIcon = new Image (Main.class.getResourceAsStream("icons/PrescriptionIcon.png"));
        ImageView PrescriptionImg = new ImageView(PrescriptionIcon);
        PrescriptionImg.setFitHeight(25);
        PrescriptionImg.setPreserveRatio(true);
        PrescriptionImg.setLayoutX(454);
        PrescriptionImg.setLayoutY(404);

        TextField PrescriptionCode = new TextField();
        PrescriptionCode.setPromptText("Enter Prescription Code");
        PrescriptionCode.setMaxHeight(40);
        PrescriptionCode.setMinHeight(40);
        PrescriptionCode.setLayoutX(444);
        PrescriptionCode.setLayoutY(396);
        PrescriptionCode.setMinWidth(200);
        PrescriptionCode.setStyle("-fx-padding: 2px 14px 2px 40px !important;");

        Image PrescriptionCodeIcon1 = new Image (Main.class.getResourceAsStream("icons/Correct.png"));
        ImageView PrescriptionCodeCorrect = new ImageView(PrescriptionCodeIcon1);
        PrescriptionCodeCorrect.setPreserveRatio(true);
        PrescriptionCodeCorrect.setFitHeight(9);
        PrescriptionCodeCorrect.setLayoutX(620);
        PrescriptionCodeCorrect.setLayoutY(412);
        PrescriptionCodeCorrect.setVisible(false);


        Image PrescriptionCodeIcon2 = new Image (Main.class.getResourceAsStream("icons/Wrong.png"));
        ImageView PrescriptionCodeWrong = new ImageView(PrescriptionCodeIcon2);
        PrescriptionCodeWrong.setPreserveRatio(true);
        PrescriptionCodeWrong.setFitHeight(9);
        PrescriptionCodeWrong.setLayoutX(620);
        PrescriptionCodeWrong.setLayoutY(412);
        PrescriptionCodeWrong.setVisible(false);


        Pane Page1 = new Pane();
        Page1.setLayoutX(250);
        Page1.setId("Page1");
        Page1.getChildren().setAll(topPage1 , p1_table , PrescriptionCode , PrescriptionCodeWrong , PrescriptionCodeCorrect ,PrescriptionImg , btnAdd_PM);


        //End Page1



        //Start Page2

        Button btnAdd_M = new Button("Add To Cart");
        btnAdd_M.setId("addtocartBtn");
        btnAdd_M.setLayoutX(20);
        btnAdd_M.setLayoutY(396);
        btnAdd_M.setMinWidth(200);
        btnAdd_M.setMaxHeight(40);
        btnAdd_M.setMinHeight(40);
        btnAdd_M.setDisable(true);

        Label Title2 = new Label("Medication");
        Title2.setId("Title");
        Title2.setLayoutY(25);
        Title2.setLayoutX(200);

        ObservableList<Product> p2_data = FXCollections.observableArrayList();
        FillTableViewByProduct(p2_data, ProductType.Medication);

        TableColumn name2 = new TableColumn("Product name");
        name2.setCellValueFactory(new PropertyValueFactory<Medication, String>("name"));
        name2.setMinWidth(110);
        name2.setMaxWidth(110);

        TableColumn code2 = new TableColumn("Code");
        code2.setCellValueFactory(new PropertyValueFactory<Medication, Integer>("code"));
        code2.setMinWidth(102);
        code2.setMaxWidth(102);


        TableColumn price2 = new TableColumn("Price");
        price2.setCellValueFactory(new PropertyValueFactory<Medication, Double>("price"));
        price2.setMinWidth(102);
        price2.setMaxWidth(102);

        TableColumn purpose2 = new TableColumn("Purpose");
        purpose2.setCellValueFactory(new PropertyValueFactory<Medication, String>("purpose"));
        purpose2.setMinWidth(102);
        purpose2.setMaxWidth(102);

        TableColumn adultDose2 = new TableColumn("Adult dose");
        adultDose2.setCellValueFactory(new PropertyValueFactory<Medication, String>("adultDose"));
        adultDose2.setMinWidth(102);
        adultDose2.setMaxWidth(102);

        TableColumn childDose2 = new TableColumn("Child dose");
        childDose2.setCellValueFactory(new PropertyValueFactory<Medication, String>("childDose"));
        childDose2.setMinWidth(102);
        childDose2.setMaxWidth(102);

        TableView p2_table = new TableView();
        p2_table.setId("productsTable");
        p2_table.setPlaceholder(new Label("There is no products !"));
        p2_table.setLayoutX(20);
        p2_table.setLayoutY(100);
        p2_table.setMinWidth(624); //Edited
        p2_table.setMaxWidth(624); //Edited
        p2_table.setMinHeight(280); //Edited
        p2_table.setMaxHeight(280); //Edited
        p2_table.setEditable(true);

        p2_table.getColumns().setAll(name2,code2,price2,purpose2,adultDose2,childDose2);
        p2_table.setItems(p2_data);



        Pane topPage2 = new Pane();
        topPage2.setId("topPage");
        topPage2.getChildren().setAll(Title2);
        Title2.layoutXProperty().bind(topPage2.widthProperty().subtract(Title2.widthProperty()).divide(2));


        Pane Page2 = new Pane();
        Page2.setLayoutX(250);
        Page2.setId("Page2");
        Page2.getChildren().setAll(topPage2 , p2_table, btnAdd_M);


        //End Page2


        //Start Page3

        Label Title3 = new Label("Cosmetics");
        Title3.setId("Title");
        Title3.setLayoutY(25);
        Title3.setLayoutX(200);

        Button btnAdd_C = new Button("Add To Cart");
        btnAdd_C.setId("addtocartBtn");
        btnAdd_C.setLayoutX(20);
        btnAdd_C.setLayoutY(396);
        btnAdd_C.setMinWidth(200);
        btnAdd_C.setMaxHeight(40);
        btnAdd_C.setMinHeight(40);
        btnAdd_C.setDisable(true);

        Pane topPage3 = new Pane();
        topPage3.setId("topPage");
        topPage3.getChildren().setAll(Title3);
        Title3.layoutXProperty().bind(topPage3.widthProperty().subtract(Title3.widthProperty()).divide(2));

        ObservableList<Product> p3_data = FXCollections.observableArrayList();
        FillTableViewByProduct(p3_data, ProductType.Cosmetics);

        TableColumn name3 = new TableColumn("Product name");
        name3.setCellValueFactory(new PropertyValueFactory<Cosmetics, String>("name"));
        name3.setMinWidth(156);
        name3.setMaxWidth(156);

        TableColumn code3 = new TableColumn("Code");
        code3.setCellValueFactory(new PropertyValueFactory<Cosmetics, Integer>("code"));
        code3.setMinWidth(155);
        code3.setMaxWidth(155);


        TableColumn price3 = new TableColumn("Price");
        price3.setCellValueFactory(new PropertyValueFactory<Cosmetics, Double>("price"));
        price3.setMinWidth(156);
        price3.setMaxWidth(156);



        TableColumn category3 = new TableColumn("Category");
        category3.setCellValueFactory(new PropertyValueFactory<Cosmetics, String>("category"));
        category3.setMinWidth(156);
        category3.setMaxWidth(156);


        TableView p3_table = new TableView();
        p3_table.setId("productsTable");
        p3_table.setPlaceholder(new Label("There is no products !"));
        p3_table.setLayoutX(20);
        p3_table.setLayoutY(100);
        p3_table.setMinWidth(624); //Edited
        p3_table.setMaxWidth(624); //Edited
        p3_table.setMinHeight(280); //Edited
        p3_table.setMaxHeight(280); //Edited
        p3_table.setEditable(true);

        p3_table.getColumns().setAll(name3,code3,price3,category3);
        p3_table.setItems(p3_data);

        Pane Page3 = new Pane();
        Page3.setLayoutX(250);
        Page3.setId("Page3");
        Page3.getChildren().setAll(topPage3, p3_table, btnAdd_C);

        //End Page3


        //Start Page4

        Label Title4 = new Label("Orders History");
        Title4.setId("Title");
        Title4.setLayoutY(25);
        Title4.setLayoutX(200);

        Pane topPage4 = new Pane();
        topPage4.setId("topPage");
        topPage4.getChildren().setAll(Title4);
        Title4.layoutXProperty().bind(topPage4.widthProperty().subtract(Title4.widthProperty()).divide(2));


        TableView OrdersList = new TableView();
        OrdersList.setPlaceholder(new Label("You didn't made any orders !"));
        OrdersList.setLayoutX(20);
        OrdersList.setLayoutY(100);
        OrdersList.setMinWidth(624); //Edited
        OrdersList.setMaxWidth(624); //Edited
        OrdersList.setMinHeight(280); //Edited
        OrdersList.setMaxHeight(280); //Edited
        OrdersList.setEditable(true);



        TableColumn OrderId = new TableColumn("Order ID");
        OrderId.setCellValueFactory(new PropertyValueFactory("invoiceId"));
        OrderId.setMinWidth(120);
        OrderId.setMaxWidth(120);

        TableColumn OrderDate = new TableColumn("Order Date");
        OrderDate.setCellValueFactory(new PropertyValueFactory("invoiceDate"));
        OrderDate.setMinWidth(130);
        OrderDate.setMaxWidth(130);


        TableColumn OrderTime = new TableColumn("Order Time");
        OrderTime.setCellValueFactory(new PropertyValueFactory("invoiceTime"));
        OrderTime.setMinWidth(110);
        OrderTime.setMaxWidth(110);

        TableColumn OrderPrice = new TableColumn("Total Price");
        OrderPrice.setCellValueFactory(new PropertyValueFactory("invoicePrice"));
        OrderPrice.setMinWidth(130);
        OrderPrice.setMaxWidth(130);

        TableColumn OrderStatus = new TableColumn("Order Status");
        OrderStatus.setMinWidth(130);
        OrderStatus.setMaxWidth(130);
        OrderStatus.setCellValueFactory(new PropertyValueFactory("status"));
        OrderStatus.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
            @Override
            public TableCell<Order, String> call(TableColumn<Order, String> col) {
                final TableCell<Order, String> cell = new TableCell<Order, String>() {
                    @Override
                    public void updateItem(String status, boolean empty) {
                        super.updateItem(status, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(status);
                            if (status.equals("Pending")) {
                                setStyle("-fx-text-fill: #cc0000;");
                            } else if (status.equals("Is delivering ..")) {
                                setStyle("-fx-text-fill: #65879e;");
                            } else if (status.equals("Completed")) {
                                setStyle("-fx-text-fill: #00a800;");
                            }
                        }
                    }
                };
                return cell;
            }
        });

        OrdersList.getColumns().setAll(OrderId , OrderDate , OrderTime , OrderPrice , OrderStatus);
        OrdersList.setItems(CustomerOrders);

        Button DetailsBtn = new Button("View Order Details");
        DetailsBtn.setLayoutX(20);
        DetailsBtn.setLayoutY(400);
        DetailsBtn.setId("DetailsBtn");

        Pane Page4 = new Pane();
        Page4.setLayoutX(250);
        Page4.setId("Page4");
        Page4.getChildren().setAll(topPage4 , OrdersList , DetailsBtn);

        //End Page4


        //Start Page5

        Pane topPage5 = new Pane();
        topPage5.setId("topPage");


        Image newCustomer = new Image (Main.class.getResourceAsStream("icons/editCustomer.png"));
        ImageView NewCustomer = new ImageView(newCustomer);
        NewCustomer.setFitHeight(100);
        NewCustomer.setPreserveRatio(true);
        NewCustomer.setLayoutX(280);
        NewCustomer.setLayoutY(33);




        Image FN_Img = new Image (Main.class.getResourceAsStream("icons/firstname.png"));
        ImageView FN_Icon = new ImageView(FN_Img);
        FN_Icon.setFitHeight(33);
        FN_Icon.setPreserveRatio(true);
        FN_Icon.setLayoutX(90);
        FN_Icon.setLayoutY(178);


        Image LN_Img = new Image (Main.class.getResourceAsStream("icons/lastname.png"));
        ImageView LN_Icon = new ImageView(LN_Img);
        LN_Icon.setFitHeight(33);
        LN_Icon.setPreserveRatio(true);
        LN_Icon.setLayoutX(350);
        LN_Icon.setLayoutY(178);

        Image user2 = new Image (Main.class.getResourceAsStream("icons/UserIcon.png"));
        ImageView userIcon2 = new ImageView(user2);
        userIcon2.setFitHeight(35);
        userIcon2.setPreserveRatio(true);
        userIcon2.setLayoutX(90);
        userIcon2.setLayoutY(250);


        Image pass2 = new Image (Main.class.getResourceAsStream("icons/pass.png"));
        ImageView passIcon2 = new ImageView(pass2);
        passIcon2.setFitHeight(35);
        passIcon2.setPreserveRatio(true);
        passIcon2.setLayoutX(350);
        passIcon2.setLayoutY(250);

        Image phoneImg = new Image (Main.class.getResourceAsStream("icons/phone.png"));
        ImageView phoneIcon = new ImageView(phoneImg);
        phoneIcon.setFitHeight(34);
        phoneIcon.setPreserveRatio(true);
        phoneIcon.setLayoutX(100);
        phoneIcon.setLayoutY(322);


        Image genderImg = new Image (Main.class.getResourceAsStream("icons/gender.png"));
        ImageView genderIcon = new ImageView(genderImg);
        genderIcon.setFitHeight(30);
        genderIcon.setPreserveRatio(true);
        genderIcon.setLayoutX(355);
        genderIcon.setLayoutY(325);


        Image addressImg = new Image (Main.class.getResourceAsStream("icons/address.png"));
        ImageView addressIcon = new ImageView(addressImg);
        addressIcon.setFitHeight(26);
        addressIcon.setPreserveRatio(true);
        addressIcon.setLayoutX(355);
        addressIcon.setLayoutY(395);




        TextField FN = new TextField (currentuser.getFirstName());
        FN.setEditable(false);
        FN.setLayoutX(80);
        FN.setLayoutY(170);
        FN.setMinWidth(240);


        TextField LN = new TextField(currentuser.getLastName());
        FN.setEditable(false);
        LN.setLayoutX(340);
        LN.setLayoutY(170);
        LN.setMinWidth(240);


        TextField p5_txtUsername = new TextField (currentuser.getUsername());
        p5_txtUsername.setEditable(false);
        p5_txtUsername.setLayoutX(80);
        p5_txtUsername.setLayoutY(240);
        p5_txtUsername.setMinWidth(240);

        final PasswordField p5_txtPassword = new PasswordField ();
        p5_txtPassword.setPromptText("Enter new Password");
        p5_txtPassword.setEditable(false);
        p5_txtPassword.setLayoutX(340);
        p5_txtPassword.setLayoutY(240);
        p5_txtPassword.setMinWidth(240);



        TextField p5_txtPhone = new TextField (currentuser.getPhone());
        p5_txtPhone.setEditable(false);
        p5_txtPhone.setLayoutX(80);
        p5_txtPhone.setLayoutY(310);
        p5_txtPhone.setMinWidth(240);


        ChoiceBox p5_CbGender = new ChoiceBox(FXCollections.observableArrayList("Male","Female"));
        if (currentuser.getGender().equalsIgnoreCase("Male"))
            p5_CbGender.getSelectionModel().select(0);
        else
            p5_CbGender.getSelectionModel().select(1);
        p5_CbGender.setId("gender");
        p5_CbGender.setDisable(true);
        p5_CbGender.setLayoutX(340);
        p5_CbGender.setLayoutY(310);
        p5_CbGender.setMinWidth(240);


        ChoiceBox p5_CbDay = new ChoiceBox(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"));
        p5_CbDay.getSelectionModel().select(currentuser.getDateOfBirth().getDay()-1);
        p5_CbDay.setDisable(true);
        p5_CbDay.setMaxWidth(70);
        p5_CbDay.setMinWidth(70);
        p5_CbDay.setLayoutX(80);
        p5_CbDay.setLayoutY(380);

        ChoiceBox p5_CbMonth = new ChoiceBox(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12"));
        p5_CbMonth.getSelectionModel().select(currentuser.getDateOfBirth().getMonth()-1);
        p5_CbMonth.setDisable(true);
        p5_CbMonth.setMaxWidth(78);
        p5_CbMonth.setMinWidth(78);
        p5_CbMonth.setLayoutX(160);
        p5_CbMonth.setLayoutY(380);

        ChoiceBox p5_CbYear = new ChoiceBox(FXCollections.observableArrayList("1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018"));
        p5_CbYear.getSelectionModel().select(currentuser.getDateOfBirth().getYear()-1970);
        p5_CbYear.setDisable(true);
        p5_CbYear.setMaxWidth(70);
        p5_CbYear.setMinWidth(70);
        p5_CbYear.setLayoutX(248);
        p5_CbYear.setLayoutY(380);


        TextField p5_txtAddress = new TextField (currentuser.getAddress());
        p5_txtAddress.setEditable(false);
        p5_txtAddress.setLayoutX(340);
        p5_txtAddress.setLayoutY(380);
        p5_txtAddress.setMinWidth(240);


        p5_txtAddress.setOpacity(.3);
        p5_txtPassword.setOpacity(.3);
        p5_txtPhone.setOpacity(.3);
        p5_txtUsername.setOpacity(.3);
        FN.setOpacity(.3);
        LN.setOpacity(.3);

        p5_txtAddress.setCursor(Cursor.DEFAULT);
        p5_txtPassword.setCursor(Cursor.DEFAULT);
        p5_txtPhone.setCursor(Cursor.DEFAULT);
        p5_txtUsername.setCursor(Cursor.DEFAULT);

        Button p5_btnSubmit = new Button("Save");
        p5_btnSubmit.setDisable(true);
        p5_btnSubmit.setLayoutX(340);
        p5_btnSubmit.setLayoutY(460);
        p5_btnSubmit.setMinWidth(240);
        p5_btnSubmit.setId("submit");

        Button p5_btnEdit = new Button("Edit");
        p5_btnEdit.setLayoutX(80);
        p5_btnEdit.setLayoutY(460);
        p5_btnEdit.setMinWidth(240);
        p5_btnEdit.setId("edit");

        Button p5_btnCancel = new Button("Cancel");
        p5_btnCancel.setLayoutX(80);
        p5_btnCancel.setLayoutY(460);
        p5_btnCancel.setMinWidth(240);
        p5_btnCancel.setVisible(false);
        p5_btnCancel.setId("cancel2");

        Pane Page5 = new Pane();
        Page5.setLayoutX(250);
        Page5.setId("Page5");
        Page5.getChildren().setAll(topPage5 , NewCustomer, FN , LN , p5_txtUsername, p5_txtPassword , p5_txtPhone, p5_CbGender , p5_CbDay , p5_CbMonth , p5_CbYear , p5_txtAddress, p5_btnCancel, p5_btnEdit, p5_btnSubmit, FN_Icon , LN_Icon , userIcon2, passIcon2, phoneIcon, genderIcon, addressIcon);

        //End Page5


        //End Body


        //Start My Cart


        Image CartImg = new Image (Main.class.getResourceAsStream("icons/mycart.png"));
        ImageView CartIcon = new ImageView(CartImg);
        CartIcon.setPreserveRatio(true);
        CartIcon.setFitHeight(25);
        CartIcon.setLayoutX(25);
        CartIcon.setLayoutY(25);

        Label Mycart = new Label("My Cart");
        Mycart.setId("mycart");
        Mycart.setLayoutX(60);
        Mycart.setLayoutY(25);

        Pane panel_cartArea = new Pane();
        panel_cartArea.setId("CartArea");
        panel_cartArea.setLayoutX(0);
        panel_cartArea.setLayoutY(0);
        panel_cartArea.getChildren().setAll(CartIcon , Mycart);

        Label TotalDue = new Label("Total Due : 0 LE");
        TotalDue.setLayoutX(20);
        TotalDue.setLayoutY(396);
        TotalDue.setStyle("-fx-text-fill: #fff;");


        Button confirm = new Button("Confirm Order");
        confirm.setId("confirm");
        confirm.setLayoutX(20);
        confirm.setLayoutY(440);
        confirm.setMinWidth(210);
        confirm.setMinHeight(40);
        confirm.setMaxHeight(40);
        confirm.setDisable(true);

        Button cancel = new Button("Clear");
        cancel.setId("cancel");
        cancel.setLayoutX(20);
        cancel.setLayoutY(490); //Edited
        cancel.setMinWidth(210);
        cancel.setMinHeight(40);
        cancel.setMaxHeight(40);
        cancel.setDisable(true);

        TableView pCart_tvOrder = new TableView();
        pCart_tvOrder.setPlaceholder(new Label("Your cart is empty !"));

        pCart_tvOrder.setStyle(" -fx-border-width: 0 !important;");
        pCart_tvOrder.setLayoutX(20);
        pCart_tvOrder.setLayoutY(100);
        pCart_tvOrder.setMinWidth(210); //Edited
        pCart_tvOrder.setMaxWidth(210); //Edited
        pCart_tvOrder.setMinHeight(276); //Edited
        pCart_tvOrder.setMaxHeight(276); //Edited
        pCart_tvOrder.setEditable(true);

        TableColumn ProductName = new TableColumn("Product");
        ProductName.setMinWidth(80);
        ProductName.setMaxWidth(80);
        TableColumn ProductPrice = new TableColumn("Price");
        ProductPrice.setMinWidth(55);
        ProductPrice.setMaxWidth(55);
        TableColumn ProductQuantity = new TableColumn("Quantity");
        ProductQuantity.setMinWidth(73);
        ProductQuantity.setMaxWidth(73);

        ProductName.setCellValueFactory( new PropertyValueFactory<Person,String>("name"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<Person,String>("price"));
        ProductQuantity.setCellValueFactory(new PropertyValueFactory<Person,String>("quantity"));

        pCart_tvOrder.getColumns().addAll(ProductName, ProductPrice, ProductQuantity);
        pCart_tvOrder.setItems(pCart_tvOrder_data);

        Pane MyCart = new Pane();
        MyCart.getChildren().addAll(panel_cartArea , TotalDue , confirm, cancel, pCart_tvOrder);
        MyCart.setId("Sidebar");
        MyCart.setMinWidth(250);
        MyCart.setMinHeight(600);
        MyCart.setLayoutX(910);
        MyCart.setLayoutY(0);


        //End My Cart


        Pane panel = new Pane();
        panel.setId("Panel");
        panel.getChildren().setAll(panel_menu , Page1 , MyCart);


        Scene scene = new Scene(panel , 1160, 600);
        scene.getStylesheets().add("pharmacy/style.css");
        Font.loadFont(Main.class.getResource("Cairo.ttf").toExternalForm(),12);
        window.setScene(scene);
        window.show();


        label1.setOnMouseClicked(event -> {
            panel.getChildren().setAll(panel_menu , Page1 , MyCart);
            panel_selected.setLayoutY(100);
            window.setTitle("Pharmacy - Prescription Medications");
        });


        label2.setOnMouseClicked(event -> {
            panel.getChildren().setAll(panel_menu , Page2 , MyCart);
            panel_selected.setLayoutY(160);
            window.setTitle("Pharmacy - Medications");
        });



        label3.setOnMouseClicked(event -> {
            panel.getChildren().setAll(panel_menu , Page3 , MyCart);
            panel_selected.setLayoutY(220);
            window.setTitle("Pharmacy - Cosmetics");
        });



        label4.setOnMouseClicked(event -> {
            panel.getChildren().setAll(panel_menu , Page4 , MyCart);
            panel_selected.setLayoutY(280);
            window.setTitle("Pharmacy - Orders History");
            CustomerOrders.clear();
            DetailsBtn.setDisable(true);
            for (int i = 0; i < PharmacyDatabase.OrderTable.getOrders_Number(); i++) {
                if (PharmacyDatabase.OrderTable.getOrderIndex(i).getInvoiceCustomer().getID() == currentuser.getID()) {
                    CustomerOrders.add(0 , PharmacyDatabase.OrderTable.getOrderIndex(i));
                }
            }
        });


        label5.setOnMouseClicked(event -> {
            panel.getChildren().setAll(panel_menu , Page5 , MyCart);
            panel_selected.setLayoutY(340);
            window.setTitle("Pharmacy - Settings");
        });


        label6.setOnMouseClicked(event -> {
            if (MsgBox.ShowMsg("Logout","Are you sure you want to log out and quit ?",MsgBox.type.warning)) {
                pCart_tvOrder_data.clear();
                p1_data.clear(); /////////////////////////////////////////////////////////////////////////////////////
                LoginAndRegister obj = new LoginAndRegister();
                obj.start(new Stage());
                window.hide();
            }
        });



        btnAdd_PM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (p1_table.getSelectionModel().getSelectedItem() != null) {
                    PrescriptionMedication p = (PrescriptionMedication)p1_table.getSelectionModel().getSelectedItem();
                    if (PharmacyDatabase.PrescriptionTable.isValidPrescription(PrescriptionCode.getText() , p)) {
                        PrescriptionCode.setStyle("-fx-border-color: #3bd374;-fx-padding: 2px 14px 2px 40px !important;");
                        PrescriptionCodeCorrect.setVisible(true);
                        PrescriptionCodeWrong.setVisible(false);
                        if (AddToCart(p1_data,p)) {
                            confirm.setDisable(false);
                            cancel.setDisable(false);
                            TotalDue.setText("Total Due : " + String.valueOf(getTotalDue()) + " LE");
                        }
                    } else {
                        PrescriptionCode.setStyle("-fx-border-color: #ee5f67;-fx-padding: 2px 14px 2px 40px !important;");
                        PrescriptionCodeCorrect.setVisible(!true);
                        PrescriptionCodeWrong.setVisible(!false);
                        if (PrescriptionCode.getText().equals("")) {
                            MsgBox.ShowMsg("Ops !" , "You must enter prescription code !" , MsgBox.type.error);
                        } else if (PrescriptionCode.getText().length() != 8) {
                            MsgBox.ShowMsg("Ops !" , "Prescription code must be 8 digits only !" , MsgBox.type.error);
                        } else {
                            MsgBox.ShowMsg("Ops !" , "Prescription code is invalid or not  selected medication !" , MsgBox.type.error);
                        }
                    }
                }
            }
        });

        btnAdd_M.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (p2_table.getSelectionModel().getSelectedItem() != null) {
                    Medication p = (Medication)p2_table.getSelectionModel().getSelectedItem();
                    if (AddToCart(p2_data,p)) {
                        confirm.setDisable(false);
                        cancel.setDisable(false);
                        TotalDue.setText("Total Due : " + String.valueOf(getTotalDue()) + " LE");
                    }
                }
            }
        });


        btnAdd_C.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (p3_table.getSelectionModel().getSelectedItem() != null) {
                    Cosmetics p = (Cosmetics)p3_table.getSelectionModel().getSelectedItem();
                    if (AddToCart(p3_data, p)) {
                        confirm.setDisable(false);
                        cancel.setDisable(false);
                        TotalDue.setText("Total Due : " + String.valueOf(getTotalDue()) + " LE");
                    }
                }
            }
        });


        confirm.setOnMouseClicked(event -> {
            ArrayList<Product> Cartlist = new ArrayList<>();
            Cartlist.addAll(pCart_tvOrder_data);
            Order currentOrder = new Order(getTotalDue() , (Customer) currentuser , Cartlist);
            PharmacyDatabase.OrderTable.addOrder(currentOrder);
            ChangeDB(pCart_tvOrder_data);
            pCart_tvOrder_data.clear();
            String msg = "Your order with id #" + currentOrder.getInvoiceId() + " has be confirmed !";
            MsgBox.ShowMsg("DONE", msg , MsgBox.type.success);
            TotalDue.setText("Total Due : 0 LE");
            confirm.setDisable(true);
            cancel.setDisable(true);
        });



        cancel.setOnMouseClicked(event -> {
            pCart_tvOrder_data.clear();
            p1_data.clear();
            p2_data.clear();
            p3_data.clear();
            TotalDue.setText("Total Due : 0 LE");
            confirm.setDisable(true);
            cancel.setDisable(true);
            btnAdd_PM.setDisable(true);
            btnAdd_M.setDisable(true);
            btnAdd_C.setDisable(true);

            FillTableViewByProduct(p1_data,ProductType.PM);
            FillTableViewByProduct(p2_data,ProductType.Medication);
            FillTableViewByProduct(p3_data,ProductType.Cosmetics);
        });

        p1_table.setOnMouseClicked(event -> {
            if (p1_table.getSelectionModel().getSelectedItem() != null) {
                btnAdd_PM.setDisable(false);
            }
        });

        p2_table.setOnMouseClicked(event -> {
            if (p2_table.getSelectionModel().getSelectedItem() != null) {
                btnAdd_M.setDisable(false);
            }
        });

        p3_table.setOnMouseClicked(event -> {
            if (p3_table.getSelectionModel().getSelectedItem() != null) {
                btnAdd_C.setDisable(false);
            }
        });


        OrdersList.setOnMouseClicked(event -> {
            if (OrdersList.getSelectionModel().getSelectedItem() != null) {
                DetailsBtn.setDisable(false);
            }
        });


        DetailsBtn.setOnMouseClicked(event -> {
            if (OrdersList.getSelectionModel().getSelectedItem() != null) {
                OrderDetails.View((Order)OrdersList.getSelectionModel().getSelectedItem());
            }
        });


        p5_btnSubmit.setOnMouseClicked(event -> {

            if (FN.getText().equals("")) {
                MsgBox.ShowMsg("OPS!" , "First name field is empty !" , MsgBox.type.error);
                return;
            }

            if (LN.getText().equals("")) {
                MsgBox.ShowMsg("OPS!" , "Last name field is empty !" , MsgBox.type.error);
                return;
            }

            if (p5_txtUsername.getText().equals("")) {
                MsgBox.ShowMsg("OPS!" , "Username field is empty !" , MsgBox.type.error);
                return;
            }

            if (p5_txtPhone.getText().equals("")) {
                MsgBox.ShowMsg("OPS!" , "Phone field is empty !" , MsgBox.type.error);
                return;
            }


            if (p5_txtAddress.getText().equals("")){
                MsgBox.ShowMsg("OPS!" , "Address field is empty !" , MsgBox.type.error);
                return;
            }

            if (!p5_txtPassword.getText().equals("")) {
                currentuser.setPassword(p5_txtPassword.getText());
            }

            currentuser.setUsername(p5_txtUsername.getText());
            currentuser.setPhone(p5_txtPhone.getText());
            currentuser.setGender(p5_CbGender.getValue().toString());
            currentuser.setDateOfBirth(Integer.parseInt(p5_CbDay.getValue().toString()),Integer.parseInt(p5_CbMonth.getValue().toString()),Integer.parseInt(p5_CbYear.getValue().toString()));
            currentuser.setAddress(p5_txtAddress.getText());
            currentuser.setFirstName(FN.getText());
            currentuser.setLastName(LN.getText());

            p5_CbDay.setDisable(!false);
            p5_CbGender.setDisable(!false);
            p5_CbMonth.setDisable(!false);
            p5_CbYear.setDisable(!false);
            p5_btnSubmit.setDisable(!false);
            p5_txtAddress.setEditable(!true);
            p5_txtPassword.setEditable(!true);
            p5_txtPhone.setEditable(!true);
            p5_txtUsername.setEditable(!true);
            LN.setEditable(!true);
            FN.setEditable(!true);

            p5_txtAddress.setOpacity(.3);
            p5_txtPassword.setOpacity(.3);
            p5_txtPhone.setOpacity(.3);
            p5_txtUsername.setOpacity(.3);
            LN.setOpacity(.3);
            FN.setOpacity(.3);

            p5_txtAddress.setCursor(Cursor.DEFAULT);
            p5_txtPassword.setCursor(Cursor.DEFAULT);
            p5_txtPhone.setCursor(Cursor.DEFAULT);
            p5_txtUsername.setCursor(Cursor.DEFAULT);
            LN.setCursor(Cursor.DEFAULT);
            FN.setCursor(Cursor.DEFAULT);


            p5_btnEdit.setDisable(!true);
            p5_btnEdit.setVisible(!false);
            p5_btnCancel.setVisible(!true);
            p5_btnCancel.setDisable(!false);
            panel_menu.setDisable(!true);

        });


        p5_btnEdit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                p5_CbDay.setDisable(false);
                p5_CbGender.setDisable(false);
                p5_CbMonth.setDisable(false);
                p5_CbYear.setDisable(false);
                p5_btnSubmit.setDisable(false);
                p5_txtAddress.setEditable(true);
                p5_txtPassword.setEditable(true);
                p5_txtPhone.setEditable(true);
                p5_txtUsername.setEditable(true);
                FN.setEditable(true);
                LN.setEditable(true);

                p5_txtAddress.setOpacity(1);
                p5_txtPassword.setOpacity(1);
                p5_txtPhone.setOpacity(1);
                p5_txtUsername.setOpacity(1);
                FN.setOpacity(1);
                LN.setOpacity(1);

                p5_txtAddress.setCursor(Cursor.TEXT);
                p5_txtPassword.setCursor(Cursor.TEXT);
                p5_txtPhone.setCursor(Cursor.TEXT);
                p5_txtUsername.setCursor(Cursor.TEXT);
                FN.setCursor(Cursor.TEXT);
                LN.setCursor(Cursor.TEXT);

                p5_btnEdit.setDisable(true);
                p5_btnEdit.setVisible(false);
                p5_btnCancel.setVisible(true);
                p5_btnCancel.setDisable(false);
                panel_menu.setDisable(true);

            }
        });

        p5_btnCancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                p5_CbDay.setDisable(true);
                p5_CbGender.setDisable(true);
                p5_CbMonth.setDisable(true);
                p5_CbYear.setDisable(true);
                p5_btnSubmit.setDisable(true);
                p5_txtAddress.setEditable(false);
                p5_txtPassword.setEditable(false);
                p5_txtPhone.setEditable(false);
                p5_txtUsername.setEditable(false);
                FN.setEditable(false);
                LN.setEditable(false);

                p5_txtAddress.setOpacity(.3);
                p5_txtPassword.setOpacity(.3);
                p5_txtPhone.setOpacity(.3);
                p5_txtUsername.setOpacity(.3);
                FN.setOpacity(.3);
                LN.setOpacity(.3);

                p5_txtAddress.setCursor(Cursor.DEFAULT);
                p5_txtPassword.setCursor(Cursor.DEFAULT);
                p5_txtPhone.setCursor(Cursor.DEFAULT);
                p5_txtUsername.setCursor(Cursor.DEFAULT);
                FN.setCursor(Cursor.DEFAULT);
                LN.setCursor(Cursor.DEFAULT);

                p5_btnCancel.setDisable(true);
                p5_btnCancel.setVisible(false);
                p5_btnEdit.setVisible(true);
                p5_btnEdit.setDisable(false);
                panel_menu.setDisable(false);

                //Old Values
                p5_txtAddress.setText(currentuser.getAddress());
                p5_txtPassword.clear();
                p5_txtPhone.setText(currentuser.getPhone());
                p5_txtUsername.setText(currentuser.getUsername());
                FN.setText(currentuser.getFirstName());
                LN.setText(currentuser.getLastName());
                if (currentuser.getGender().equalsIgnoreCase("Male"))
                     p5_CbGender.getSelectionModel().select(0);
                else
                     p5_CbGender.getSelectionModel().select(1);
                p5_CbDay.getSelectionModel().select(currentuser.getDateOfBirth().getDay()-1);
                p5_CbMonth.getSelectionModel().select(currentuser.getDateOfBirth().getMonth()-1);
                p5_CbYear.getSelectionModel().select(currentuser.getDateOfBirth().getYear()-1970);

            }
        });




    }

}
