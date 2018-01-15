/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MummysRestaurant;

//imports will change when proper directories are established
import MummysRestaurant.OrdersDataAccess;
import java.util.Scanner;
/**
 *
 * @author syntel
 */
public class ConfirmationUI {
    
    void mainConfirmationView(){
        //display packages and information and pricing
        
        System.out.println("Please choose and enter an option:\n"
                + "[1]: To confirm order\n"
                + "[2]: To cancel and start over");
        
        
        Scanner scanner = new Scanner(System.in);
        
        int userInput = scanner.nextInt();
        
        switch(userInput){
            case 1:
                //Grabs the input method for the packageOrder or Package data access layer
            case 2: 
                //erase everything, go back to main menu
        }
        
    }
    
}
