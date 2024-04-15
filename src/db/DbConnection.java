package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public Connection createConnection() {
        String url = System.getenv("JSP_DB_URL");
        String username = System.getenv("JSP_DB_USERNAME");
        String password = System.getenv("JSP_DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
