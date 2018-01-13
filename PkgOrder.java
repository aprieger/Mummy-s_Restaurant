

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class PkgOrder {
    public static void addOpenPkgOrder(int packageId, int customerId, 
            double pricePerPkg, int quantity, int isOpen) {
            String updateStr = ("INSERT INTO Pkg_Orders (Pkg_Order_Id, Order_Id, Package_Id, Customer_Id, Price_Per_Pkg, Quantity, Is_Open)"
                    + " VALUES ("+PkgOrder.getNextPkgOrderId()+",'"+0+"','"+packageId+"',"+customerId+",'"+pricePerPkg+"',"+quantity+","+isOpen+")");
            PkgOrder.sendUpdate(updateStr);
    }
    
    public static void editOrderId(int pkgOrderId, int newOrderId) {
        PkgOrder.sendUpdate("UPDATE Pkg_Orders SET Order_Id="
                +newOrderId+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editPackageId(int pkgOrderId, int newPackageId) {
        PkgOrder.sendUpdate("UPDATE Pkg_Orders SET Package_Id="
                +newPackageId+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editCustomerId(int pkgOrderId, int newCustomerId) {
        PkgOrder.sendUpdate("UPDATE Pkg_Orders SET Customer_Id="
                +newCustomerId+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editPricePerPkg(int pkgOrderId, double newPricePerPkg) {
        PkgOrder.sendUpdate("UPDATE Pkg_Orders SET Price_Per_Pkg="
                +newPricePerPkg+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editQuantity(int pkgOrderId, int newQuantity) {
        PkgOrder.sendUpdate("UPDATE Pkg_Orders SET Quantity="
                +newQuantity+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void editIdOpen(int pkgOrderId, int newIsOpen) {
        PkgOrder.sendUpdate("UPDATE Pkg_Orders SET Is_Open="
                +newIsOpen+" WHERE Pkg_Order_Id="+pkgOrderId);
    }
    
    public static void deletePkgOrder(int deletePkgOrderId) {
        PkgOrder.sendUpdate("DELETE FROM Pkg_Orders WHERE Pkg_Order_Id="+deletePkgOrderId);
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
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery("SELECT MAX(Pkg_Order_Id) FROM Pkg_Orders");
        if (resultsAL!=null)
            return (int)Double.parseDouble(resultsAL.get(0).get(0).toString())+1;
        else
            return 1;
    }
    
    public static String getPkgOrder(int pkgOrderId) {
        String queryStr = ("SELECT * from PkgOrders WHERE Pkg_Order_Id="+pkgOrderId);
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery(queryStr);
        if (resultsAL!=null)
            return (resultsAL.get(0).get(0).toString());
        else
            return "Package Order not found";
    }
    
    public static String getOpenPkgOrdersByCustomer(String Customer_Id) {
        ArrayList<ArrayList> resultsAL = PkgOrder.sendQuery("SELECT O.Pkg_Order_Id, P.Name, P.Meal_Category, O.Price_Per_Pkg, O.Quantity "
                + "FROM Pkg_Orders O, Packages P "
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
                + "FROM Pkg_Orders "
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
}
