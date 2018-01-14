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
    
    public static boolean systemLogin() throws SQLException {
        Scanner scan = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){
        
            System.out.print("Enter your username: ");
            String inputUserame = scan.nextLine();
        
            System.out.print("Enter your password: ");
            String inputPassword = scan.nextLine();
            
            try(ResultSet rs = statement.executeQuery("select user_id, password, is_Admin "
                    + "from workers where user_id = '"+ inputUserame +"'")){
            
                if (!rs.isBeforeFirst() )     
                    System.out.println("The username was incorrect.");
                while (rs.next()) {
            
                    if(statement.execute("select password from workers where user_id = '"+ inputPassword +"'")){
                        System.out.println("Login successful");
                        return false;
                    }else {
                        System.out.println("The password was incorrect."); 
                        return true;
                    }
                }
                return true;
            }
        }
    }
    
    public static void resetPassword() throws SQLException { 
        Scanner scan = new Scanner(System.in);
    
        System.out.println("Enter the last name of the employee: ");
        String lastName = scan.nextLine();
        
        System.out.println("Enter your employee id: ");
        String empId = scan.nextLine();
        
        try (Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement()){
            try (ResultSet rs = statement.executeQuery("select employee_id from workers " +
                    "where last_name = '" + lastName +"' and employee_id = "+ Integer.parseInt(empId ))){
                if (!rs.isBeforeFirst())     
                    System.out.println("The last name or employee id was incorrect.");
                while(rs.next()){  
                    if ((rs.getString("last_name").equals(lastName)) && (rs.getString("employee_id").equals(empId))){
                        System.out.println("Enter a new password: ");
                        String tempPassword = scan.nextLine();
                        statement.executeUpdate("update workers set password = '"+ tempPassword +"' where employee_id = " + Integer.parseInt(empId));
                    }
                }
            }
        }
    }
    
    public static void forgotUserName() {
        Scanner scan = new Scanner(System.in);
    
        System.out.print("Enter the last name of the employee: ");
        String tempName = scan.nextLine();
        
        System.out.print("Enter the employee id of the employee: ");
        String tempPhoneNumber = scan.nextLine();
        
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement statement = con.createStatement(); 
        
        if(statement.execute(tempName));
            
        if(statement.execute("select user_name from worker where user_name= '"+ tempName +"'" )){
            if(tempPhoneNumber.equals(statement.executeQuery("select phone_number from worker where phone_number= "+ tempPhoneNumber ))){
                System.out.println("Your username is " ); // TODO
            }else
                System.out.println("Not the correct phone number for that worker.");
        }else
            System.out.println("No worker found with that last name.");    
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

