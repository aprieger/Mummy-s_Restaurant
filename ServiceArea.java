

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class ServiceArea {
    public static void addServiceArea(String name, int areaCode, int packageId, double taxRate) {
            String updateStr = ("INSERT INTO Service_Areas (Service_Area_Id, Name, Area_Code, Package_Id, Tax_Rate)"
                    + " VALUES ("+ServiceArea.getNextServiceAreaId()+",'"+name+"','"+packageId+"',"+areaCode+",'"+packageId+"',"+taxRate+")");
            ServiceArea.sendUpdate(updateStr);
    }
    
    public static void editPackageId(int editServiceAreaId, int newPackageId) {
        ServiceArea.sendUpdate("UPDATE Service_Areas SET Package_Id="
                +newPackageId+" WHERE Service_Area_Id="+editServiceAreaId);
    }
    
    public static void editName(int editServiceAreaId, String newName) {
        ServiceArea.sendUpdate("UPDATE Service_Areas SET Name='"
                +newName+"' WHERE Service_Area_Id="+editServiceAreaId);
    }
    
    public static void editAreaCode(int editServiceAreaId, int newAreaCode) {
        ServiceArea.sendUpdate("UPDATE Service_Areas SET Area_Code="
                +newAreaCode+" WHERE Service_Area_Id="+editServiceAreaId);
    }
    
    public static void editTaxRate(int editServiceAreaId, double newTaxRate) {
        ServiceArea.sendUpdate("UPDATE Service_Areas SET Tax_Rate='"
                +newTaxRate+" WHERE Service_Area_Id="+editServiceAreaId);
    }
    
    public static void editServiceArea(String editServiceAreaId, String columnNameSQL, 
            String editNewData, boolean isString) {
        if (isString==true)    
            ServiceArea.sendUpdate("UPDATE Service_Areas SET "+columnNameSQL+"='"
                    +editNewData+"' WHERE Service_Area_Id="+editServiceAreaId);
        else
            ServiceArea.sendUpdate("UPDATE Service_Areas SET "+columnNameSQL+"="
                    +editNewData+" WHERE Service_Area_Id="+editServiceAreaId);
    }
    
    public static void deleteServiceArea(int deleteServiceAreaId) {
        ServiceArea.sendUpdate("DELETE FROM Service_Areas WHERE Service_Area_Id="+deleteServiceAreaId);
    }
    
    public static int searchPkgOrderIdByName(String searchName) {
        ArrayList<ArrayList> resultsAL = ServiceArea.sendQuery("SELECT Pkg_Order_Id FROM Pkg_Orders WHERE Name='"+searchName+"'");
        if (resultsAL!=null)
            return Integer.parseInt(resultsAL.get(0).get(0).toString())+1;
        else
            return 0;
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
                    switch (rsmd.getColumnClassName(columnIndex)) {
                        case ("java.math.BigDecimal"): 
                            subAL.add(rs.getInt(columnIndex));
                            break;
                        case ("java.lang.String"): 
                            subAL.add(rs.getString(columnIndex));
                            break;
                        case ("java.math.BigDouble"): 
                            subAL.add(rs.getDouble(columnIndex));
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
    
    public static int getNextServiceAreaId() {
        ArrayList<ArrayList> resultsAL = ServiceArea.sendQuery("SELECT MAX(Pkg_Order_Id) FROM Pkg_Orders");
        if (resultsAL!=null)
            return (int)Double.parseDouble(resultsAL.get(0).get(0).toString())+1;
        else
            return 1;
    }
}
