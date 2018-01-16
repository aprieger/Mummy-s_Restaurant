/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class GreetingUI {
    
    
    
    
    
    public static void mainGreetingUI() {
        int userInput;
        boolean loopUI = true;  
        
        do { 
            System.out.println("Welcome to Munny's Restaurant. Please select an option.\n"
                + "[1]: Enter 1 to access customer login."
                + "[2]: Enter 2 to access employee login."
                + "[6]: Exit program");
        
        
            Scanner scanner = new Scanner(System.in);
        
            userInput = scanner.nextInt();
           
       
            switch(userInput){
                case 1:
                    //
                    break;       
                case 2: 
                    AdminWorkerRecordsUI.adminUI();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Not a vaild choice!\n");
            }
        } while (loopUI);
    }  
}

