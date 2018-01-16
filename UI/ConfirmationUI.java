/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MummysRestaurant.UI;

//imports will change when proper directories are established
import MummysRestaurant.OrdersDataAccess;
import MummysRestaurant.OrdersModelClass;
import MummysRestaurant.PkgOrder;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author syntel
 */
public class ConfirmationUI {
    
    public void mainConfirmationView(int customerId, int creditCardId){
        
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
        System.out.println("Total Price: $" + finalPrice);
        System.out.println("Payment:\nCredit Card ending with: " + need to get the last 4 of credit card);
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
    
    public static void mainConfirmationView(int customerId){
        
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
                //establish orders row object
                OrdersDataAccess orderDataLayer = new OrdersDataAccess();
                int orderId = orderDataLayer.idOrdersGenerator();
                
                OrdersModelClass ordersRowObject = new OrdersModelClass();
                ordersRowObject.setOrderID(orderId);
                ordersRowObject.setCustomerID(customerId);
                ordersRowObject.setCreditID(0);
                ordersRowObject.setPaymentType(0);
                ordersRowObject.setTotalPrice(finalPrice);
                ordersRowObject.setStreet(street);
                ordersRowObject.setCity(city);
                ordersRowObject.setAreaCode(city);
                ordersRowObject.setPhoneNumber(phoneNumber);
                ordersRowObject.setDeliveryDate(deliveryDate);
                ordersRowObject.setOrderDate(orderDate);
                ordersRowObject.setOrderStatus(0);
                
                //sets up to retrieve pkgorder ids
                ArrayList<Integer> listOfPckOrderId = new ArrayList();
                ArrayList<JSONObject> listOfPckOrderObject = new ArrayList();
                PkgOrder pkgOrderDataLayer = new PkgOrder();
                listOfPckOrderObject = pkgOrderDataLayer.getOpenPkgOrdersByCustomer(customerId);
                
                //iterate through a list of objects to get the id
                for(JSONObject entry: listOfPckOrderObject) {
                    listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                }
                
                //Inserts row into orders table
                orderDataLayer.insertNewOrdersRow(ordersRowObject);
                
                //closes all open pkgorders
                for(Integer entry: listOfPckOrderId) {
                    pkgOrderDataLayer.closePkgOrder(entry, orderId);
                }
                
                //calls receipt UI and passes in orderId
                ReceiptUI finalScreen = new ReceiptUI();
                finalScreen.receiptView(orderId);
            case 2: 
                //erase everything, go back to main menu
                call method from PkgOrder.java, pass in customerId, and delete packages that are open
                        
        }
        
    }
}
