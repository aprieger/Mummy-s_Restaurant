/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.UI;
import app.*;
import app.Package;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author syntel
 */
public class ReceiptUI {
    
    public static void receiptView(int orderId, int customerId) {
        
        System.out.println("Thanks for your order! Your...\n\n");
        
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
            System.out.println("Order made on: " + ordersObject.get("ORDER_DATE") + "\n\n");
            //list the packages x Quantity: price
            for(JSONObject entry: listOfPkgOrdersObject){
                System.out.println("Item(s) Ordered:" + "\n" + 
                        entry.get("NAME") + " x " + entry.get("QUANTITY") + ": $"
                        + (entry.getInt("QUANTITY")* entry.getDouble("PRICE_PER_PKG") 
                        + "\n"));
            }
            //add the total 
            System.out.println("\n" + "Total: $" + ordersObject.get("TOTAL_PRICE") + "\n");
            //type of payment, if cred: add last 4
            int paymentType = ordersObject.getInt("PAYMENT_TYPE");
            if (paymentType != 0) {
                CreditCards creditCardsAccess = new CreditCards();
                String creditCardNumber = creditCardsAccess.getCreditCardByCreditID(ordersObject.getInt("CREDIT_ID"));
                System.out.println("Paid with credit card ending in: " + 
                        creditCardNumber.substring(creditCardNumber.length()-4));
            } else { 
                System.out.println("\nWill pay in cash upon delivery");
            }
            
        } catch(SQLException se) {
            System.out.println(se);
        } catch(JSONException je) {
            System.out.println(je);
        } 
        System.out.println("Your order will be filled and delivered on the date specified!");
    }
}
