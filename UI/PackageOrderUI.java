package app.UI;
import app.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//TODO IMPORT ALL NECESSARY PACKAGES
import java.util.Scanner;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

//import the Package

/**
 *
 * @author syntel
 */
public class PackageOrderUI {
    
    public static void mainViewOfPackageOrder(int customerId){
        
        ArrayList<JSONObject> listOfJsonObject = new ArrayList();
        listOfJsonObject = PkgOrder.getOpenPkgOrdersByCustomer(customerId);
        
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("NAME") + "\n" + 
                        "Quantity: " + entry.getInt("QUANTITY") + "\n" + "Price per package: " + entry.getInt("PRICE_PER_PKG"));
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
                ConfirmationUI.mainConfirmationView(customerId);
            //credit
            case 1:
                CreditCardUI.CustomerCreditView(customerId);
            //menu
            case 2:
                CustomerMenuUI.goToMenuUI(customerId);
        }
    }
}
