


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.SQLException;
import java.util.Scanner;

public class WorkerUI {
     
        
    public static void workersUI(){ 
        int userInput;
        boolean loopUI = true;
        
        do { 
            System.out.println("Please choose and enter an option:\n"
                + "[1]: View today's orders\n"
                + "[2]: Back to main menu.\n"
                + "[3]: Exit program");
        
            Scanner scanner = new Scanner(System.in);
        
            userInput = scanner.nextInt();
           
            try {
                switch(userInput){
                    case 1:
                        Worker.viewOrders();   
                        break;       
                    case 2:
                        loopUI = false;
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Not a vaild choice!\n");
                }
            }
            catch (SQLException e){      
            }
        } while (loopUI);
    }  
}
