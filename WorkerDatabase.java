/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class WorkerDatabase {
    ArrayList<Worker> workers;
    
    public static void main(String[] args){
        WorkerDatabase wd = new WorkerDatabase();
        System.out.println(getNextPrimaryKey());
//        wd.resetPassword();
    }
    
    public void createWorkerTable() { // Complete
        try {
            Connection con = DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","hr","hr"); 
            Statement statement = con.createStatement(); 
            String tableOne = "Create table Workers " +
                "(user_id integer not null primary key, " +
                "first_name varchar(250), " +
                "last_name varchar(250), " +
                "user_name varchar(50), " +
                "password varchar(50), " +
                "phone_number integer, " +
                "is_admin integer not null, " +
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
    
    public void insertRecords() { // Complete
        
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO workers VALUES (1001, 'Fred', 'Smith', 'google', 'google1', 9048180063, 1 )");
            statement.executeUpdate("INSERT INTO workers VALUES (1002, 'Bill', 'Smith', 'yahoo', '$Yahoo2', 6028180063, 0 )");
            statement.executeUpdate("INSERT INTO workers VALUES (1003, 'Shelley', 'Collins', '#amazon', '9AmAzon', 6238180063, 0 )");
            statement.executeUpdate("INSERT INTO workers VALUES (1004, 'Becky', 'Amear', 'microsoft', 'Microsoft7', 4808180063, 1 )");
            System.out.println("Values inserted into table!");
            
            statement.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }   
    }
    
    public void resetPassword() { // Complete
        Scanner scan = new Scanner(System.in);
        boolean resultHit = false;
    
        System.out.print("Enter the last name of the employee: ");
        String tempName = scan.nextLine();
        System.out.println(tempName);
        
        System.out.print("Enter the phone number of the employee: ");
        long tempPhoneNumber = scan.nextLong(); 
        scan.nextLine();
        System.out.println(tempPhoneNumber);
        
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select last_name, phone_number from workers where last_name= '" + tempName +"' and phone_number= "+ tempPhoneNumber );
            while(rs.next()){
                System.out.println(rs.getString("last_name") + "  "+ rs.getString("phone_number"));
            }
            if (rs!=null){
                System.out.print("Enter a new password: ");
                String tempPassword = scan.nextLine();
             
                statement.executeUpdate("update workers set password='"+ tempPassword +"' where last_name= '"+ tempName +"' and phone_number= " + tempPhoneNumber);
        
            } else {
                System.out.println("The record could not be found.");
            }
            statement.close();
            con.close();
        }
        catch(SQLException s){
            System.out.println(s.getMessage());
        }
        
    }
    
//    public void forgotUserName() {
//        Scanner scan = new Scanner(System.in);
//    
//        System.out.print("Enter the last name of the employee: ");
//        String tempName = (scan.nextLine());
//        
//        System.out.print("Enter the phone number of the employee: ");
//        int tempPhoneNumber = (Integer.parseInt(scan.nextLine()));
//        
//        try {
//            Connection con = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
//            Statement statement = con.createStatement(); 
//        
//        if(tempName.equals(statement.executeQuery("select user_name from worker where user_name= '"+ tempName +"'" ))){
//            if(tempPhoneNumber == (int) statement.executeQuery("select phone_number from worker where phone_number= "+ tempPhoneNumber )){
//                System.out.println("Your username is "+ );
//            }else
//                System.out.println("Not the correct phone number for that worker.");
//        }else
//            System.out.println("No worker found with that last name.");    
//        }
//        catch(SQLException e){
//            
//        }
//    }
    
            
    public void enterNewWorker(){  //complete
        Worker newWorker = new Worker();
        Scanner scan = new Scanner(System.in);
        
        newWorker.setUserId(getNextPrimaryKey());
    
        System.out.print("Enter the first name of the employee: ");
        newWorker.setFirstName(scan.nextLine());
        
        System.out.print("Enter the last name of the employee: ");
        newWorker.setLastName(scan.nextLine());
        
        System.out.print("Enter the phone number of the employee: ");
        newWorker.setPhoneNumber(Integer.parseInt(scan.nextLine()));
        
        System.out.print("Enter username for the employee: ");
        newWorker.setUserName(scan.nextLine());
        
        System.out.print("Enter password for the employee: ");
        newWorker.setPassword(scan.nextLine());
        
        System.out.print("Enter if the employee has administration access (y or n): ");
        String temp = scan.nextLine();
        if(temp.equalsIgnoreCase("y"))
            newWorker.setIsAdmin(1);
        else
            newWorker.setIsAdmin(0);
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement(); 
            
             statement.executeUpdate("INSERT INTO tableOne " + "VALUES ('"+ newWorker.getUserId() +"', '" + newWorker.getFirstName() + "', '"+ newWorker.getLastName() +"', '" + newWorker.getPhoneNumber() +"', '"+ newWorker.getUserName() +"', '"+ newWorker.getUserName() +"')");
            
            statement.close();
            con.close();
            newWorker = null;
        }
        catch (Exception e) {
            
        }
    }
    
//    public void deleteWorker(){
//        Scanner scan = new Scanner(System.in);
//        
//        System.out.print("Enter the first name of the employee: ");
//        String firstName = (scan.nextLine());
//        
//        System.out.print("Enter the last name of the employee: ");
//        String lastName = scan.nextLine();
//        
//        try {
//            Connection con = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
//            Statement statement = con.createStatement(); 
//            
//            ResultSet rs = statement.executeQuery("select first_name||' '|| last_name from workers where first_name='"+ firstName  +"' and last_name ="+ lastName);
//            
//             
//            System.out.println("Delete "+ rs +"(y or n)?");
//            String result = (scan.nextLine());
//            if (result == "y")
//                statement
//             
//            statement.close();
//            con.close();
//        }
//        catch (Exception e) {
//            
//        }  
//    }
    
    public static int getNextPrimaryKey(){ // Complete
        int max = 0;
        
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement(); 
            ResultSet rs = statement.executeQuery("SELECT MAX(USER_ID) FROM WORKERS");
            while(rs.next()) 
                max = Integer.parseInt(rs.getString("max(user_id)"));
            System.out.println(max);
            statement.close();
            con.close();
            return (max + 1);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
//    public void numberOfWorkers(){
//        try {
//            Connection con = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
//            Statement statement = con.createStatement(); 
//            System.out.println("Number of employees is " + statement.executeQuery("select count(*) from employees"));
//            statement.close();
//            con.close();
//        }
//        catch (Exception e) {
//            
//        }
//    }
        
    public void searchWorkers() {  //complete
        Scanner scan = new Scanner(System.in);
    
        System.out.print("Enter the last name of the employee: ");
        String lastName = scan.nextLine();
        
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement(); 
        
            ResultSet rs = statement.executeQuery("select * from workers where last_name='"+ lastName +"'");  
                while(rs.next())  
                    System.out.println("User ID: " + rs.getString("user_id") +", Name: " +rs.getString("first_name")+ " " + rs.getString("last_name") + ", Phone Number: " + rs.getString("phone_number"));
                
            statement.close();
            con.close();
        }catch (SQLException e){
            System.out.println("ERROR: "+ e.getMessage());
        }
    }
}
