


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.util.Scanner;

public class WorkerUI {
     int userInput;
     boolean loopUI = true;
        
    public void workersUI(){ 
        do { 
            System.out.println("Please choose and enter an option:\n"
                + "[1]: TODO\n"
                + "[2]: TODO\n"
                + "[3]: TODO\n"    
                + "[4]: TODO\n"
                + "[5]: Back to main menu.\n"
                + "[6]: Exit program");
        
        
            Scanner scanner = new Scanner(System.in);
        
            userInput = scanner.nextInt();
           
       
            switch(userInput){
                case 1:
                    // UI we want worker to access
                    break;       
                case 2: 
                    // UI we want worker to access
                    break;
                case 3:
                    // UI we want worker to access
                    break;
                case 4:
                    // UI we want worker to access
                    break;
                case 5:
                    loopUI = false;
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Not a vaild choice!\n");
            }
        } while (loopUI);
    }  
}
