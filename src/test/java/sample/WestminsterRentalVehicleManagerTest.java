package sample;

import com.mysql.jdbc.PreparedStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class WestminsterRentalVehicleManagerTest {

  /*  @Mock
    private DataSource ds;
    @Mock
    private Connection c;
    @Mock
    private PreparedStatement stmt;
    @Mock
    private ResultSet rs;
*/

    Car tempCar;
    MotorBike tempBike;


    @BeforeEach
    void setUp() throws Exception{

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateInteger() {
    }

    @Test
    void showFreeSpacesLeft() {
    }

    @Test
    void addVehicle() {

        Car car = new Car("White","Honda","Vacant","AD1234",2,2,"Potasssium");

    }

    @Test
    void addCar() {

    }

    @Test
    void addMotorBike() {
    }

    @Test
    void deleteVehicle() {
    }

    @Test
    void printListOfVehicles() {
    }

    @Test
    void edit() {
    }

    @Test
    void bookVehicle() {
    }

    @Test
    void editCar() {
    }

    @Test
    void prinBookedVehicleDetails() {
    }

    @Test
    void addBookingDataToTables() {
    }

    @Test
    void writeIntoFile() {
    }

    @Test
    void generateReport() {
    }

    @Test
    void compareTwoDates() {
    }
}