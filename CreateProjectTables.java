/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateProjectTables {
    
    
    public static void main(String[] args) {
        try{
            createTables();
        }
        catch(SQLException e) {
            
        }
    }
    
    public static void createTables() throws SQLException{ 
        String temp;
        try (Connection con = DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","hr","hr"); 
            Statement statement = con.createStatement()){ 
            
            statement.executeUpdate("drop table pkgorders purge");
            statement.executeUpdate("drop table serviceArea purge");
            statement.executeUpdate("drop table packages purge");
            statement.executeUpdate("drop table orders purge");
            statement.executeUpdate("drop table credit_cards purge");
            statement.executeUpdate("drop table customer_accounts purge");
            statement.executeUpdate("drop table workers purge");
            
            
            temp = "workers";
            String workers = "Create table Workers " +
                "(employee_id int not null primary key, " +
                "first_name varchar2(50), " +
                "last_name varchar2(75), " +
                "user_id varchar2(20), " +
                "password varchar2(20), " +
                "phone_number varchar2(15), " +
                "is_admin int, " +
                "is_active int )";
       
            statement.execute(workers);
            System.out.println("Table "+ temp +" created!"); 
            
            temp = "customers";
            String customers = "Create table customer_accounts " +
                "(customer_id int not null primary key, " +
                "first_name varchar2(25), " +
                "last_name varchar2(50), " +
                "street varchar2(100), " +    
                "city varchar2(75), " +    
                "area_code int, " +  
                "phone_number varchar2(12), " +    
                "username varchar2(25), " +
                "password varchar2(25), " +
                "enabled int )";
       
            statement.execute(customers);
            System.out.println("Table "+ temp +" created!"); 
            
            temp = "credit cards";
            String creditCards = "Create table credit_cards " +
                "(credit_id int not null primary key, " +
                "customer_id int references customer_accounts (customer_id), " +
                "card_number int, " +
                "brand varchar2(255), " +
                "security_number int, " +
                "experation_date int, " +
                "name_on_card varchar2(255), " +
                "street varchar2(255), " +
                "city varchar2(255), " +
                "area_code int)";
       
            statement.execute(creditCards);
            System.out.println("Table "+ temp +" created!"); 
            
            temp = "orders";
            String orders = "Create table orders " +
                "(order_id int not null primary key, " +
                "customer_id int references customer_accounts (customer_id), " +
                "credit_id int references credit_cards (credit_id), " +
                "payment_type int, " +
                "total_price numeric(20,2), " +
                "street varchar2(255), " +
                "city varchar2(255), " +
                "area_code int, " +
                "phone_number varchar2(30), " +
                "delivery_date timestamp, " +
                "order_date timestamp, "+
                "order_status int)";
            
            statement.execute(orders);
            System.out.println("Table "+ temp +" created!"); 
            
            temp = "packages";
            String packages = "Create table packages " +
                "(package_id int not null primary key, " +
                "name varchar2(50), " +
                "description varchar2(1000), " +
                "meal_category number(2), " +
                "image_source varchar2(100), " +
                "price float(126), " +
                "is_special number(2), " +
                "meal_type number(2))";
            
            statement.execute(packages);
            System.out.println("Table "+ temp +" created!"); 
            
            temp = "service ares";
            String serviceArea = "Create table serviceareas " +
                "(area_id int not null primary key, " +
                "package_id int references packages (package_id), " +
                "name varchar2(50), " +
                "area_code int, " +
                "tax_rate float(126))";
            
            statement.execute(serviceArea);
            System.out.println("Table "+ temp +" created!"); 
            
            temp = "package orders";
            String pkgorders = "Create table pkgorders " +
                "(pkg_order_id int not null primary key, " +
                "order_id int references orders (order_id), "  +  
                "package_id int references packages (package_id), " +
                "customer_id int references customer_accounts (customer_id), " +
                "credit_id int references credit_cards (credit_id), " +
                "price_per_pkg float(126), " +
                "quanity number(5), " +
                "is_open number(2))";
            
            statement.execute(pkgorders);
            System.out.println("Table "+ temp +" created!"); 
        }
    }
    
    public static void insertRecords() { // Complete
        
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO workers VALUES (1001, 'Fred', 'Smith', 'google', 'google1', '9048180063', 1, 1 )");
            statement.executeUpdate("INSERT INTO workers VALUES (1002, 'Bill', 'Smith', 'yahoo', '$Yahoo2', '6028180063', 0, 1 )");
            statement.executeUpdate("INSERT INTO workers VALUES (1003, 'Shelley', 'Collins', '#amazon', '9AmAzon', '6238180063', 0, 1 )");
            statement.executeUpdate("INSERT INTO workers VALUES (1004, 'Becky', 'Amear', 'microsoft', 'Microsoft7', '4808180063', 1, 1 )");
            System.out.println("Values inserted into table!");
            
            statement.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }   
    }
}
     
    