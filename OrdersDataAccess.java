

import java.sql.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class OrdersDataAccess {
    
    public static JSONObject getAllOrdersForToday(String presentDate) throws SQLException{
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
    
    
    //Returns an ArrayList of JSONObjects that belong to a specific customer
    //Index out the ArrayList and use .get() with the key as a parameter to retrieve the column value
    //ex: getAllRowsForCustomer(4).get(0).get("ORDER_DATE")
    public static ArrayList<JSONObject> getAllRowsForCustomer(int customerID) throws SQLException, JSONException{
        try {

            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select * from orders where customer_id = " + customerID;
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            //Converts Resultset to an ArrayList of Json objects
            ArrayList listOfJsonObjects = new ArrayList<JSONObject>();
            while(queryResults.next()) {
                int totalColumns = queryResults.getMetaData().getColumnCount();
                JSONObject columnValuePair = new JSONObject();
                //At every row, the column and its value are mapped...
                for (int i = 0; i < totalColumns; i++) {
                    //..and then put into the JSONObject
                    columnValuePair.put(queryResults.getMetaData().getColumnName(i+1),
                            queryResults.getObject(i+1));
                }
                //Which is added to this ArrayList to be returned
                listOfJsonObjects.add(columnValuePair);
            }
            
            
            statement.close();
            conn.close();
            
            return (listOfJsonObjects);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    
    public static void main(String[] args) {
        
        try{
            System.out.println(getAllRowsForCustomer(4).get(0).get("ORDER_DATE"));
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (JSONException ex) {
            System.out.println(ex);
        }
        
       
    }
     
}
