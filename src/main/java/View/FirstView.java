package View;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.DBConnection;
import sample.WestminsterRentalVehicleManager;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class FirstView {
    private static ObservableList<Product> data = getProduct();
    public static TableView table = new TableView();
    public static TableView tableBookingAvailable = new TableView();
    public static DBConnection obj_DBConnection = new DBConnection();

    public static void MainFx(Stage stage) throws IOException {
        Pane root = new Pane();
        stage.setTitle("Vehicle Rents");
        stage.setWidth(535);
        stage.setHeight(560);

        Label lblTitle = new Label("Westminster Vehicle Rentals");
        lblTitle.setLayoutX(40);
        lblTitle.setLayoutY(80);
        lblTitle.setFont(new Font("Fredoka One", 35));
        root.getChildren().add(lblTitle);

        String styles =
                "-fx-background-color: #F1948A ;" +
                        "-fx-border-color: #FDEDEC;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-label-padding: 20;" +
                        "-fx-font-size: 18;"
                         ;
        Button printBtn = new Button();
        printBtn.setStyle(styles);
        printBtn.setMaxSize(200,200);
        printBtn.setText("Print All Details");
        printBtn.setLayoutX(180);
        printBtn.setLayoutY(200);

        root.getChildren().add(printBtn);

        //sercg btton at main window
        Button searchBtn = new Button();
        searchBtn.setStyle(styles);
        searchBtn.setText("Search Vehicles");
        searchBtn.setLayoutX(180);
        searchBtn.setLayoutY(300);
        root.getChildren().add(searchBtn);

        //pane for inside search btn in pick up and drop off section
        Pane searchSub = new Pane();
        Button ButtonSubSearch = new Button();
        ButtonSubSearch.setText("Search Vehicles");
        ButtonSubSearch.setLayoutX(50);
        ButtonSubSearch.setLayoutY(290);
        searchSub.getChildren().add(ButtonSubSearch);

        Scene sc2 = new Scene(searchSub, 400, 400);
        searchBtn.setOnAction(e -> stage.setScene(sc2));

        //back to menu from search
        Button backToMenuSearch = new Button();
        backToMenuSearch.setText("Back to menu");
        backToMenuSearch.setLayoutX(150);
        backToMenuSearch.setLayoutY(290);
        searchSub.getChildren().add(backToMenuSearch);

        //get plate number
        TextField txtVehicleBook = new TextField();
        txtVehicleBook.setLayoutX(20);
        txtVehicleBook.setLayoutY(300);

        //booking a vehicle from search
        Button BookFromSearch =new Button();
        BookFromSearch.setText("Book Vehicle");
        BookFromSearch.setLayoutX(10);
        BookFromSearch.setLayoutY(300);

        Button backToMenuInBooking = new Button();
        backToMenuInBooking.setText("Back To Menu");
        backToMenuInBooking.setLayoutX(30);
        backToMenuInBooking.setLayoutY(300);

        TextField pickupdateBook = new TextField();
        pickupdateBook.setLayoutX(30);
        pickupdateBook.setLayoutY(500);

        TextField dropOffDateBook =new TextField();
        dropOffDateBook.setLayoutX(30);
        dropOffDateBook.setLayoutY(500);

        //----------------------------------------------------------------------------------------------------------------------------------------

        Label lblpickupDate = new Label("Pickup Date >");
        lblpickupDate.setLayoutX(20);
        lblpickupDate.setLayoutY(20);

        Label lblpickupYear = new Label("Pickup Year");
        lblpickupYear.setLayoutX(110);
        lblpickupYear.setLayoutY(20);

        TextField txtpickupYear = new TextField();
        txtpickupYear.setLayoutX(200);
        txtpickupYear.setLayoutY(20);

        searchSub.getChildren().addAll(lblpickupDate, lblpickupYear, txtpickupYear);

        Label lblpickupmonth = new Label("Pickup Month");
        lblpickupmonth.setLayoutX(110);
        lblpickupmonth.setLayoutY(60);

        TextField txtpickupMonth = new TextField();
        txtpickupMonth.setLayoutX(200);
        txtpickupMonth.setLayoutY(60);

        searchSub.getChildren().addAll(lblpickupmonth, txtpickupMonth);

        Label lblpickupDay = new Label("Pickup Day");
        lblpickupDay.setLayoutX(110);
        lblpickupDay.setLayoutY(100);

        TextField txtpickupDay = new TextField();
        txtpickupDay.setLayoutX(200);
        txtpickupDay.setLayoutY(100);

        searchSub.getChildren().addAll(txtpickupDay, lblpickupDay);

        Label lblDropOffDate = new Label("DropOff Date >");
        lblDropOffDate.setLayoutX(20);
        lblDropOffDate.setLayoutY(140);

        Label lblDropOffYear = new Label("DropOff Year");
        lblDropOffYear.setLayoutX(110);
        lblDropOffYear.setLayoutY(140);

        TextField txtdropoffYear = new TextField();
        txtdropoffYear.setLayoutX(200);
        txtdropoffYear.setLayoutY(140);

        searchSub.getChildren().addAll(txtdropoffYear, lblDropOffYear, lblDropOffDate);

        Label lbldropoffMonth = new Label("DropOff Month");
        lbldropoffMonth.setLayoutX(110);
        lbldropoffMonth.setLayoutY(180);

        TextField txtdropoffMonth = new TextField();
        txtdropoffMonth.setLayoutX(200);
        txtdropoffMonth.setLayoutY(180);

        searchSub.getChildren().addAll(txtdropoffMonth, lbldropoffMonth);

        Label lbldropOffDay = new Label("DropOff Day");
        lbldropOffDay.setLayoutX(110);
        lbldropOffDay.setLayoutY(220);

        TextField txtdropofDay = new TextField();
        txtdropofDay.setLayoutX(200);
        txtdropofDay.setLayoutY(220);

        searchSub.getChildren().addAll(txtdropofDay, lbldropOffDay);

        /*
        //Booking
        Pane Booking = new Pane();
        Button book = new Button();
        book.setText("Book The Vehicle");
        book.setLayoutX(12);
        book.setLayoutY(200);
        Booking.getChildren().add(book);

        //In pane Booking
        Button backToMenuBooking = new Button();
        backToMenuBooking.setText("Back to menu!");
        backToMenuBooking.setLayoutX(12);
        backToMenuBooking.setLayoutY(250);
        Booking.getChildren().add(backToMenuBooking);


        Scene booking = new Scene(Booking, 400, 400);
        searchBtn.setOnAction(e -> stage.setScene(sc2));*/

       Label lblFName = new Label("First Name");
        lblFName.setLayoutY(100);
        lblFName.setLayoutX(150);
        TextField txtFname = new TextField();
        txtFname.setLayoutX(220);
        txtFname.setLayoutY(100);

        Label lblLName = new Label("Last Name");
        lblLName.setLayoutY(150);
        lblLName.setLayoutX(150);
        TextField txtLname = new TextField();
        txtLname.setLayoutX(220);
        txtLname.setLayoutY(150);

//---------------------------------------------------------------------------------------------------------------------------------------------------
        //Table Booking
        Scene TableScnBooking = new Scene(new Group());
        final Label labelB = new Label("Available Vehicles");
        labelB.setFont(new Font("Arial", 20));
        tableBookingAvailable.setEditable(true);
        //plateNumber
        TableColumn<BookinData, String> PlateNumberB = new TableColumn<>("PlateNumber");
        PlateNumberB.setMinWidth(150);
        PlateNumberB.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));

        //vehicleType
        TableColumn<BookinData, String> VehicleTypeB = new TableColumn<>("VehicleType");
        VehicleTypeB.setMinWidth(150);
        VehicleTypeB.setCellValueFactory(new PropertyValueFactory<>("vehicle_type"));

        tableBookingAvailable = new TableView<>();
        tableBookingAvailable.getColumns().addAll(PlateNumberB, VehicleTypeB);
       // HBox hBox = new HBox();//Add choiceBox and textField to hBox
       // hBox.setAlignment(Pos.BOTTOM_LEFT);//Center HBox
        //Window inside the print btn
        final VBox vboxBooking = new VBox();
        vboxBooking.setSpacing(10);
        vboxBooking.setPadding(new Insets(10, 0, 0, 10));
        vboxBooking.getChildren().addAll(tableBookingAvailable,txtVehicleBook,BookFromSearch,backToMenuInBooking,pickupdateBook,dropOffDateBook);

        //showing table when clicking on print btn
        ((Group) TableScnBooking.getRoot()).getChildren().addAll(vboxBooking);


        //Button sub search > on action print avilable vehicles
        ButtonSubSearch.setOnAction(new EventHandler<ActionEvent>() {
            Connection conn = obj_DBConnection.get_connection();
            PreparedStatement ps = null;
            Statement st = null;
            ArrayList<String[]> not_booked_vehicles = new ArrayList<String[]>();
            @Override
            public void handle(ActionEvent actionEvent) {
                if (txtpickupDay.getText().isEmpty() | txtpickupMonth.getText().isEmpty() | txtpickupMonth.getText().isEmpty() |
                        txtdropofDay.getText().isEmpty() | txtdropoffMonth.getText().isEmpty() | txtdropoffYear.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You can't continue with with empty feilds");
                    alert.showAndWait();
                } else {

                    int pyear = Integer.parseInt(txtpickupYear.getText());
                    int pmonth = Integer.parseInt(txtpickupMonth.getText());
                    int pday = Integer.parseInt(txtpickupDay.getText());
                    int dyear = Integer.parseInt(txtdropoffYear.getText());
                    int dmonth = Integer.parseInt(txtdropoffMonth.getText());
                    int dday = Integer.parseInt(txtdropofDay.getText());


                    //pickup date set
                    LocalDate pickupdate_ = LocalDate.of(pyear, pmonth, pday);
                    LocalDate dropOffDate_ = LocalDate.of(dyear, dmonth, dday);

                   // Date sqlPickup = Date.valueOf(pickupdate_);
                   // Date sqlDrop = Date.valueOf(dropOffDate_);

                    String formattedDatePick = pickupdate_.format(DateTimeFormatter.ofPattern("d-MMM-yyyy"));
                    String formattedDateDrop = dropOffDate_.format(DateTimeFormatter.ofPattern("d-MMM-yyyy"));

                    pickupdateBook.setText(formattedDatePick);
                    dropOffDateBook.setText(formattedDateDrop);


               /*        String sql = "SELECT *" +
                            "FROM bookedvehicle_details where '" + sqlPickup + "' not BETWEEN pickupDate AND dropOffDate" +
                            "   OR  '" + sqlDrop + "'  not BETWEEN pickupDate AND dropOffDate";
                        ResultSet rs = st.executeQuery(sql);

                        while (rs.next()) {
                            String[] temp = new String[2];
                            temp[0] = rs.getString("plateNumber");
                            temp[1] = rs.getString("vehicle_type");

                            not_booked_vehicles.add(temp);
                            temp[0] = "";
                            temp[1] = "";
                        }
                        sql = "SELECT * FROM vehicle_details where availability = 'vacant'";
                        ResultSet rs1 = st.executeQuery(sql);
                        while (rs1.next()) {
                            String[] temp2 = new String[2];
                            temp2[0] = rs.getString("plateNumber");
                            temp2[1] = rs.getString("vehicle_type");

                            not_booked_vehicles.add(temp2);
                            temp2[0] = "";
                            temp2[1] = "";
                        }

*/
                    WestminsterRentalVehicleManager wes = new WestminsterRentalVehicleManager();
                    boolean ans = false;
                    try {
                        ans = wes.compareTwoDates(pickupdate_,dropOffDate_);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(ans==false){
                        return;
                    }
                        //add data to table
                        tableBookingAvailable.setItems(getBookingData(pickupdate_, dropOffDate_));
                        stage.setScene(TableScnBooking);
                }
            }
        });

        //vehicle booking
        BookFromSearch.setOnAction(new EventHandler<ActionEvent>() {
                Connection conn = obj_DBConnection.get_connection();
                PreparedStatement ps = null;
                Statement st = null;
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (txtVehicleBook.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText(null);
                        alert.setContentText("Enter the Plate Number of the Vehicle To book");
                        alert.showAndWait();
                    } else {
                        String plateNum = txtVehicleBook.getText();

                        //get first name and last name
                       String fnameInput;
                       fnameInput= JOptionPane.showInputDialog("First Name");

                       String lnameInput;
                       lnameInput = JOptionPane.showInputDialog("Last Name");

                       DBConnection obj_DBConnection = new DBConnection();
                       Connection conn = obj_DBConnection.get_connection();
                       PreparedStatement ps =null;

                       String p = (pickupdateBook.getText());
                       System.out.println(p);

                       String d =(dropOffDateBook.getText());
                       System.out.println(d);
                        try{
                            //get pickup date and get drop off date
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                            LocalDate datep = LocalDate.parse(p, formatter);
                            System.out.println("localp " +datep);

                            LocalDate dated = LocalDate.parse(d, formatter);
                            System.out.println("locald "+ dated);

                           st = (Statement) conn.createStatement();
                           String innerSql = "Select * from Vehicle_details";
                           ResultSet resultFromSql = st.executeQuery(innerSql);
                           while(resultFromSql.next()){
                               String vehicleType_ =resultFromSql.getString("vehicle_type");
                               if (vehicleType_.equals("Car") ){
                                   bookACar(plateNum,fnameInput,lnameInput,datep,dated);
                                   break;
                               }else if(vehicleType_.equals("MotorBike") ){
                                   bookAMotorBike(plateNum,fnameInput,lnameInput,datep,dated);
                                   break;
                               }
                           }
                        }catch (Exception e){
                           System.err.println(e);
                       }
                    }
                }});

//////////////////////////////////////////////Filter Records////////////////////////////////////////////////////////////////////////
        Scene TableScn = new Scene(new Group());
        final Label label = new Label("All vehicles");
        label.setFont(new Font("Arial", 20));
        table.setEditable(true);
        //plateNumber
        TableColumn<Product, String> PlateNumber = new TableColumn<>("PlateNumber");
        PlateNumber.setMinWidth(100);
        PlateNumber.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));

        //vehicleType
        TableColumn<Product, String> VehicleType = new TableColumn<>("VehicleType");
        VehicleType.setMinWidth(100);
        VehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));

        //colour
        TableColumn<Product, String> colour = new TableColumn<>("Colour");
        colour.setMinWidth(100);
        colour.setCellValueFactory(new PropertyValueFactory<>("color"));

        //availability
        TableColumn<Product, String> availability = new TableColumn<>("Availability");
        availability.setMinWidth(100);
        availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        //vehicle make
        TableColumn<Product, String> vehicleMake = new TableColumn<>("Vehicle Make");
        vehicleMake.setMinWidth(100);
        vehicleMake.setCellValueFactory(new PropertyValueFactory<>("make"));


        FilteredList<Product> flProduct = new FilteredList(data, p -> true);//Pass the data to a filtered list
        table.setItems(flProduct);//Set the table's items using the filtered list
        table.getColumns().addAll(PlateNumber, VehicleType, colour, availability, vehicleMake);

        //Adding ChoiceBox and TextField here!
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("VehicleType", "Color", "PlateNumber");
        choiceBox.setValue("VehicleType");

        TextField textField = new TextField();
        textField.setPromptText("Enter Filter!");

        textField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            flProduct.setPredicate(product -> {
                String typedText = newValue.toLowerCase();
                switch (choiceBox.getValue())//Switch on choiceBox value
                {
                    case "VehicleType":
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (product.getVehicleType().toLowerCase().indexOf(typedText) != -1) {
                            return true;
                        }
                        break;
                    case "Color":
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (product.getColor().toLowerCase().indexOf(typedText) != -1) {
                            return true;
                        }
                        break;
                    case "PlateNumber":
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (product.getPlateNumber().toLowerCase().indexOf(typedText) != -1) {
                            return true;
                        }
                        break;
                }
                return false;
            });
            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
            {
                if (newVal != null) {
                    textField.setText("");
                    flProduct.setPredicate(null);//This is same as saying flProduct.setPredicate(p->true);
                }
            });
            SortedList<Product> sortedList = new SortedList<>(flProduct);
            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);
        }));

        //putting data in to the table
        table = new TableView<>();
        table.setItems(getProduct());
        table.getColumns().addAll(PlateNumber, VehicleType, vehicleMake, availability, colour);

        HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
        hBox.setAlignment(Pos.BOTTOM_LEFT);//Center HBox
        //Window inside the print btn
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hBox);
        Button backToMenuInPrint = new Button();
        backToMenuInPrint.setText("Back to menu");
        backToMenuInPrint.setLayoutX(400);
        backToMenuInPrint.setLayoutY(500);
        vbox.getChildren().add(backToMenuInPrint);

        //showing table when clicking on print btn
        ((Group) TableScn.getRoot()).getChildren().addAll(vbox);
        printBtn.setOnAction(e -> stage.setScene(TableScn));

        //Main window
        Scene scene01 = new Scene(root, 300, 250);
        stage.setScene(scene01);
        stage.show();

        //Back to Menu
        backToMenuInPrint.setOnAction(e -> stage.setScene(scene01));
        backToMenuSearch.setOnAction(e -> stage.setScene(scene01));
        backToMenuInBooking.setOnAction(e -> stage.setScene(scene01)); //from available table to menu
        //backToMenuBooking.setOnAction(e -> stage.setScene(scene01)); // from book to menu
    }

    //book a motorbike
    public static void bookAMotorBike(String vehiclePN,String fname, String lname, LocalDate pd,LocalDate dd){
        java.sql.Date sqlPickup = java.sql.Date.valueOf(pd);
        java.sql.Date sqlDrop = java.sql.Date.valueOf(dd);
        DBConnection obj_DBConnection = new DBConnection();
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps =null;
        try{
            String innerSql = "Insert into BookedVehicle_Details (plateNumber, pickupDate, dropOffDate, vehicle_type, firstName, LastName) " +
                    "values (?,?,?,?,?,?)";
            ps = (PreparedStatement) conn.prepareStatement(innerSql);
            ps.setString(1,vehiclePN);
            ps.setDate(2,sqlPickup);
            ps.setDate(3,sqlDrop);
            ps.setString(4,"MotorBike");
            ps.setString(5,fname);
            ps.setString(6,lname);
            ps.executeUpdate();
            System.out.println("Booking Successful");
            vehicleTableBooking(vehiclePN);
            //respose lable
            return;

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //book a car
    public static void bookACar( String vehiclePN,String fname, String lname, LocalDate pd, LocalDate dd){
        java.sql.Date sqlPickup = java.sql.Date.valueOf(pd);
        java.sql.Date sqlDrop = java.sql.Date.valueOf(dd);
        DBConnection obj_DBConnection = new DBConnection();
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps =null;
        try{
            String innerSql = "Insert into BookedVehicle_Details (plateNumber, pickupDate, dropOffDate, vehicle_type, firstName, LastName) " +
                    "values (?,?,?,?,?,?)";
            ps = (PreparedStatement) conn.prepareStatement(innerSql);
            ps.setString(1,vehiclePN);
            ps.setDate(2,sqlPickup);
            ps.setDate(3,sqlDrop);
            ps.setString(4,"Car");
            ps.setString(5,fname);
            ps.setString(6,lname);
            ps.executeUpdate();
            System.out.println("Booking Successful");
            vehicleTableBooking(vehiclePN);
            //respose lable
           return;

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //change attributes of vehicle table
  public static void vehicleTableBooking(String plateNumber){
            DBConnection obj_DBConnection = new DBConnection();
            Connection conn = obj_DBConnection.get_connection();
             try{
                 String query2 = "update Vehicle_Details set availability = ? where plateNumber = ?";
                 java.sql.PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                 preparedStmt2.setString(1,"Booked");
                 preparedStmt2.setString   (2, plateNumber);
                 preparedStmt2.executeUpdate();
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

    //getting data to print table
    private static ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        DBConnection obj_DBConnection = new DBConnection();
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        Statement st = null;

        try {
            st = (Statement) conn.createStatement();
            String sql = "SELECT plateNumber,vehicle_type,color,vehicle_make,availability FROM Vehicle_Details";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String plateNumber = rs.getString("plateNumber");
                String typeOfVehicle = rs.getString("vehicle_type");
                String color = rs.getString("color");
                String make = rs.getString("vehicle_make");
                String available = rs.getString("availability");

                products.add(new Product(plateNumber, typeOfVehicle, color, available, make));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return products;
    }

    //getting data to booking avilablr table
    public static ObservableList<BookinData> getBookingData(LocalDate pD, LocalDate dD) {
        java.sql.Date sqlPickup = java.sql.Date.valueOf(pD);
        java.sql.Date sqlDrop = java.sql.Date.valueOf(dD);
        ObservableList<BookinData> products_ = FXCollections.observableArrayList();
        DBConnection obj_DBConnection = new DBConnection();
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        Statement st = null;

        try {
            st = (Statement) conn.createStatement();
            String sql = "SELECT * FROM vehicle_details where availability ='vacant'";

            ResultSet rs = st.executeQuery(sql);
            //printing the vehicles which are available for booking
           while (rs.next()) {
                String plateNumber = rs.getString("plateNumber");
                String make = rs.getString("vehicle_type");
                products_.add(new BookinData(plateNumber, make));
               //System.out.println(plateNumber);

            }

             sql = "SELECT *" +
                    "FROM bookedvehicle_details where '"+sqlPickup+"' not BETWEEN pickupDate AND dropOffDate" +
                    "   OR  '"+sqlDrop+"'  not BETWEEN pickupDate AND dropOffDate";

            ResultSet rs1 = st.executeQuery(sql);
            while (rs1.next()) {
                    String plateNumber2 = rs1.getString("plateNumber");
                    String make2 = rs1.getString("vehicle_type");
                    products_.add(new BookinData(plateNumber2, make2));
                //System.out.println(plateNumber2);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return products_;
    }
}

