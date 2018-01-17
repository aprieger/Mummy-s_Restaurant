/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.UI;
import app.*;

//imports will change when proper directories are established
import app.OrdersDataAccess;
import app.OrdersModelClass;
import app.PkgOrder;
import app.CreditCards;
import app.UI.CustomerMenuUI;
import app.UI.ReceiptUI;
import java.security.Timestamp;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        finalPrice = openPackageOrders.getFinalPrice(customerId);
        String creditCardNumber = "";
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("NAME") + " " + 
                        "Quantity: " + entry.getInt("QUANTITY") + " " + "Price: " + entry.getInt("Price_Per_Pkg"));
            }
            
            CreditCards creditCardsAccess = new CreditCards();
            creditCardNumber = creditCardsAccess.getCreditCardByCreditID(creditCardId);
            
        } catch (JSONException je) {
            System.out.println(je);
        }
        System.out.println("Total Price: $" + finalPrice);
        System.out.println("Payment:\nCredit Card ending with: " + creditCardNumber.substring(13));
        System.out.println("Please choose and enter an option:\n"
                + "[1]: To confirm order\n"
                + "[2]: To cancel and start over");
        
        
        Scanner scanner = new Scanner(System.in);
        
        int userInput = scanner.nextInt();
        
        switch(userInput){
            case 1:
                try {
                    //establish orders row object
                    OrdersDataAccess orderDataLayer = new OrdersDataAccess();
                    int orderId = orderDataLayer.idOrdersGenerator();

                    //variables for the object
                    String deliveryDate = "";
                    String streetAddress = "";
                    String cityAddress ="";
                    String phoneNumber ="";
                    int areaCode;
                    String orderDate = "";
                    
                    
                    //will scan in for the user to type in
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Please enter delivery date(EX: DD-MMM-YY -> 12-FEB-18):\n");
                    deliveryDate = sc.nextLine();
                    System.out.println("Please enter street address:\n");
                    streetAddress = sc.nextLine();
                    System.out.println("Please enter City:\n");
                    cityAddress = sc.nextLine();
                    System.out.println("Please enter your phone number (area code included)\n");
                    phoneNumber = sc.nextLine();
                    System.out.println("Please enter your zipcode:\n");
                    areaCode = Integer.parseInt(sc.nextLine());
                    
                    OrdersModelClass ordersRowObject = new OrdersModelClass();
                    ordersRowObject.setOrderID(orderId);
                    ordersRowObject.setCustomerID(customerId);
                    ordersRowObject.setCreditID(creditCardId);
                    ordersRowObject.setPaymentType(1);
                    ordersRowObject.setTotalPrice(finalPrice);
                    ordersRowObject.setStreet(streetAddress);
                    ordersRowObject.setCity(cityAddress);
                    ordersRowObject.setAreaCode(areaCode);
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
                    for (JSONObject entry : listOfPckOrderObject) {
                        listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                    }

                    //Inserts row into orders table
                    orderDataLayer.insertNewOrdersRow(ordersRowObject);

                    //closes all open pkgorders
                    for (Integer entry : listOfPckOrderId) {
                        pkgOrderDataLayer.closePkgOrder(entry, orderId);
                    }

                    //calls receipt UI and passes in orderId
                    ReceiptUI finalScreen = new ReceiptUI();
                    finalScreen.receiptView(orderId);
                    
                } catch (SQLException se) {
                    System.out.println(se);
                } catch (JSONException je) {
                    System.out.println(je);
                }
            case 2: 
                try {
                    //sets up to retrieve pkgorder ids
                    ArrayList<Integer> listOfPckOrderId = new ArrayList();
                    ArrayList<JSONObject> listOfPckOrderObject = new ArrayList();
                    PkgOrder pkgOrderDataLayer = new PkgOrder();
                    listOfPckOrderObject = pkgOrderDataLayer.getOpenPkgOrdersByCustomer(customerId);
                    
                    //iterate through a list of objects to get the id
                    for (JSONObject entry : listOfPckOrderObject) {
                        listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                    }
                    
                    //deletes all open pkgorders
                    for (Integer entry : listOfPckOrderId) {
                        pkgOrderDataLayer.deletePkgOrder(entry);
                    }
                    
                } catch (JSONException je) {
                    System.out.println(je);
                }
                CustomerMenuUI menu = new CustomerMenuUI();
                menu.goToMenuUI(customerId);
        }
        
    }
    
    public static void mainConfirmationView(int customerId){
        
        
        //getting the final price
        PkgOrder openPackageOrders = new PkgOrder();
        double finalPrice; 
        finalPrice = openPackageOrders.getFinalPrice(customerId);
        
        //retrieves all the open pckorder rows 
        ArrayList<JSONObject> listOfJsonObject = new ArrayList();
        listOfJsonObject = openPackageOrders.getOpenPkgOrdersByCustomer(customerId);
        
        
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("NAME") + " " + 
                        "Quantity: " + entry.getInt("QUANTITY") + " " + "Price: " + entry.getInt("Price_Per_Pkg"));
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
        
        switch(userInput) {
            case 1:
                try {
                    //establish orders row object
                    OrdersDataAccess orderDataLayer = new OrdersDataAccess();
                    int orderId = orderDataLayer.idOrdersGenerator();

                    //variables for the object
                    String deliveryDate = "";
                    String streetAddress = "";
                    String cityAddress ="";
                    String phoneNumber ="";
                    int areaCode;
                    String orderDate = "";
                    
                    
                    //will scan in for the user to type in
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Please enter delivery date(EX: DD-MMM-YY -> 12-FEB-18):\n");
                    deliveryDate = sc.nextLine();
                    System.out.println("Please enter street address:\n");
                    streetAddress = sc.nextLine();
                    System.out.println("Please enter City:\n");
                    cityAddress = sc.nextLine();
                    System.out.println("Please enter your phone number (area code included)\n");
                    phoneNumber = sc.nextLine();
                    System.out.println("Please enter your zipcode:\n");
                    areaCode = Integer.parseInt(sc.nextLine());
                    
                    
                    OrdersModelClass ordersRowObject = new OrdersModelClass();
                    ordersRowObject.setOrderID(orderId);
                    ordersRowObject.setCustomerID(customerId);
                    ordersRowObject.setCreditID(0);
                    ordersRowObject.setPaymentType(0);
                    ordersRowObject.setTotalPrice(finalPrice);
                    ordersRowObject.setStreet(streetAddress);
                    ordersRowObject.setCity(cityAddress);
                    ordersRowObject.setAreaCode(areaCode);
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
                    for (JSONObject entry : listOfPckOrderObject) {
                        listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                    }

                    //Inserts row into orders table
                    orderDataLayer.insertNewOrdersRow(ordersRowObject);

                    //closes all open pkgorders
                    for (Integer entry : listOfPckOrderId) {
                        pkgOrderDataLayer.closePkgOrder(entry, orderId);
                    }

                    //calls receipt UI and passes in orderId
                    ReceiptUI finalScreen = new ReceiptUI();
                    finalScreen.receiptView(orderId);
                    
                } catch (SQLException se) {
                    System.out.println(se);
                } catch (JSONException je) {
                    System.out.println(je);
                }
            case 2: 
                
                try {
                    //sets up to retrieve pkgorder ids
                    ArrayList<Integer> listOfPckOrderId = new ArrayList();
                    ArrayList<JSONObject> listOfPckOrderObject = new ArrayList();
                    PkgOrder pkgOrderDataLayer = new PkgOrder();
                    listOfPckOrderObject = pkgOrderDataLayer.getOpenPkgOrdersByCustomer(customerId);
                    
                    //iterate through a list of objects to get the id
                    for (JSONObject entry : listOfPckOrderObject) {
                        listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                    }
                    
                    //deletes all open pkgorders
                    for (Integer entry : listOfPckOrderId) {
                        pkgOrderDataLayer.deletePkgOrder(entry);
                    }
                    
                } catch (JSONException je) {
                    System.out.println(je);
                }
                CustomerMenuUI menu = new CustomerMenuUI();
                menu.goToMenuUI(customerId);               
        }
    }
    
    /*public boolean dateWithinRange(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
                
        try {
            Date date = df.parse(dateString);
            
            Calendar currentDateAfter1Month = Calendar.getInstance();
            currentDateAfter1Month
            
        } catch (Exception pe) {
            System.out.println(pe);
        }
        return formattedDate;
    }*/
}
