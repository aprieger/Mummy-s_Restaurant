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
import java.io.IOException;
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
    
    public void mainConfirmationView(int customerId, int creditCardId) throws IOException{
        
        PkgOrder openPackageOrders = new PkgOrder();
        double finalPrice;
        
        ArrayList<JSONObject> listOfJsonObject = new ArrayList();
        listOfJsonObject = openPackageOrders.getOpenPkgOrdersByCustomer(customerId);
        finalPrice = openPackageOrders.getFinalPrice(customerId);
        String creditCardNumber = "";
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("NAME") + " " + 
                        "Quantity: " + entry.getInt("QUANTITY") + " " + "Price: " + entry.getInt("PRICE_PER_PKG"));
            }
            
            CreditCards creditCardsAccess = new CreditCards();
            creditCardNumber = creditCardsAccess.getCreditCardByCreditID(creditCardId);
            
        } catch (JSONException je) {
            System.out.println(je);
        }
        System.out.println("Total Price: $" + finalPrice);
        System.out.println("Payment:\nCredit Card ending with: " + creditCardNumber.substring(creditCardNumber.length()-4));
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
                    finalScreen.receiptView(orderId, customerId);
                    ReceiptClass.writeReceiptToFile(orderId, customerId);
                    
                } catch (SQLException se) {
                    System.out.println(se);
                } catch (JSONException je) {
                    System.out.println(je);
                } catch (IOException ie) {
                    System.out.println(ie);
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
    
    public static void mainConfirmationView(int customerId) throws IOException{
        
        
        //getting the final price
        double finalPrice; 
        finalPrice = PkgOrder.getFinalPrice(customerId);
        
        //retrieves all the open pckorder rows 
        ArrayList<JSONObject> listOfJsonObject = new ArrayList();
        listOfJsonObject = PkgOrder.getOpenPkgOrdersByCustomer(customerId);
        
        
        try {
            for (JSONObject entry: listOfJsonObject) {
                System.out.println("Package Name: " + entry.getString("NAME") + " " + 
                        "Quantity: " + entry.getInt("QUANTITY") + " " + "Price: " + entry.getInt("PRICE_PER_PKG"));
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
                    System.out.println("Please enter delivery date(EX: DD-MMM-YYYY -> 12-FEB-2018):\n");
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
                    listOfPckOrderObject = PkgOrder.getOpenPkgOrdersByCustomer(customerId);

                    //iterate through a list of objects to get the id
                    for (JSONObject entry : listOfPckOrderObject) {
                        listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                    }

                    //Inserts row into orders table
                    OrdersDataAccess.insertNewOrdersRow(ordersRowObject);

                    //closes all open pkgorders
                    for (Integer entry : listOfPckOrderId) {
                        PkgOrder.closePkgOrder(entry, orderId);
                    }

                    //calls receipt UI and passes in orderId
                    ReceiptUI finalScreen = new ReceiptUI();
                    finalScreen.receiptView(orderId, customerId);
                    ReceiptClass.writeReceiptToFile(orderId, customerId);
                    
                } catch (SQLException se) {
                    System.out.println(se);
                } catch (JSONException je) {
                    System.out.println(je);
                } catch (IOException ie) {
                    System.out.println(ie);
                }
            case 2: 
                
                try {
                    //sets up to retrieve pkgorder ids
                    ArrayList<Integer> listOfPckOrderId = new ArrayList();
                    ArrayList<JSONObject> listOfPckOrderObject = new ArrayList();
                    listOfPckOrderObject = PkgOrder.getOpenPkgOrdersByCustomer(customerId);
                    
                    //iterate through a list of objects to get the id
                    for (JSONObject entry : listOfPckOrderObject) {
                        listOfPckOrderId.add(entry.getInt("PKG_ORDER_ID"));
                    }
                    
                    //deletes all open pkgorders
                    for (Integer entry : listOfPckOrderId) {
                        PkgOrder.deletePkgOrder(entry);
                    }
                } catch (JSONException je) {
                    System.out.println(je);
                }
                CustomerMenuUI menu = new CustomerMenuUI();
                menu.goToMenuUI(customerId);               
        }
    }
}
