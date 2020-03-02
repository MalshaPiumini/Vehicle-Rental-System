package sample;

import com.mysql.jdbc.Statement;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {
    private static final int MAX_SPACE = 50;
    private Scanner input_1 = new Scanner(System.in);
    DBConnection obj_DBConnection = new DBConnection();

    //validating the integer inputs
    public void validateInteger() {
        while (!input_1.hasNextInt()) {
            System.out.println("Invalid input, re-enter an Integer");
            input_1.next();
        }
    }

    //Counting the no of vehicles in the system
    public void showFreeSpacesLeft() {
        Connection conn = obj_DBConnection.get_connection();
        int rowCount = -1;
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Vehicle_Details");
            // get the number of rows from the result set
            rs.next();
            rowCount = rs.getInt(1);
            int freeSpaces = MAX_SPACE - rowCount;
            // the mysql insert statement
            System.out.println("Free space left :" + freeSpaces);
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Add vehicle
    public void addVehicle() {
        Connection conn = obj_DBConnection.get_connection();
        int rowCount = -1;
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Vehicle_Details");
            // get the number of rows from the result set
            rs.next();
            rowCount = rs.getInt(1);
            if (rowCount > 50) {
                System.out.println("You can't enter records");
                return;
            } else {
                //get colour
                System.out.println("Enter color of the Vehicle");
                String color = input_1.next();

                //get make
                System.out.println("Enter make of the Vehicle");
                String make = input_1.next();

                //get plate Number
                System.out.println("Enter plate number of the Vehicle");
                String plateNumber = input_1.next();

                System.out.println("If you want to add a car... enter 1\n" + "if you want to add a motor bike.... enter 2");
                validateInteger();
                int option_02 = input_1.nextInt();

                if (option_02 == 1) {
                    //get no of seats
                    System.out.println("Enter number of seats");
                    validateInteger();
                    int noOfSeats = input_1.nextInt();

                    //get no of doors
                    System.out.println("Enter number of doors");
                    validateInteger();
                    int noOfDoors = input_1.nextInt();

                    //model of the car
                    System.out.println("Enter the model of the car");
                    String model = input_1.next();

                    Car tempCar = new Car(color, make, "y", plateNumber, noOfSeats, noOfDoors, model);
                    addCar(tempCar);

                } else if (option_02 == 2) {
                    //get type of the motor bike
                    System.out.println("Enter the type of the Motor Bike ");
                    String typeOfMotorBike = input_1.next();

                    //get motor bike having a side car or not
                    System.out.println("Has bike has a side car or not ? enter (Y/N)");
                    String hasSideCar = input_1.next();

                    MotorBike tempBike = new MotorBike(color, make, "Y", plateNumber, hasSideCar, typeOfMotorBike);
                    addMotorBike(tempBike);
                    showFreeSpacesLeft();
                }else {
                    System.out.println("Wrong input, Re-Enter All");
                }
            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    //Adding a car
    @Override
    public void addCar(Car c) {
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        try {
            //Inserting Data to the Vehicle Details
            String query = "Insert into Vehicle_Details (plateNumber,color,availability,vehicle_type,vehicle_make) values (?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, c.getPlateNumber());
            ps.setString(2, c.getColor());
            ps.setString(3, "Vacant");
            ps.setString(4, "Car");
            ps.setString(5, c.getMake());
            ps.executeUpdate();

            //Inserting data to the car details
            String query_2 = "Insert into Car_Details (plateNumber,carModel,noOfSeats,noOfDoors) values (?,?,?,?)";
            ps = conn.prepareStatement(query_2);
            ps.setString(1, c.getPlateNumber());
            ps.setString(2, c.getCarModel());
            ps.setInt(3, c.getNoOfSeats());
            ps.setInt(4, c.getNoOfDoors());
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Adding a motor bike
    @Override
    public void addMotorBike(MotorBike m) {
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        try {
            //Inserting data to the table vehicle details
            String query = "Insert into Vehicle_Details (plateNumber,color,availability,vehicle_type,vehicle_make) values (?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, m.getPlateNumber());
            ps.setString(2, m.getColor());
            ps.setString(3, "Vacant");
            ps.setString(4, "MotorBike");
            ps.setString(5, m.getMake());
            ps.executeUpdate();

            //Inserting Data to the table MotorBike details
            String query_2 = "Insert into MotorBike_Details (plateNumber,BikeType,hasSideCar) values (?,?,?)";
            ps = conn.prepareStatement(query_2);
            ps.setString(1, m.getPlateNumber());
            ps.setString(2, m.getBiketype());
            ps.setString(3, m.getHasSideCar());
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Deleting vehicle in the system
    public void deleteVehicle(String a) {
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        String typeOfVehicle = "";
        Statement st = null;
        try {
            // create the mysql database connection
            st = (Statement) conn.createStatement();
            String sql = "SELECT * FROM Vehicle_Details where availability = 'Vacant'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String plateNumber = rs.getString("plateNumber");
                if (plateNumber.equals(a)) {
                    typeOfVehicle = rs.getString("vehicle_type");
                    if (typeOfVehicle.equals("Car")) {
                        //deleting the selected car
                        String query = "delete from Car_Details where plateNumber = ?";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, a);
                        ps.execute();// execute the preparedstatement

                        String query2 = "delete from Vehicle_Details where plateNumber = ?";
                        ps = conn.prepareStatement(query2);
                        ps.setString(1, a);
                        ps.execute();// execute the preparedstatement
                        System.out.println("Delete Successful");

                        System.out.println("You deleted the record of " + plateNumber + "\nType : " + typeOfVehicle);
                        String s1 = "PlateNB :";
                        LocalDate date = LocalDate.now(); // gets the current date
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String all = s1 + "  " + plateNumber + "     vehicle Deleted on :"+date.format(formatter)+"\n" ;
                        writeIntoFile(all);
                        break;

                    } else if (typeOfVehicle.equals("MotorBike")) {
                        //deleting the selected MotorBike
                        String query = "delete from MotorBike_Details where plateNumber = ?";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, a);
                        ps.execute();  // execute the preparedstatement

                        String query2 = "delete from Vehicle_Details where plateNumber = ?";
                        ps = conn.prepareStatement(query2);
                        ps.setString(1, a);
                        ps.execute();// execute the preparedstatement
                        System.out.println("Delete Successful");
                        System.out.println("You deleted the record of " + plateNumber + "\nType : " + typeOfVehicle);
                        String s1 = "PlateNB :";
                        LocalDate date = LocalDate.now(); // gets the current date
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String all = s1 + "  " + plateNumber + "     vehicle Deleted on :"+date.format(formatter)+"\n" ;
                        writeIntoFile(all);
                        break;
                    }
                }
            }

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("Got an Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (st != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try

        }
    }

    //printing the databse list according to the ascending order of make
    @Override
    public void printListOfVehicles() {
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        Statement st = null;

        try {
            st = (Statement) conn.createStatement();
            System .out.println("Fetching records in ascending order...according to the make\n");
            String sql = "SELECT * FROM Vehicle_Details ORDER BY vehicle_make ASC";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String plateNumber = rs.getString("plateNumber");
                String typeOfVehicle = rs.getString("vehicle_type");
                String colour = rs.getString("color");
                String make = rs.getString("vehicle_make");

                //Display values
                System.out.println("------------------------------------------------");
                System.out.format("%7s | %12s | %7s | %12s ", plateNumber, typeOfVehicle,colour, make + "\n");
                System.out.println("------------------------------------------------");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (st != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //Edit vehicle details
    @Override
    public void edit(String PN) {
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        String typeOfVehicle = "";
        Statement st = null;
        try {
            // create the mysql database connection
            st = (Statement) conn.createStatement();
            String sql = "SELECT * FROM Vehicle_Details";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String plateNumber = rs.getString("plateNumber");
                if (plateNumber.equals(PN)) {
                    typeOfVehicle = rs.getString("vehicle_type");
                    if (typeOfVehicle.equals("Car")) {
                        editCar(PN);

                    } else if (typeOfVehicle.equals("MotorBike")) {
                        editMotorBike(PN);
                    }
                }
            }

        } catch (Exception e){
            System.out.println("Got an Exception");
            System.err.println(e);
        }

    }

    //booking a vehicle
    @Override
    public void bookVehicle() {
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        Statement st = null;

        try {
            st = (Statement) conn.createStatement();

            //getting pickup date
            Shedule fk = new Shedule();
            System.out.println("Enter the Pickup Shedule");
            System.out.print("Year : ");
            validateInteger();
            int year = input_1.nextInt();
            fk.setYear(year);
            System.out.print("Month : ");
            validateInteger();
            int month = input_1.nextInt();
            fk.setMonth(month);
            System.out.print("Day : ");
            validateInteger();
            int day = input_1.nextInt();
            fk.setDay(day);
            String pickupdate = year + "-"+month+"-"+day;
            System.out.println( " Pickup date :"+pickupdate);
            Shedule fk2 = new Shedule();

            //getting drop off date
            System.out.println("Enter the Drop off date");
            System.out.print("Year : ");
            validateInteger();
            int yearD = input_1.nextInt();
            fk2.setYear(yearD);
            System.out.print("Month : ");
            validateInteger();
            int monthD = input_1.nextInt();
            fk2.setMonth(monthD);
            System.out.print("Day : ");
            validateInteger();
            int dayD = input_1.nextInt();
            fk2.setDay(dayD);
            String dropOffDate = yearD+ "-"+monthD+"-"+dayD;
            System.out.println("drop off date :"+dropOffDate);

            //converting to date
            LocalDate  pickupdate_ = LocalDate.of(year,month,day);
            LocalDate dropOffDate_ = LocalDate.of(yearD,monthD,dayD);

           boolean ans =  compareTwoDates(pickupdate_,dropOffDate_);
           if(ans==false){
               return;
           }

            //get all dates between these dates
            List<LocalDate> totalDates = new ArrayList<>();
            popularDatas(pickupdate_, dropOffDate_, totalDates);
           // System.out.println(totalDates);

            java.sql.Date sqlPickup = java.sql.Date.valueOf(pickupdate_);
            java.sql.Date sqlDrop = java.sql.Date.valueOf(dropOffDate_);

            String sql = "SELECT *" +
                    "FROM bookedvehicle_details where '"+sqlPickup+"' not BETWEEN pickupDate AND dropOffDate" +
                    "   OR  '"+sqlDrop+"'  not BETWEEN pickupDate AND dropOffDate";
            ResultSet rs = st.executeQuery(sql);
            //printing the vehicles which are available for booking
            while (rs.next()) {
                String plateNumber = rs.getString("plateNumber");
                String make = rs.getString("vehicle_type");
                //Display values
                System.out.println("------------------------------------------------");
                System.out.format("%7s | %12s", plateNumber, make + "\n");
                System.out.println("------------------------------------------------");
            }


            sql = "SELECT * FROM vehicle_details where availability = 'vacant'";
            ResultSet rs1 = st.executeQuery(sql);
            while (rs1.next()) {
                String plateNumber2 = rs1.getString("plateNumber");
                String make2 = rs1.getString("vehicle_type");
                System.out.println("------------------------------------------------");
                System.out.format("%7s | %13s", plateNumber2, make2 + "\n");
                System.out.println("------------------------------------------------");
            }

            addBookingDataToTables(year,month,day,yearD,monthD,dayD);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //getlistDates
    private void popularDatas(LocalDate pickupdate, LocalDate dropOffDate, List<LocalDate> datas) {
        if (!pickupdate.plusDays(1).isAfter(dropOffDate)) {
            popularDatas(pickupdate.plusDays(1), dropOffDate, datas);
        }
        datas.add(pickupdate);
    }

    //edit motorbike details
    private void editMotorBike(String pn) {
        Connection conn = obj_DBConnection.get_connection();
        Statement st = null;
        try {
            System.out.println("Enter details of the Motor Bike with plate number "+pn);
            System.out.println("Enter color of the Vehicle");
            Scanner sc = new Scanner(System.in);
            String color = sc.next();

            //get no of seats
            System.out.println("Enter has sidecar or not, enter 'y' for yes\n" +
                    "'n' for no");
            Scanner sc2 = new Scanner(System.in);
            String hasSideCar = sc2.next();

            // create the java mysql update preparedstatement
            String query = "update MotorBike_Details set hasSideCar = ? where plateNumber = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString   (1, hasSideCar);
            preparedStmt.setString(2, pn);
            preparedStmt.executeUpdate();

            String query2 = "update Vehicle_Details set color = ? where plateNumber = ?";
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
            preparedStmt2.setString   (1, color);
            preparedStmt2.setString(2, pn);

            // execute the java preparedstatement
            preparedStmt2.executeUpdate();
            System.out.println("Updated successfully");
            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    //edit car details
    public void editCar(String number) {
        Connection conn = obj_DBConnection.get_connection();
        try {
            System.out.println("Enter details of the car with plate number "+number);
            System.out.println("Enter color of the Car");
            String color = input_1.next();

            //get no of seats
            System.out.println("Enter number of seats");
            validateInteger();
            int noOfSeats = input_1.nextInt();

            // create the java mysql update preparedstatement
            String query = "update Car_Details set noOfSeats = ? where plateNumber = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, noOfSeats);
            preparedStmt.setString(2, number);
            preparedStmt.executeUpdate();

            String query2 = "update Vehicle_Details set color = ? where plateNumber = ?";
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
            preparedStmt2.setString   (1, color);
            preparedStmt2.setString(2, number);
            preparedStmt2.executeUpdate();
            System.out.println("Updated successfully");
            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
    //get the list of booked vehicles
    public void prinBookedVehicleDetails(){
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = (Statement) conn.createStatement();
            String printSql = "SELECT * FROM BookedVehicle_Details";
            rs = st.executeQuery(printSql);

            while (rs.next()) {
                //Retrieve by column name
                String plateNumber = rs.getString("plateNumber");
                String typeOfVehicle = rs.getString("vehicle_type");
                String pDate = rs.getString("pickupDate");
                String dDate = rs.getString("dropOffDate");
                String fname = rs.getString("firstName");
                String lName = rs.getString("lastName");

                //Display values
                System.out.println("---------------------------------------------------------------------------------");
                System.out.format("%7s | %12s | %7s | %12s | %10s| %12s  ", plateNumber, typeOfVehicle, pDate, dDate, fname, lName + "\n");
                System.out.println("---------------------------------------------------------------------------------");
            }
        } catch (Exception se) {
            System.err.println(se);
        }
    }

    //book a vehicle
    public void addBookingDataToTables(int py,int pm, int pd,int dy,int dm,int dd){
        Connection conn = obj_DBConnection.get_connection();
        PreparedStatement ps = null;
        Statement st = null;
        LocalDate  pickupdate_ = LocalDate.of(py,pm,pd);
        LocalDate dropOffDate_ = LocalDate.of(dy,dm,dd);
        java.sql.Date sqlPickup = java.sql.Date.valueOf(pickupdate_);
        java.sql.Date sqlDrop = java.sql.Date.valueOf(dropOffDate_);

        try {
            st = (Statement) conn.createStatement();
            System.out.println("If you still want to Book a vehicle Enter 1\n else Enter 2");
            validateInteger();
            int bookingOption = input_1.nextInt();
            switch (bookingOption){
                case 1:
                    System.out.println("Enter plate Number of the vehicle from above");
                    String vehiclePN =input_1.next();
                    String sql = "SELECT * FROM Vehicle_details";
                            ResultSet rs =st.executeQuery(sql);
                            while (rs.next()){
                                String fromSqlTablePN =rs.getString("vehicle_type");
                                String plateNumberFromTable = rs.getString("plateNumber");
                                if(fromSqlTablePN.equals("Car") & plateNumberFromTable.equals(vehiclePN)){
                            System.out.println("Enter first Name");
                            String fname = input_1.next();
                            System.out.println("Enter last Name");
                            String lname = input_1.next();
                            String innerSql = "Insert into BookedVehicle_Details (plateNumber, pickupDate, dropOffDate, vehicle_type, firstName, LastName) " +
                                    "values (?,?,?,?,?,?)";
                            ps = conn.prepareStatement(innerSql);
                            ps.setString(1,vehiclePN);
                            ps.setDate(2,sqlPickup);
                            ps.setDate(3,sqlDrop);
                            ps.setString(4,"Car");
                            ps.setString(5,fname);
                            ps.setString(6,lname);
                            ps.executeUpdate();
                            System.out.println("Booking Successful");

                            String query2 = "update Vehicle_Details set availability = ? where plateNumber = ?";
                            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                            preparedStmt2.setString(1,"Booked");
                            preparedStmt2.setString   (2, vehiclePN);
                            preparedStmt2.executeUpdate();
                            String s1 = "PlateNB :";
                            String s2 = "Booked By : ";
                            String s3 = "From :";
                            String s4 = "To :";

                            String all = s1 + "  " + plateNumberFromTable + "     " + s3+" "+pickupdate_+"  "+s4 + "  "+s2+fname+" "+lname+"\n";
                            writeIntoFile(all);
                            break;

                        }else if(fromSqlTablePN.equals("MotorBike") & plateNumberFromTable.equals(vehiclePN)){
                            System.out.println("Enter first Name");
                            String fname = input_1.next();
                            System.out.println("Enter last Name");
                            String lname = input_1.next();
                            String innerSql = "Insert into BookedVehicle_Details (plateNumber, pickupDate, dropOffDate, vehicle_type, firstName, LastName) " +
                                    "values (?,?,?,?,?,?)";
                            ps = conn.prepareStatement(innerSql);
                            ps.setString(1,vehiclePN);
                            ps.setDate(2,sqlPickup);
                            ps.setDate(3,sqlDrop);
                            ps.setString(4,"MotorBike");
                            ps.setString(5,fname);
                            ps.setString(6,lname);
                            ps.executeUpdate();
                            System.out.println("Booking Successful");

                            String query2 = "update Vehicle_Details set availability = ? where plateNumber = ?";
                            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                            preparedStmt2.setString(1,"Booked");
                            preparedStmt2.setString   (2, vehiclePN);
                            preparedStmt2.executeUpdate();
                            String s1 = "PlateNB :";
                            String s2 = "Booked By : ";
                            String s3 = "From :";
                            String s4 = "To :";

                            String all = s1 + "  " + plateNumberFromTable + "     " + s3+" "+pickupdate_+"  "+s4 +"   "+dropOffDate_+ "  "+s2+fname+" "+lname+"\n";
                            writeIntoFile(all);
                            break;
                        }
                    }
                    prinBookedVehicleDetails();
            }
        }catch (Exception e){
            System.err.println(e);
        }
    }

    //write in file
    public void writeIntoFile(String details){
        String str = details;
        BufferedWriter writer = null;
        //Relative path didnt work for this so had to give the absolute path
        File file =new File("D:/IIT/JavaProjects/Java_Test_01/Report.txt");
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(' ');
            writer.append(str);
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("File is not Exsisting ");
            System.err.println(e);
        }
        catch (IOException e) {
            System.out.println("Error IO");
            System.out.println(e);
        }

    }
    //generate a report
    public void generateReport() {
        System.out.println("\n---Details---\n");
        try {
            //Relative path didnt work for this so had to give the absolute path
            BufferedReader in = new BufferedReader(new FileReader("D:/IIT/JavaProjects/Java_Test_01/Report.txt"));
            String line;
            while((line = in.readLine()) != null)
            {
                System.out.println(line);
            }
            in.close();
            System.out.println("\n--------------------------------");
        } catch (FileNotFoundException e) {
            System.out.println("File is Not Exsisting");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error IO");
            e.printStackTrace();
        }
    }

    public void findByPrimaryKey(String s) {
        Connection conn = obj_DBConnection.get_connection();
        Statement st = null;

        try {
            st = (Statement) conn.createStatement();
            System.out.println("Fetching records in ascending order...according to the make\n");
            String sql = "SELECT * FROM Vehicle_Details where plateNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,s);
            ps.executeQuery();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String plateNumber = rs.getString("plateNumber");
                String typeOfVehicle = rs.getString("vehicle_type");
                String colour = rs.getString("color");
                String make = rs.getString("vehicle_make");

                System.out.println(plateNumber+"   "+typeOfVehicle+"   "+colour+"   "+make);
            }
        } catch (Exception se) {
            System.err.println(se);
        }
    }
    //comparing two dates
    public boolean compareTwoDates(LocalDate d1,LocalDate d2) throws ParseException {
        if(d1.compareTo(d2) > 0) {
            System.out.println("Date 1 occurs after Date 2, That is not valid");
            return false;
        } else if(d1.compareTo(d2) < 0) {
           // System.out.println("Date 1 occurs before Date 2");
        } else if(d1.compareTo(d2) == 0) {
            System.out.println("Both dates are equal");
        }
        return true;
    }
}
