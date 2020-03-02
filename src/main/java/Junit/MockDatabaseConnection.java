package Junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MockDatabaseConnection {
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mckoi://localhost/",
                "root",
                "");
    }
}
