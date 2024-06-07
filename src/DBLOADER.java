
import java.sql.*;

public class DBLOADER {

    public static ResultSet executeStatement(String query) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded successfully");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vod", "root", "system");
        System.out.println("Connection established");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        System.out.println("Statement created");
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("ResultSet created");
        return rs;
    }

}
