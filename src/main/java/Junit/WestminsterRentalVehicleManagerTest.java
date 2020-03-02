package Junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import sample.Car;
import sample.DBConnection;
import sample.MotorBike;
import sample.WestminsterRentalVehicleManager;

import javax.sql.DataSource;
import java.security.PublicKey;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;


public class WestminsterRentalVehicleManagerTest {
    @Mock
    private DataSource ds;
    @Mock
    private Connection c;
    @Mock
    private PreparedStatement stmt;
    @Mock
    private ResultSet rs;

    private Car tempCar;
    private MotorBike tempMotorBike;
    WestminsterRentalVehicleManager wrm =new  WestminsterRentalVehicleManager();
 /*   @BeforeAll
   public Connection get_connection(){
      Connection connection=null;
      try{
        Class.forName("com.mysql.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalstore","root","");
      }catch (Exception e){
        System.err.println(e);
      }
      return connection;
    }*/


    @Test
    public void testAddCar(){
      DBConnection obj_DBConnection = new DBConnection();
      Connection conn = obj_DBConnection.get_connection();
      tempCar = new Car("Green","Honda","Vacant","FK7778",5,4,"Boron");
      wrm.addCar(tempCar);
      try{
        Statement st = conn.createStatement();
        String sql = "Select * from Vehicle_details where plateNumber = 'FK7778'";
        ResultSet rs = st.executeQuery(sql);
         while (rs.next()){
             assertEquals("FK7778", rs.getString("plateNumber"),"Plate Numbers Not matched Data Input Failed");
         }
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

    @Test
  public void testAddMotorBike(){
      DBConnection obj_DBConnection = new DBConnection();
      Connection conn = obj_DBConnection.get_connection();
      tempMotorBike = new MotorBike("Blue","Deo","Vacant","XC1111","Y","Scooter");
      wrm.addMotorBike(tempMotorBike);
      try{
        Statement st = conn.createStatement();
        String sql = "Select plateNumber from Vehicle_details where plateNumber= 'XC1111'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
          assertEquals("XC1111",rs.getString("plateNumber"),"Expected and actual are not Equal,Test Failed");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }catch (Exception e){
        System.err.println(e);
      }

    }

    @Test
  public void testDel_Count(){
      DBConnection obj_DBConnection = new DBConnection();
      Connection conn = obj_DBConnection.get_connection();
      int rowCount=-1;
      int rowCountAfterDelete = -1;
      try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Vehicle_Details");
        // get the number of rows from the result set
        rs.next();
        rowCount = rs.getInt(1);
        System.out.println(rowCount);

        wrm.deleteVehicle("AS1234");
        ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM Vehicle_Details");
        // get the number of rows from the result set
        rs2.next();
        rowCountAfterDelete = rs2.getInt(1);
        System.out.println(rowCountAfterDelete);

        assertEquals(rowCount,rowCountAfterDelete+1);

      } catch (Exception e) {
        System.err.println("Got an exception!");
      }
    }

  @Test
  public void testDelete() throws SQLException {
    DBConnection obj_DBConnection = new DBConnection();
    Connection conn = obj_DBConnection.get_connection();
    Statement st =conn.createStatement();
    tempMotorBike = new MotorBike("White","Deo","Vacant","XC6889","Y","Pulzer");
    wrm.addMotorBike(tempMotorBike);
    wrm.deleteVehicle("XC6889");

    String sql = "Select * from Vehicle_details where plateNumber='XC6889'";
    ResultSet rs = st.executeQuery(sql);
    while (rs.next()){
      System.out.println("pass");
      assertNull("XC6889","Object delete not successful");
    }
  }



}
