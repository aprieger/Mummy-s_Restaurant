import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class PkgOrder {
    public static void addOpenPkgOrder(int packageId, int customerId, int quantity) {
        //Checks if there is a customer account with the specifiec customer id in the CustomerAccounts table
        ArrayList<ArrayList> customerResultsAL = PkgOrder.sendQuery("SELECT * from CustomerAccounts WHERE Customer_Id="+customerId);
        if (customerResultsAL!=null) {
            //Cehck if there is a package with the specified package id in the Packages table
            ArrayList<ArrayList> packageResultsAL = PkgOrder.sendQuery("SELECT Package_Id, Name, Description, Meal_Category, Image_Source, Price, Is_Special, Meal_Type from Packages WHERE Package_Id="+packageId);
            if (packageResultsAL!=null) {
                //Add the new open pkg order to the PkgOrders table (Note: IsOpen=1 and OrderId=0)
                String pricePerPkgStr = packageResultsAL.get(0).get(6).toString();
                String updateStr = ("INSERT INTO PkgOrders (Pkg_Order_Id, Order_Id, Package_Id, Customer_Id, Price_Per_Pkg, Quantity, Is_Open)"
                            + " VALUES ("+PkgOrder.getNextPkgOrderId()+","+0+","+packageId+","+customerId+","+pricePerPkgStr+","+quantity+","+1+")");
                    PkgOrder.sendUpdate(updateStr);
            }
            else
                System.out.println(">>>>Error: package does not exist in table");
        }
        else
            System.out.println(">>>>Error: customer does not exist in table");
    }
    
    public static void editOrderId(int pkgOrderId, int newOrderId) {
        //Check if there is an order with the specified order id in the Orders table
        ArrayList<ArrayList> ordersResultsAL = PkgOrder.sendQuery("SELECT * from Orders WHERE Order_Id="+newOrderId);
        if (ordersResultsAL!=null)
            PkgOrder.sendUpdate("UPDATE PkgOrders SET Order_Id="
                    +newOrderId+" WHERE Pkg_Order_Id="+pkgOrderId);
        else
            System.out.println(">>>>Error: Order doesn't exist");
    }
    
    public static void editPackageId(int pkgOrderId, int newPackageId) {
        ArrayList<ArrayList> packageResultsAL = PkgOrder.sendQuery("SELECT Package_Id, Price from Packages WHERE Package_Id="+newPackageId);
        //Check if there is a package with the specified package id in the Package table and get the price
        if (packageResultsAL!=null) {
            //update the package id
            PkgOrder.sendUpdate("UPDATE PkgOrders SET Package_Id="
                    +newPackageId+" WHERE Pkg_Order_Id="+pkgOrderId);
            //update the price per pkg
            PkgOrder.sendUpdate("UPDATE PkgOrders SET Price_Per_Pkg="
                    +packageResultsAL.get(0).get(1).toString()+" WHERE Pkg_Order_Id="+pkgOrderId);
        }
        else
            System.out.println(">>>>Error: Package doesn't exist");        
    }
    
    public static void editCustomerId(int pkgOrderId, int newCustomerId) {
        //Check if there is an order with the specified order id in the Orders table
        ArrayList<ArrayList> customerResultsAL = PkgOrder.sendQuery("SELECT * from CustomerAccounts WHERE Customer_Id="+newCustomerId);
        if (customerResultsAL!=null)        
            PkgOrder.sendUpdate("UPDATE PkgOrders SET Customer_Id="
                    +newCustomerId+" WHERE Pkg_Order_Id="+pkgOrderId);
        else
            System.out.println(">>>>Error: Customer Account doesn't exist");                    
    }
    
    public static void editQuantity(int pkgOrderId, int newQuantity) {
        PkgOrder.sendUpdate("UPDATE PkgOrders SET Quantity="
                +newQuantity+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editIdOpen(int pkgOrderId, int newIsOpen) {
        PkgOrder.sendUpdate("UPDATE PkgOrders SET Is_Open="
                +newIsOpen+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void closePkgOrder (int pkgOrderId, int orderId) {
        //Check if there is an order with the specified order id in the Orders table
        ArrayList<ArrayList> ordersResultsAL = PkgOrder.sendQuery("SELECT * from Orders WHERE Order_Id="+orderId);
        if (ordersResultsAL!=null) {
            PkgOrder.sendUpdate("UPDATE PkgOrders SET Order_Id="
                    +orderId+" WHERE Pkg_Order_Id="+pkgOrderId);
            PkgOrder.sendUpdate("UPDATE PkgOrders SET Is_Open=0 WHERE Pkg_Order_Id="+pkgOrderId);
        }
        else
            System.out.println(">>>>Error: Order doesn't exist");
    }
    
    public static void deletePkgOrder(int deletePkgOrderId) {
        PkgOrder.sendUpdate("DELETE FROM PkgOrders WHERE Pkg_Order_Id="+deletePkgOrderId);
    }
    
    public static ArrayList sendQuery(String queryStr) {
        try {
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","hr","hr");
            PreparedStatement pstmt = conn.prepareStatement(queryStr);
            ResultSet rs = pstmt.executeQuery(queryStr);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            ArrayList<ArrayList> resultsAL = new ArrayList();
            while(rs.next()){
                ArrayList subAL = new ArrayList();
                int columnIndex = 1;
                while(rsmd.getColumnCount()>=columnIndex) {
                    System.out.println(rsmd.getColumnClassName(columnIndex));
                    switch (rsmd.getColumnClassName(columnIndex)) {
                        case ("java.math.BigDecimal"): 
                            subAL.add(rs.getDouble(columnIndex));
                            break;
                        case ("java.lang.String"): 
                            subAL.add(rs.getString(columnIndex));
                            break;
                        default:
                            subAL.add(rs.getString(columnIndex));
                            break;
                    }
                    columnIndex++;
                }
                resultsAL.add(subAL);
            }
            pstmt.close();
            conn.close();
            return resultsAL;
        } catch (Exception ex) {return null;}
    }
    
    public static void sendUpdate(String queryStr) {
        try {
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","hr","hr");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(queryStr);
            stmt.close();
            conn.close();
        } catch (Exception ex) {}
    }
    
    public static int getNextPkgOrderId() {
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery("SELECT MAX(Pkg_Order_Id) FROM PkgOrders");
        if (resultsAL!=null)
            return (int)Double.parseDouble(resultsAL.get(0).get(0).toString())+1;
        else
            return 1;
    }
    
    public static String getPkgOrderId(int pkgOrderId) {
        String queryStr = ("SELECT * from PkgOrders WHERE Pkg_Order_Id="+pkgOrderId);
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery(queryStr);
        if (resultsAL!=null)
            return (resultsAL.get(0).get(0).toString());
        else
            return "Package Order not found";
    }
    
    public static String getOpenPkgOrdersByCustomer(String Customer_Id) {
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery("SELECT O.Pkg_Order_Id, P.Name, P.Meal_Category, O.Price_Per_Pkg, O.Quantity "
                + "FROM PkgOrders O, Packages P "
                + "WHERE P.Package_Id=O.Package_Id AND O.Customer_Id="+Customer_Id+" AND O.Is_Open=1");
        String output=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package ID","|Name",
                "|Description","|Meal Category","|Image Source","|Price",
                "|Is Special?","|Meal Type");
        int aLIndex = 0;
        if (resultsAL!=null ){
            while (resultsAL.size()>aLIndex) {
                int subALIndex = 0;
                while (resultsAL.get(aLIndex).size()>subALIndex) {
                    output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                    subALIndex++;
                }
                aLIndex++;
                output += "\n";
            }
        }
        return output;
    }
    
    public static String getAllClosedPkgOrders() {
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery("SELECT * "
                + "FROM PkgOrders "
                + "WHERE Is_Open=0");
        String output=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package ID","|Name",
                "|Description","|Meal Category","|Image Source","|Price",
                "|Is Special?","|Meal Type");
        int aLIndex = 0;
        if (resultsAL!=null ){
            while (resultsAL.size()>aLIndex) {
                int subALIndex = 0;
                while (resultsAL.get(aLIndex).size()>subALIndex) {
                    output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                    subALIndex++;
                }
                aLIndex++;
                output += "\n";
            }
        }
        return output;
    }
    
    public static String getArrayList(ArrayList<ArrayList> resultsAL){
        String output = "";
        int aLCount=0;
        if (resultsAL!=null) {
            while (resultsAL.size()>aLCount) {
                int subALCount=0;
                while (resultsAL.get(aLCount).size()>subALCount) {
                    output += resultsAL.get(aLCount).get(subALCount).toString() + " | ";
                    subALCount++;
                }
                output += "\n";
                aLCount++;
            }
        }
        return output;
    }
    
    public static String printPkgOrderData(int pkgOrderId) {
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery("SELECT O.Pkg_Order_Id, P.Name, P.Meal_Category, O.Price_Per_Pkg, O.Quantity "
                + "FROM PkgOrders O, Packages P "
                + "WHERE P.Package_Id=O.Package_Id AND P.PkgOrderId="+pkgOrderId);
        String output=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package ID","|Name",
                "|Description","|Meal Category","|Image Source","|Price",
                "|Is Special?","|Meal Type");
        int aLIndex = 0;
        if (resultsAL!=null ){
            while (resultsAL.size()>aLIndex) {
                int subALIndex = 0;
                while (resultsAL.get(aLIndex).size()>subALIndex) {
                    output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                    subALIndex++;
                }
                aLIndex++;
                output += "\n";
            }
        }
        return output;
    }
}
