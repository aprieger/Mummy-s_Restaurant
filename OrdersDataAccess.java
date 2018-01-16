package MummysRestaurant;

import java.sql.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class OrdersDataAccess {
    
    //Date must be passed in this format "DD-MM-YY", double quotes included
    public static ArrayList<JSONObject> getAllOrdersForToday(String deliveryDate) throws SQLException, JSONException{
        try {
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select o.*, p.name, p.description, p.meal_category, " +
                    "po.quantity " +
                    "from orders o, pkgorders po, packages p" + 
                    "where (o.order_id = po.order_id) and " + 
                    "(po.package_id = p.package_id) " +
                    "and o.delivery_date like '" + deliveryDate + "%' "+
                    "group by o.order_id";
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            ArrayList<JSONObject> listOfColumnValuePairs = new ArrayList();
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
                listOfColumnValuePairs.add(columnValuePair);
            }
            
            statement.close();
            conn.close();
            
            return (listOfColumnValuePairs);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    //Will need to implement CASCADE DELETE on the foreign keys via SQL CMD Line
    public static void deleteOrder(int orderID) throws SQLException{
        
        try {
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "delete from orders where order_id = " + orderID;
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query 
            statement.executeQuery(sqlQuery);
            
            statement.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
        
    //Returns an ArrayList of JSONObjects that belong to a specific customer
    //Index out the ArrayList and use .get() with the key as a parameter to retrieve the column value
    //ex: getAllRowsForCustomer(4).get(0).get("ORDER_DATE") NOTE: 4 is the customerID
    public static ArrayList<JSONObject> getCustomerOrdersCustomerID(int customerID) throws SQLException, JSONException{
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
            ArrayList<JSONObject> listOfJsonObjects = new ArrayList();
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
    
    public static ArrayList<JSONObject> getCustomerOrdersFirstName(String firstName) throws SQLException, JSONException{
        try {
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select o.* " + 
                    "from orders o, customer_accounts ca " + 
                    "where (o.customer_id = ca.customer_id) " + 
                    "and (ca.firs_name = " + firstName +")";
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            ArrayList<JSONObject> listOfColumnValuePairs = new ArrayList();
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
                listOfColumnValuePairs.add(columnValuePair);
            }
            
            statement.close();
            conn.close();
            
            return (listOfColumnValuePairs);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static ArrayList<JSONObject> getCustomerOrdersLastName(String lastName) throws SQLException, JSONException{
        try {
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select o.* " + 
                    "from orders o, customer_accounts ca " + 
                    "where (o.customer_id = ca.customer_id) " + 
                    "and (ca.last_name = " + lastName +")";
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            ArrayList<JSONObject> listOfColumnValuePairs = new ArrayList();
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
                listOfColumnValuePairs.add(columnValuePair);
            }
            
            statement.close();
            conn.close();
            
            return (listOfColumnValuePairs);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static ArrayList<JSONObject> getCustomerOrdersFullName(String firstName, String lastName) throws SQLException, JSONException{
        try {
            //query to be used to retrieve all rows from Orders
            String sqlQuery = "select o.* " + 
                    "from orders o, customer_accounts ca " + 
                    "where (o.customer_id = ca.customer_id) " + 
                    "and (ca.first_name = " + firstName +") " +
                    "and (ca.last_name = " + lastName +")";
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            ArrayList<JSONObject> listOfColumnValuePairs = new ArrayList();
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
                listOfColumnValuePairs.add(columnValuePair);
            }
            
            statement.close();
            conn.close();
            
            return (listOfColumnValuePairs);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    //TODO: need to confirm what values are going to be passed into this method
    public static void updateOrdersRow (String columnName, Object columnValue, int orderId) throws SQLException {
        String sqlQuery = "update orders " + 
                "set " + columnName + " = " + columnValue + " " +
                "where order_id = " + orderId;        
        try {
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query
            statement.executeQuery(sqlQuery);
            System.out.println("Order updated!");
        } catch (SQLException se) {
            System.out.println(se);
        }
    } 
    
    //TODO: need to finish this method, actual parameter type missing
    public void insertNewOrdersRow(OrdersModelClass orderObject) throws SQLException{
        
        String sqlQuery = "select * from orders";
         
        try {
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            //TODO: assign actual values from CUSTOMER_ID down
            queryResults.moveToInsertRow();
            queryResults.updateInt("ORDER_ID", orderObject.getOrderID());
            queryResults.updateInt("CUSTOMER_ID", orderObject.getCustomerID());
            queryResults.updateInt("CREDIT_ID", orderObject.getCreditID());
            queryResults.updateInt("PAYMENTMENT_TYPE", orderObject.getPaymentType());
            queryResults.updateDouble("TOTAL_PRICE", orderObject.getTotalPrice());
            queryResults.updateString("STREET", orderObject.getStreet());
            queryResults.updateString("CITY", orderObject.getCity());
            queryResults.updateInt("AREA_CODE", orderObject.getAreaCode());
            queryResults.updateString("PHONE_NUMBER", orderObject.getPhoneNumber());
            queryResults.updateTimestamp("DELIVERY_DATE", orderObject.getDeliveryDate());
            queryResults.updateTimestamp("ORDER_DATE", orderObject.getOrderDate());
            queryResults.updateInt("ORDER_STATUS", orderObject.getOrderStatus());
            queryResults.insertRow();
            
        } catch (SQLException se){
            System.out.println(se);
        }
        
    }
    
    //returns an id during the creation of an orders row
    public int idOrdersGenerator() throws SQLException{
        //stores existing IDs from orders
        int largestId = 0;        
        //SQL query to return all the ids from orders
        String sqlQuery = "select max(order_id) from orders";
        
        try{
            //established connectioned to oracle account through a driver
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
            //established a statement connection
            Statement statement = conn.createStatement();
            //executes the SQL query and returns a ResultSet object
            ResultSet queryResults = statement.executeQuery(sqlQuery);
            
            while(queryResults.next()){
                largestId = queryResults.getInt(1);
            }
            
            return largestId + 1;
            
        } catch (SQLException se) {
            System.out.println(se);
        }
        return 0;
    }
}