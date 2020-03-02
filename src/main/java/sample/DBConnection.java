package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    //connection
    public Connection get_connection(){
        Connection connection=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalstore","root","");
        }catch (Exception e){
            System.err.println(e);
        }
        return connection;
    }

}
