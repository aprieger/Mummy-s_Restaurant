/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MummysRestaurant.UI;

//imports will change when proper directories are established
import MummysRestaurant.OrdersDataAccess;
import PkgOrder;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author syntel
 */
public class ConfirmationUI {
    
    void mainConfirmationView(int customerId, int creditCardId){
        
        PkgOrder openPackageOrders = new PkgOrder();
        double finalPrice;
        
        ArrayList<JSONObject> listOfJsonObject = new ArrayList();
        listOfJsonObject = openPackageOrders.getOpenPkgOrdersByCustomer(customerId);
        //todo: find method to query Packages for customer related packages
        
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("PACKAGE_NAME") + " " + 
                        "Quantity: " + entry.getInt("QUANTITY") + " " + "Price: " + entry.getInt("PRICE"));
            }
        } catch (JSONException je) {
            System.out.println(je);
        }
        
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
    
    void mainConfirmationView(int customerId){
        
        PkgOrder openPackageOrders = new PkgOrder();
        double finalPrice; 
        
        ArrayList<JSONObject> listOfJsonObject = new ArrayList();
        listOfJsonObject = openPackageOrders.getOpenPkgOrdersByCustomer(customerId);
        finalPrice = openPackageOrders.getFinalPrice();
        //todo: find method to query Packages for customer related packages
        
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("PACKAGE_NAME") + " " + 
                        "Quantity: " + entry.getInt("QUANTITY") + " " + "Price: " + entry.getInt("PRICE"));
            }
            System.out.println("Total Price: $" + finalPrice);
            System.out.println("Cash Payment to be received upon delivery.");
        } catch (JSONException je) {
            System.out.println(je);
        }
        
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
