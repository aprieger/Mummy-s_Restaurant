package MummysRestaurant;

import java.sql.*;
import java.util.*;
import org.json.JSONObject;

public class OrdersDataAccess {
    
    private JSONObject getAllOrdersForToday(String presentDate) throws SQLException{
        try {
            JSONObject jsonAllRows = new JSONObject();
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select * from orders where DELIVERY_DATE = " + presentDate;
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            while(queryResults.next()) {
                //todo: convert ResultSet to JSon object
            }
            
            statement.close();
            conn.close();
            
            return jsonAllRows;
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    private void deleteOrder(int order_id){
        
    }
    
    
    
    private JSONObject getAllRowsForCustomer(int customer_id) throws SQLException{
        try {
            //JSon object that will be returned
            JSONObject jsonAllRows = new JSONObject();
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select * from orders where CUSTOMER_ID = " + Integer.toString(customer_id);
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            while(queryResults.next()) {
                //todo: convert ResultSet to JSon object
            }
            
            statement.close();
            conn.close();
            
            return jsonAllRows;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
     
}
