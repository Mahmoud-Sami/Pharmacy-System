package pharmacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pharmacy.ProductClasses.Product;

public class OrderDetails {


    public static void View(Order thisOrder) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Order #" + thisOrder.getInvoiceId());

        Image x = new Image (MsgBox.class.getResourceAsStream("icons/report.png"));
        ImageView Img = new ImageView(x);
        Img.setFitHeight(50);
        Img.setPreserveRatio(true);
        Img.setLayoutX(15);
        Img.setLayoutY(28);

        Label OrderId = new Label("Order ID #" + thisOrder.getInvoiceId());
        OrderId.setLayoutX(80);
        OrderId.setLayoutY(30);

        Label OrderStatusTxt = new Label("Status : ");
        OrderStatusTxt.setLayoutX(80);
        OrderStatusTxt.setLayoutY(55);


        Label OrderStatus = new Label(thisOrder.getStatus());
        OrderStatus.setLayoutX(126);
        OrderStatus.setLayoutY(55);
        if (thisOrder.getStatus().equals("Pending")) {
            OrderStatus.setStyle("-fx-text-fill: #cc0000;");
        } else if (thisOrder.getStatus().equals("Is delivering ..")) {
            OrderStatus.setStyle("-fx-text-fill: #65879e;");
        } else if (thisOrder.getStatus().equals("Completed")) {
            OrderStatus.setStyle("-fx-text-fill: #00a800;");
        }


        Label OrderDate = new Label("Date : " + thisOrder.getInvoiceDate());
        OrderDate.setLayoutX(375);
        OrderDate.setLayoutY(30);


        Label OrderTime = new Label("     Time : " + thisOrder.getInvoiceTime());
        OrderTime.setLayoutX(378);
        OrderTime.setLayoutY(55);


        Label TotalDue = new Label("Total Due : " + thisOrder.getInvoicePrice() + " LE");
        TotalDue.setLayoutX(374);
        TotalDue.setLayoutY(450);


        TableView ProductsList = new TableView();
        ProductsList.setLayoutX(20);
        ProductsList.setLayoutY(110);
        ProductsList.setMinWidth(460); //Edited
        ProductsList.setMaxWidth(460); //Edited
        ProductsList.setMinHeight(320); //Edited
        ProductsList.setMaxHeight(320); //Edited
        ProductsList.setEditable(true);


        ObservableList <Product> ProductsInOrder = FXCollections.observableArrayList();
        ProductsInOrder.addAll(thisOrder.getProductList());

        TableColumn ProductCode = new TableColumn("Product Code");
        ProductCode.setCellValueFactory(new PropertyValueFactory("code"));
        ProductCode.setMinWidth(96);
        ProductCode.setMaxWidth(96);


        TableColumn ProductName = new TableColumn("Product Name");
        ProductName.setCellValueFactory(new PropertyValueFactory("name"));
        ProductName.setMinWidth(160);
        ProductName.setMaxWidth(160);


        TableColumn ProductPrice = new TableColumn("Price");
        ProductPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        ProductPrice.setMinWidth(100);
        ProductPrice.setMaxWidth(100);


        TableColumn ProductQuantity = new TableColumn("Quantity");
        ProductQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        ProductQuantity.setMinWidth(100);
        ProductQuantity.setMaxWidth(100);


        ProductsList.getColumns().setAll(ProductCode, ProductName , ProductPrice , ProductQuantity);
        ProductsList.setItems(ProductsInOrder);

        Pane p = new Pane();
        p.setStyle("-fx-background-color: #fff;");
        p.getChildren().addAll(Img, OrderId , OrderStatusTxt , OrderStatus , OrderDate , OrderTime , ProductsList, TotalDue);



        Scene scene = new Scene(p , 500 , 500);
        scene.getStylesheets().add("pharmacy/style.css");
        Font.loadFont(OrderDetails.class.getResource("Cairo.ttf").toExternalForm(),12);
        window.setScene(scene);
        window.showAndWait();

    }
}