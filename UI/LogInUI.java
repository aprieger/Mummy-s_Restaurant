/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.SQLException;
import java.util.Scanner;

public class LogInUI {
    
    
        
    public static void employeeLoginUI() {
        int userInput;
        boolean loopLogin = true;
        Worker worker = null;
        
        do { 
            System.out.println("\nPlease choose and enter an option:\n"
                + "[1]: Login to system\n"
                + "[2]: Change password\n"
                + "[3]: Forgot username\n"
                + "[4]: Exit program");
        
        
            Scanner scanner = new Scanner(System.in);
        
            userInput = scanner.nextInt();
           
            try{
                switch(userInput){
                    case 1:
                        worker = Login.systemLogin(); 
                        //System.out.println(worker.getClass().getName());
                        break;       
                    case 2: 
                        Login.resetPassword();
                        break;
                    case 3:
                        Login.forgotUserName();
                        break;
                     case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Not a vaild choice!\n");
                }
            }
            catch(SQLException e){
                e.getMessage();
            }
        } while (!((worker instanceof Admin) || (worker instanceof Worker)));
        
        if (worker.getIsAdmin() == 1){  // NEED TO CHECK IF ADMIN
            // GO TO ADMIN UI
            System.out.println("ADMIN");  //testing
        }
        else {
            WorkerUI.workersUI();
        }
    }
}
