/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.UI;
import app.*;
import app.Package;
import java.util.ArrayList;
import org.json.JSONObject;
/**
 *
 * @author syntel
 */
public class ReceiptUI {
    
    public static void receiptView(int orderId) {
        //sets up to get to orderdataaccess layer and get order row
        OrdersDataAccess ordersDataLayer = new OrdersDataAccess();
        ArrayList<JSONObject> rowValues = ordersDataLayer.getCustomerOrdersCustomerID(orderId);
        
        //TODO: retrieve information for orders
        
        
        System.out.println("Your order will be filled and delivered on the date specified!");
    }
}
