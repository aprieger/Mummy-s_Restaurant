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
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *
 * @author syntel
 */
public class Login {
    String userId;
    String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static Worker systemLogin() throws SQLException {
        Scanner scan = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){
        
            System.out.print("Enter your username: ");
            String inputUserame = scan.nextLine();
        
            System.out.print("Enter your password: ");
            String inputPassword = scan.nextLine();
            
            try(ResultSet rs = statement.executeQuery("select user_id, password, is_Admin "
                    + "from workers where user_id = '"+ inputUserame +"' and password = '"+ inputPassword +"'")){
                if (!rs.isBeforeFirst() )     
                    System.out.println("The username or password was incorrect.");
                while (rs.next()) {
                        System.out.println("Login successful");
                        if (rs.getInt("is_Admin")==1) {
                            return new Admin(Integer.parseInt(rs.getString("is_Admin")));
                        }
                        else {
                            return new Worker(Integer.parseInt(rs.getString("is_Admin")));
                        }
                    }
                }
                return null;
            }
        }
    
    
    public static void resetPassword() throws SQLException { 
        Scanner scan = new Scanner(System.in);
        String message;
    
        System.out.println("Enter the last name of the employee: ");
        String lastName = scan.nextLine();
        
        message = "Enter the employee id of the employee: ";
        int empId = testInts(scan, message);
        
        try (Connection con = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){
            
            try (ResultSet rs = statement.executeQuery("select employee_id, last_name from workers " 
                    +"where last_name = '" + lastName +"' and employee_id = "+ empId )){
                if (!rs.isBeforeFirst())     
                    System.out.println("The last name or employee id was incorrect.");
                else {
                    while(rs.next()){  
                    
                        System.out.println("Enter a new password: ");
                        String tempPassword = scan.nextLine();
                        statement.executeUpdate("update workers set password = '"+ tempPassword +"' where employee_id = " + empId);
                        System.out.println("Your password has been changed.");
                    }
                }     
            }   
        }
    }
    
    public static void forgotUserName() throws SQLException {
        Scanner scan = new Scanner(System.in);
        String message;
    
        System.out.print("Enter the last name of the employee: ");
        String lastName = scan.nextLine();
        
        message = "Enter the employee id of the employee: ";
        int empId = testInts(scan, message);
        
         try (Connection con = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){
            
            try (ResultSet rs = statement.executeQuery("select employee_id, last_name, user_id from workers " 
                    +"where last_name = '" + lastName +"' and employee_id = "+ empId )){
                if (!rs.isBeforeFirst())     
                    System.out.println("The last name or employee id was incorrect.");
                else {
                    while(rs.next()) {
                        System.out.println("Your username is "+ rs.getString("user_id"));
                    }
                }
            }
        }
    }
    
    public static int testInts(Scanner s, String m){
        boolean loop;
        int intInput = 0; 
    
        do {
            System.out.println(m);
            try {
                intInput = Integer.parseInt(s.nextLine());
                loop = false;
            }
            catch(InputMismatchException | NumberFormatException e) {
                System.out.println("Employee id was not a valid number.");
                loop = true; 
            }
        }while(loop);
        return intInput;
    }
}


