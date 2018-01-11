package MummysRestaurant;

import java.sql.*;
import java.util.*;
import org.json.JSONObject;

public class OrdersDataAccess {
    
    static JSONObject getAllRows() throws SQLException{
        try {
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select * from orders";
            
            //JSon object that will be returned
            JSONObject jsonAllRows = new JSONObject();
            
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = conn.createStatement().executeQuery(sqlQuery);
            
            while(queryResults.next()) {
                //todo: convert ResultSet to JSon object
            }
            return jsonAllRows;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
