package app;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


public class PkgOrder {
    public static void addOpenPkgOrder(int packageId, int customerId, int quantity) {
        try {
            //Checks if there is a customer account with the specifiec customer id in the CustomerAccounts table
            ArrayList<JSONObject> customerResultsAL = Utilities.sendQuery("SELECT * from CustomerAccounts WHERE Customer_Id="+customerId);
            if (!customerResultsAL.isEmpty()) {
                //Cehck if there is a package with the specified package id in the Packages table
                ArrayList<JSONObject> packageResultsAL = Utilities.sendQuery("SELECT Package_Id, Name, Description, Meal_Category, Image_Source, Price, Is_Special, Meal_Type from Packages WHERE Package_Id="+packageId);
                if (!packageResultsAL.isEmpty()) {
                    //Add the new open pkg order to the PkgOrders table (Note: IsOpen=1 and OrderId=0)
                    double pricePerPkg = Double.parseDouble(packageResultsAL.get(0).get("PRICE").toString());
                    String updateStr = ("INSERT INTO PkgOrders (Pkg_Order_Id, Order_Id, Package_Id, Customer_Id, Price_Per_Pkg, Quantity, Is_Open) "
                                + "VALUES ("+PkgOrder.getNextPkgOrderId()+","+0+","+packageId+","+customerId+","+pricePerPkg+","+quantity+","+1+")");
                    Utilities.sendUpdate(updateStr);
                }
                else
                    System.out.println(">>>>Error: package does not exist in table");
            }
            else
                System.out.println(">>>>Error: customer does not exist in table");
        } catch (JSONException e){System.out.println(e);}
    }
    
    public static void editOrderId(int pkgOrderId, int newOrderId) {
        //Check if there is an order with the specified order id in the Orders table
        ArrayList<JSONObject> ordersResultsAL = Utilities.sendQuery("SELECT * from Orders WHERE Order_Id="+newOrderId);
        if (!ordersResultsAL.isEmpty())
            Utilities.sendUpdate("UPDATE PkgOrders SET Order_Id="
                    +newOrderId+" WHERE Pkg_Order_Id="+pkgOrderId);
        else
            System.out.println(">>>>Error: Order doesn't exist");
    }
    
    public static void editPackageId(int pkgOrderId, int newPackageId) {
        try {
            ArrayList<JSONObject> packageResultsAL = Utilities.sendQuery("SELECT Package_Id, Price from Packages WHERE Package_Id="+newPackageId);
            //Check if there is a package with the specified package id in the Package table and get the price
            if (!packageResultsAL.isEmpty()) {
                //update the package id
                Utilities.sendUpdate("UPDATE PkgOrders SET Package_Id="
                        +newPackageId+" WHERE Pkg_Order_Id="+pkgOrderId);
                //update the price per pkg
                Utilities.sendUpdate("UPDATE PkgOrders SET Price_Per_Pkg="
                        +packageResultsAL.get(0).get("PRICE").toString()+" WHERE Pkg_Order_Id="+pkgOrderId);
            }
            else
                System.out.println(">>>>Error: Package doesn't exist");   
        } catch (JSONException e) {System.out.println(e);}
    }
    
    public static void editCustomerId(int pkgOrderId, int newCustomerId) {
        //Check if there is an order with the specified order id in the Orders table
        ArrayList<JSONObject> customerResultsAL = Utilities.sendQuery("SELECT * from CustomerAccounts WHERE Customer_Id="+newCustomerId);
        if (!customerResultsAL.isEmpty())        
            Utilities.sendUpdate("UPDATE PkgOrders SET Customer_Id="
                    +newCustomerId+" WHERE Pkg_Order_Id="+pkgOrderId);
        else
            System.out.println(">>>>Error: Customer Account doesn't exist");                    
    }
    
    public static void editQuantity(int pkgOrderId, int newQuantity) {
        Utilities.sendUpdate("UPDATE PkgOrders SET Quantity="
                +newQuantity+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editIsOpen(int pkgOrderId, int newIsOpen) {
        Utilities.sendUpdate("UPDATE PkgOrders SET Is_Open="
                +newIsOpen+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    //TODO: Use customer id instead of pkgorderid to close all pkg orders for the custoemr
    public static void closePkgOrder (int pkgOrderId, int orderId) {
        //Check if there is an order with the specified order id in the Orders table
        ArrayList<JSONObject> ordersResultsAL = Utilities.sendQuery("SELECT * from Orders WHERE Order_Id="+orderId);
        if (!ordersResultsAL.isEmpty()) {
            Utilities.sendUpdate("UPDATE PkgOrders SET Order_Id="
                    +orderId+" WHERE Pkg_Order_Id="+pkgOrderId);
            Utilities.sendUpdate("UPDATE PkgOrders SET Is_Open=0 WHERE Pkg_Order_Id="+pkgOrderId);
        }
        else
            System.out.println(">>>>Error: Order doesn't exist");
    }
    
    public static void deletePkgOrder(int deletePkgOrderId) {
        Utilities.sendUpdate("DELETE FROM PkgOrders WHERE Pkg_Order_Id="+deletePkgOrderId);
    }
        
    public static int getNextPkgOrderId() {
        try {
            ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT MAX(Pkg_Order_Id) AS MAX FROM PkgOrders");
            if (!resultsAL.isEmpty())
                return Integer.parseInt(resultsAL.get(0).get("MAX").toString())+1;
            else
                return 1;
        } catch (JSONException e) {System.out.println(e); return -1;}
    }
    
    public static ArrayList<JSONObject> getSinglePkgOrder(int pkgOrderId) {
        try {
            String queryStr = ("SELECT * from PkgOrders WHERE Pkg_Order_Id="+pkgOrderId);
            ArrayList<JSONObject> resultsAL = Utilities.sendQuery(queryStr);
            if (!resultsAL.isEmpty())
                return resultsAL;
            else
                return null;
        } catch (Exception e) {System.out.println(e); return null;}
    }
    
    public static ArrayList<JSONObject> getOpenPkgOrdersByCustomer(int Customer_Id) {
        return Utilities.sendQuery("SELECT O.Pkg_Order_Id, P.Name, P.Meal_Category, O.Price_Per_Pkg, O.Quantity, S.Name AS SERVICE_AREA "
                + "FROM PkgOrders O, Packages P, ServiceAreas S "
                + "WHERE P.Package_Id=O.Package_Id AND O.Customer_Id="+Customer_Id+" AND S.Package_Id=P.Package_Id AND O.Is_Open=1");
    }
    
    public static ArrayList<JSONObject> getAllPkgOrdersByOrder(int OrderId) {
        return Utilities.sendQuery("SELECT O.Pkg_Order_Id, P.Name, P.Meal_Category, O.Price_Per_Pkg, O.Quantity, S.Name AS SERVICE_AREA "
                + "FROM PkgOrders O, Packages P, ServiceAreas S "
                + "WHERE P.Package_Id=O.Package_Id AND O.Order_Id="+OrderId+" AND S.Package_Id=P.Package_Id");
    }
    
    public static ArrayList<JSONObject> getAllClosedPkgOrders() {
        return Utilities.sendQuery("SELECT * FROM PkgOrders WHERE Is_Open=0");
    }
    
    public static ArrayList<JSONObject> getTotalPrice(int customer_id) {
        return Utilities.sendQuery("SELECT Price_Per_Pkg*Quantity AS PRODUCT FROM PkgOrders WHERE Is_Open=0 AND Customer_Id="+customer_id);
    }
    
    public static double getFinalPrice(int customer_id){
        try {
            ArrayList<JSONObject> resultsAL = getTotalPrice(customer_id);
            double finalPrice=0.0;
            int rowCount = resultsAL.size();
            if (!resultsAL.isEmpty()){
                for (int i=0; i<rowCount; i++)
                    finalPrice+=Double.parseDouble(resultsAL.get(i).get("PRODUCT").toString());
            }
            return finalPrice;
        } catch (JSONException e) {return 0.0;}
    }
    
    public static String getStringFromJSON(ArrayList<JSONObject> resultsAL) {
        if (!resultsAL.isEmpty()) {
            String output="";
            try {
                int rowCount = resultsAL.size();
                String[] columnNames = new String[]{"PKG_ORDER_ID","ORDER_ID","PACKAGE_ID","CUSTOMER_ID","PRICE_PER_PKG","QUANTITY","IS_OPEN"};
                int columnCount = columnNames.length;
                for (int i=0; i<rowCount; i++) {
                    for (int j=0; j<columnCount; j++) {
                        if (resultsAL.get(i).has(columnNames[j]))
                            output += columnNames[j] + ": " + resultsAL.get(i).get(columnNames[j]) + "\n";
                    }
                }
                return output;
            } catch (JSONException e) {return output+e;}
        }
        else
            return "";
    }
}
