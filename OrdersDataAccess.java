package MummysRestaurant;

import java.sql.*;
import java.util.*;

public class OrdersDataAccess {
    
    static ResultSet getAllRows() throws SQLException{
        
        try {
            String sqlQuery = "select * from orders";
            
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            
            Statement statement = conn.createStatement();
            
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            return queryResults;
            
        } catch (SQLException ex) {
            
            System.out.println(ex);
        }
        return null;
    }
}
