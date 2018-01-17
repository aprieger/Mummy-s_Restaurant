/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author syntel
 */
public class ReceiptClass {
    
    public static void writeReceiptToFile (int orderId, int customerId) throws SQLException, JSONException, IOException{
        
        String fileName = "orderNumber" + orderId;
        String desktopPath = System.getProperty("user.home") + "\\Desktop\\ReceiptFolder\\";
        String filePath = desktopPath + fileName + ".txt";
        
        String outPut = "Mummy's Restaurant " + String.format("%n") +
                "123 Drive, SomeCity 23434 " + String.format("%n") +
                "Phone: 773.202.5000 " + String.format("%n") + String.format("%n");
        ArrayList<JSONObject> listOfOrdersObject = new ArrayList();
        ArrayList<JSONObject> listOfPkgOrdersObject = new ArrayList();
        
        try {
            //establish OrdersDataAccess Layer 
            JSONObject ordersObject;
            listOfOrdersObject = OrdersDataAccess.getCustomerOrdersOrdersID(orderId);
            ordersObject = listOfOrdersObject.get(0);
            //establish PkgOrder layer
            listOfPkgOrdersObject = PkgOrder.getAllPkgOrdersByOrder(orderId);
            
            //put date the order was made
            outPut += "Order made on: " + ordersObject.get("ORDER_DATE") + String.format("%n")
                    + String.format("%n");
            //list the packages x Quantity: price
            for(JSONObject entry: listOfPkgOrdersObject){
                outPut += "Item(s) Ordered:" + String.format("%n") + 
                        entry.get("NAME") + " x " + entry.get("QUANTITY") + ": $"
                        + (entry.getInt("QUANTITY")* entry.getDouble("PRICE_PER_PKG") 
                        + String.format("%n"));
            }
            //add the total 
            outPut += String.format("%n") + "Total: $" + ordersObject.get("TOTAL_PRICE") + String.format("%n");
            //type of payment, if cred: add last 4
            int paymentType = ordersObject.getInt("PAYMENT_TYPE");
            if (paymentType != 0) {
                CreditCards creditCardsAccess = new CreditCards();
                String creditCardNumber = creditCardsAccess.getCreditCardByCreditID(ordersObject.getInt("CREDIT_ID"));
                outPut += "Paid with credit card ending in: " + 
                        creditCardNumber.substring(creditCardNumber.length()-4);
            } else { 
                outPut += "Will pay in cash upon delivery";
            }
            
            char buffer[] = new char[outPut.length()];
            outPut.getChars(0, outPut.length(), buffer, 0);
            FileWriter f0 = new FileWriter(filePath);
            for (int i = 0; i < buffer.length; i++) {
                f0.write(buffer[i]);
            }
            f0.close();
        } catch(SQLException se) {
            System.out.println(se);
        } catch(JSONException je) {
            System.out.println(je);
        } catch(IOException ie) {
            System.out.println(ie);
        }
    }
}


