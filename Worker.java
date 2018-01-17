/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONObject;
import app.UI.*;

public class Worker extends Person{
    
    private int employeeId;
    private int isAdmin;
    private int isActive;
    private Login login;
    
public Worker(){
    login = new Login();
}

public Worker(int a){
    this.isAdmin = a;
}
    public Login getLogin() {
        return login;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int user_id) {
        this.employeeId = user_id;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int is_Admin) {
        this.isAdmin = is_Admin;
    }
    
    public static void viewOrders() throws SQLException{
        int orderNumber;
        ArrayList<JSONObject> todaysOrders;
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yy");
        String formattedToday = today.format(formatter);
        System.out.println(formattedToday);
        
        //Date must be passed in this format "DD-MM-YY", double quotes included
        todaysOrders = OrdersDataAccess.getAllOrdersForToday(formattedToday);
        
        //Returns an ArrayList of JSONObjects that belong to a specific customer
        //Index out the ArrayList and use .get() with the key as a parameter to retrieve the column value
        //ex: getAllRowsForCustomer(4).get(0).get("ORDER_DATE")
        System.out.println("Orders for today are:");
        
         try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){ 
            
             System.out.println("Todays Orders ********************");
            for (int i = 0; i < todaysOrders.size(); i++) {
                 //compare date in JSOM Object and get order_id number
                 try(orderNumber = (Integer) todaysOrders.get(i).get("ORDER_ID")){
                 
                try(ResultSet rs = statement.executeQuery("select o.order_id, c.last_name, "
                    +"p.name, p.quanity from orders o join customers on c.customer_id = o.customer_id "
                    +"join pkgorder pk on pk.order_id = o.order_id "
                    +"join packages p on p.package_id = pl.package_id "
                    +"where o.order_id = "+ orderNumber)){
                System.out.println("Order #: "+rs.getString("o.order_id")+ ", Customer name: "
                    +rs.getString("c.last_name")+", Package name: "+rs.getString("p.name")
                    +", Quanity: "+rs.getString("p.quanity"));  
                }
                }
            }        
        }
    }
}

