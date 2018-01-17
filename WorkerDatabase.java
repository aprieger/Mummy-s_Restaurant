/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkerDatabase {

    
    public static void createWorkerTable() { // Complete
        try {
            Connection con = DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","hr","hr"); 
            Statement statement = con.createStatement(); 
            String tableOne = "Create table Workers " +
                "(employee_id integer not null primary key, " +
                "first_name varchar(250), " +
                "last_name varchar(250), " +
                "user_id varchar(50), " +
                "password varchar(50), " +
                "phone_number varchar(15), " +
                "is_admin integer, " +
                "is_active integer )";
       
            statement.execute(tableOne);
            System.out.println("Table created!"); 
        
            statement.close();
            con.close();
        }
        catch(SQLException e){
            System.out.println("ERROR");
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
     
    