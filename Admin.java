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
import java.util.Scanner;

public class Admin extends Worker {
    
    public void enterNewWorker()throws SQLException{  
        Worker newWorker = new Worker();
        boolean loop;
        String message, tempPhoneNumber, tempUsername;
        Scanner scan = new Scanner(System.in);
        
        newWorker.setEmployeeId(getNextPrimaryKey());
    
        System.out.print("Enter the first name of the employee: ");
        newWorker.setFirstName(scan.nextLine());
        
        System.out.print("Enter the last name of the employee: ");
        newWorker.setLastName(scan.nextLine());
        
        do {
            System.out.print("Enter the phone number of the employee: ");
            tempPhoneNumber = scan.nextLine();
            loop = verifyPhoneNumber(tempPhoneNumber);
        }while(loop);
        newWorker.setPhoneNumber(tempPhoneNumber);
        
        do {
            System.out.print("Enter username for the employee: ");
            tempUsername = scan.nextLine();
            loop = verifyUserId(tempUsername);
        }while(loop);
        newWorker.getLogin().setUserId(tempUsername);
        
        System.out.print("Enter password for the employee: ");
        newWorker.getLogin().setPassword(scan.nextLine());
        
        message = "Enter if the employee has administration access (y or n): ";
        newWorker.setIsAdmin(yesNoInput(message));
        
        message = "Enter if the employee is active (y or n): ";
        newWorker.setIsActive(yesNoInput(message));
        
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){ 
            
            statement.executeUpdate("INSERT INTO WORKERS VALUES ("+ getNextPrimaryKey() 
                    +", '" + newWorker.getFirstName() + "', '"+ newWorker.getLastName() 
                    +"', '" + newWorker.getLogin().getUserId() + "', '"+ newWorker.getLogin().getPassword() 
                    + "', '"+ newWorker.getPhoneNumber() +"', "+ newWorker.getIsAdmin() +", "
                    + newWorker.getIsActive() +")");
            
            System.out.println("The record has been created.");
        }
    }
    
    public void deleteWorker() throws SQLException{  
        int result;
        String message;
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter the first name of the employee: ");
        String firstName = scan.nextLine();
        
        System.out.print("Enter the last name of the employee: ");
        String lastName = scan.nextLine();
        
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){ 
            
            try (ResultSet rs = statement.executeQuery("select employee_id, first_name, last_name from workers where first_name= '"
                    + firstName  +"' and last_name = '"+ lastName +"'")){
                if (!rs.isBeforeFirst() )     
                    System.out.println("No record found."); 
                while(rs.next()){
                    message = ("Delete "+ rs.getString("first_name")+" "+ rs.getString("last_name") +"(y or n)?");
                    result = new Admin().yesNoInput(message);
                
                    if(result == 1){
                        statement.executeUpdate("delete from workers where employee_id = '"+ rs.getString("employee_id") +"'");
                        System.out.println("Worker record deleted.");
                    }
                    else {
                        System.out.println("Worker record unchanged.");
                    }
                }
            }
        }  
    }
    
    public void setWorkerStatus() throws SQLException{  // completed and tested
        int result, newResult;
        String message;
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter the employee id: ");
        String employeeId = scan.nextLine();
        
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){ 
            
            try (ResultSet rs = statement.executeQuery("select employee_id, first_name,"
                    +"last_name, is_Active from workers where employee_id = '"+ employeeId  +"'")){
                while(rs.next()){
                    result = Integer.parseInt(rs.getString("is_Active"));
                    
                    System.out.println("Current status for "+ rs.getString("first_name")+ " "
                            +rs.getString("last_name")+ " is "+ ((result == 1) ? "Active": "Inactive" ));
                    message = "Change user status y or n?";
                    result = new Admin().yesNoInput(message);
                    
                    if (result == 1) {
                       newResult = ((Integer.parseInt(rs.getString("is_Active")))== 1) ? 0: 1;
                       statement.executeUpdate("update workers set is_Active = '"+ newResult +"' where employee_id = '"+ employeeId +"'");
                       System.out.println("Status changed.");
                    }            
                }
            }   
        }
    }
        
    public void searchForWorker() throws SQLException {  
        Scanner scan = new Scanner(System.in);
    
        System.out.print("Enter the last name of the employee: ");
        String lastName = scan.nextLine();
        
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){ 
        
            try (ResultSet rs = statement.executeQuery("select * from workers where last_name='"
                    + lastName +"'");){
                if (!rs.isBeforeFirst() )     
                    System.out.println("No record found."); 
                while(rs.next())  
                    System.out.println("Employee ID: " + rs.getString("employee_id") +", Name: " +rs.getString("first_name")+ " " + rs.getString("last_name") + ", Phone Number: " + rs.getString("phone_number"));
            }
        }
    }
    
    public int yesNoInput(String str) {  
        String userYN;
        Scanner s = new Scanner(System.in);
        
        while(true) {
            System.out.println(str);
            userYN = s.nextLine();
            
            if(userYN.equalsIgnoreCase("y"))
                return 1;
            else if (userYN.equalsIgnoreCase("n"))
                return 0;
            else
                System.out.println("Please enter y or n");
        } 
    }
    
    public boolean verifyPhoneNumber(String testPhoneNumber) {  
        String regexStr = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
        if(testPhoneNumber.matches(regexStr)) {
            return false;
        }
        else {
            System.out.println("Not a vaild phone number");
            System.out.println("Please use either ########## or ###-###-#### format.");
            return true;
        }
    }
    
    public boolean verifyUserId(String testUserID) throws SQLException{
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
                Statement statement = con.createStatement();){
            try (ResultSet rs = statement.executeQuery("SELECT user_id from workers where user_id = '"+ testUserID +"'")){
                while(rs.next()){
                    if (rs.getString("user_id").equals(testUserID)){
                        System.out.println("That username is currently in use.");
                        return true;
                    }
                }
                System.out.println("That username is available ");
                return false;
            }
        }
    }
     
    public static int getNextPrimaryKey() throws SQLException{ 
        int max = 0;
        
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){ 
            try (ResultSet rs = statement.executeQuery("SELECT MAX(USER_ID) FROM WORKERS")){
                while(rs.next()) 
                    max = Integer.parseInt(rs.getString("max(user_id)"));
                return max;
            }
        }
    }
}


