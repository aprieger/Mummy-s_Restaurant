/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MummysRestaurant;

import java.util.Scanner;
import MummysRestaurant.ConfirmationUI;
//import the Package

/**
 *
 * @author syntel
 */
public class PackageOrderUI {
    
    private static void mainViewofPackageOrder(){
        //todo: find method to query Packages for customer related packages
        
        //display the list of packages
        
        System.out.println("Please select and enter payment type:\n"
                + "[1]: For cash\n"
                + "[2]: For Credit"
                + "\n[3]: Return to the menu");
        
        Scanner scanner = new Scanner(System.in);
        
        int userInput = scanner.nextInt();
        
        switch (userInput) {
            case 1: 
                ConfirmationUI confirmation = new ConfirmationUI();
                confirmation.mainConfirmationView();
            case 2:
                //todo: grab the PaymentUI method
            case 3: 
                Package menu = new Package();
                menu.printMenu();
        }
    }
}
