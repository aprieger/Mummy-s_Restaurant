/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.UI;
import app.*;
import java.sql.SQLException;
import java.util.Scanner;
import org.json.JSONException;

/**
 *
 * @author syntel
 */
public class AdminMainUI {
        
        
        public static void AdminUI() {
        int userInput;
        boolean loopUI = true;  
        
        do { 
            System.out.println("Please choose and enter an option:\n"
                + "[1]: View worker records."
                + "[2]: View package records."  
                + "[3]: View customers records"
                + "[4]: View todays orders"  
                + "[5]: Back to main menu.\n"
                + "[6]: Exit program");
        
        
            Scanner scanner = new Scanner(System.in);
        
            userInput = scanner.nextInt();
           
    try {   
            switch(userInput){
                case 1:
                    AdminWorkerRecordsUI.adminUI();
                    break;       
                case 2: 
                    AdminPkgsAndAreasUI.goToAdminPackagesUI();
                    break;
                case 3:
                    new EditCustomersUI().goToEditCustomersUI();
                    break;
                case 4:
                    Worker.viewOrders();
                    break;
                case 5:
                    loopUI = false;
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Not a vaild choice!\n");
            }
    }
    catch (SQLException | JSONException e){
        
    }
        } while (loopUI);
    }  
}
