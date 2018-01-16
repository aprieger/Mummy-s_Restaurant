/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MummysRestaurant.UI;

//TODO IMPORT ALL NECESSARY PACKAGES
import java.util.Scanner;
import MummysRestaurant.UI.ConfirmationUI;
import MummysRestaurant.UI.CreditCardUI;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

//import the Package

/**
 *
 * @author syntel
 */
public class PackageOrderUI {
    
    private static void mainViewOfPackageOrder(int customerId){
        PkgOrder openPackageOrders = new PkgOrder();
        
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
        
        System.out.println("Please select and enter payment type:\n"
                + "[0]: For cash\n"
                + "[1]: For Credit\n"
                + "[2]: Return to the menu");
        
        Scanner scanner = new Scanner(System.in);
        
        int userInput = scanner.nextInt();
        
        switch (userInput) {
            //cash
            case 0: 
                ConfirmationUI confirmation = new ConfirmationUI();
                confirmation.mainConfirmationView(customerId);
            //credit
            case 1:
                CreditCardUI creditCardOption = new CreditCardUI();
                creditCardOption.CustomerCreditView(customerId);
            //food menu
            case 2: 
                Package menu = new MenuUI();
                //Does this need a parameter to be passed in?
                menu.menuUI(customerId);
        }
    }
}
