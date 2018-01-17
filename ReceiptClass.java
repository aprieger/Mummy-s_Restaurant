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
        String desktopPath = System.getProperty("user.home") + "\\Desktop\\";
        String filePath = desktopPath + fileName + ".txt";
        
        String outPut = "Mummy's Restaurant\n " + 
                "123 Drive, SomeCity 23434\n " +
                "Phone: 773.202.5000\n ";
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
            outPut += "Order made: " + (String)ordersObject.get("ORDER_DATE" + "\\n");
            //list the packages x Quantity: price
            for(JSONObject entry: listOfPkgOrdersObject){
                outPut += entry.get("Name") + " x " + entry.get("Quantity") + ": $"
                        + (entry.getInt("Quantity")* entry.getDouble("Price_Per_Pkg") + "\\n");
            }
            //add the total 
            outPut += "Total: $" + ordersObject.get("TOTAL_PRICE") + "\\n";
            //type of payment, if cred: add last 4
            int paymentType = ordersObject.getInt("PAYMENT_TYPE");
            if (paymentType != 0) {
                CreditCards creditCardsAccess = new CreditCards();
                String creditCardNumber = creditCardsAccess.getCreditCardByCreditID(ordersObject.getInt("CREDIT_ID"));
                outPut += "Credit credit ending in: " + creditCardNumber.substring(13);
            } else { 
                outPut += "Will pay in cash upon delivery";
            }
            
            char buffer[] = new char[outPut.length()];
            outPut.getChars(0, outPut.length(), buffer, 0);
            FileWriter f0 = new FileWriter(filePath);
            for (int i = 0; i < buffer.length; i +=2) {
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
    
    public static void main(String[] args) throws JSONException, SQLException, IOException{
        try{
        ReceiptClass.writeReceiptToFile(5,5);
        } catch (SQLException se) {
            System.out.println(se);
        } catch (JSONException je) {
            System.out.println(je);
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
}


