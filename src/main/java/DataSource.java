import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    public Connection getConnection() {
        try {
            String url = System.getenv("JDBC_URL");
            String username = System.getenv("name");
            String password = System.getenv("password");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(Connection connection){
        if (connection != null) {
            try{
                connection.close();
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}
