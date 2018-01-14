import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Package {  
    public static void addPackage(String addName, String addDescription, 
            int addMealCategory, String addImageSource, double addPrice, 
            int addIsSpecial, int addMealType) {

            String updateStr = ("INSERT INTO Packages (Package_Id, Name, Description, Meal_Category, Image_Source, Price, Is_Special, Meal_Type)"
                    + " VALUES ("+Package.getNextPackageId()+",'"+addName+"','"+addDescription+"',"+addMealCategory+",'"+addImageSource+"',"+addPrice+","+addIsSpecial+","+addMealType+")");
            Package.sendUpdate(updateStr);
    }
    
    public static void editName(int editPackageId, String newName) {
        Package.sendUpdate("UPDATE Packages SET Name='"
                +newName+"' WHERE Package_Id="+editPackageId);
    }
    
    public static void editDescription(int editPackageId, String newDescription) {
        Package.sendUpdate("UPDATE Packages SET Description='"
                +newDescription+"' WHERE Package_Id="+editPackageId);
    }
    
    public static void editMealCategory(int editPackageId, int newMealCategory) {
        Package.sendUpdate("UPDATE Packages SET Meal_Category="
                +newMealCategory+" WHERE Package_Id="+editPackageId);
    }
    
    public static void editImageSource(int editPackageId, String newImageSource) {
        Package.sendUpdate("UPDATE Packages SET Image_Source='"
                +newImageSource+"' WHERE Package_Id="+editPackageId);
    }
    
    public static void editPrice(int editPackageId, double newPrice) {
        Package.sendUpdate("UPDATE Packages SET Price="
                +newPrice+" WHERE Package_Id="+editPackageId);
    }
    
    public static void editIsSpecial(int editPackageId, int newIsSpecial) {
        Package.sendUpdate("UPDATE Packages SET Is_Special="
                +newIsSpecial+" WHERE Package_Id="+editPackageId);
    }
    
    public static void editMealType(int editPackageId, int newMealType) {
        Package.sendUpdate("UPDATE Packages SET Meal_Type="
                +newMealType+" WHERE Package_Id="+editPackageId);
    }
    
    
    public static void deletePackage(int deletePackageId) {
        Package.sendUpdate("DELETE FROM Packages WHERE Package_Id="+deletePackageId);
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
    
    public static String isNameInTable(String existName) {
        String queryStr = ("Select COUNT(*) from Packages WHERE Name="+existName);
        ArrayList<ArrayList> resultsAL = Package.sendQuery(queryStr);
        if (resultsAL!=null)
            return (resultsAL.get(0).get(0).toString());
        else
            return "Name not found";
    }
    
    public static int getNextPackageId() {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT MAX(Package_Id) FROM Packages");
        if (resultsAL!=null)
            return Integer.parseInt(resultsAL.get(0).get(0).toString())+1;
        else
            return 1;
    }
    
    public static String getAllPackages() {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT * from Packages");
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
    
    public static String printArrayList(ArrayList<ArrayList> resultsAL){
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
    
    public static String getMenu() {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT Package_Id, Name, Description, Meal_Category, Price, Is_Special, Meal_Type from Packages");
        String output=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package Number","|Name",
                "|Description","|Meal Category","|Price", "|Specialty Item","|Meal Type");
        int aLIndex = 0;
        if (resultsAL!=null) {
            while (resultsAL.size()>aLIndex) {
                int subALIndex = 0;
                while (resultsAL.get(aLIndex).size()>subALIndex) {
                    switch (subALIndex) {
                        case 0:
                            output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                            break;
                        case 1:
                            output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                            break;
                        case 2:
                            output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                            break;    
                        case 3:
                            if ((int)resultsAL.get(aLIndex).get(subALIndex)==0)
                                output += String.format("%-20s","|Breakfast");
                            else if ((int)resultsAL.get(aLIndex).get(subALIndex)==1)
                                output += String.format("%-20s","|Lunch");
                            else
                                output += String.format("%-20s","|Dinner");
                            break; 
                        case 4:
                            output += String.format("%-20s","|$"+resultsAL.get(aLIndex).get(subALIndex));
                            break; 
                        case 5:
                            if ((int)resultsAL.get(aLIndex).get(subALIndex)==0)
                                output += String.format("%-20s","|No");
                            else
                                output += String.format("%-20s","|Yes");
                            break;
                        case 6:
                            if ((int)resultsAL.get(aLIndex).get(subALIndex)==0)
                                output += String.format("%-20s","|Vegetarian");
                            else if ((int)resultsAL.get(aLIndex).get(subALIndex)==1)
                                output += String.format("%-20s","|Non-Vegetarian");
                            else
                                output += String.format("%-20s","|Spicy");
                            break;
                        default:
                            break;
                    }
                    subALIndex++;
                }
                aLIndex++;
                output += "\n";
            }
        }
        return output;
    }
    
    public static String getPackageString(String inputPackageId) {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT Name, Description, Meal_Category, Price, Is_Special, Meal_Type from Packages WHERE Package_Id="+inputPackageId);
        if (resultsAL!=null) {
            String output = "Name: "+resultsAL.get(0).get(0)+"\nDesription: "+resultsAL.get(0).get(1)+"\nMeal Category: ";
            if ((int)resultsAL.get(0).get(2) == 0)
                output += "Breakfast";
            else if ((int)resultsAL.get(0).get(2) == 1)
                output += "Lunch";
            else if ((int)resultsAL.get(0).get(2) == 2)
                output += "Dinner";
            else 
                output += "Other";
            output += "\nPrice: "+resultsAL.get(0).get(3)+"\nSpecialty Item: ";
            if ((int)resultsAL.get(0).get(4) == 0)
                output += "No";
            else if ((int)resultsAL.get(0).get(4) == 1)
                output += "Yes";
            else 
                output += "";
            output += "\nMeal Type: ";
            if ((int)resultsAL.get(0).get(5) == 0)
                output += "Vegetarian";
            else if ((int)resultsAL.get(0).get(5) == 1)
                output += "Non Vegetarian";
            else if ((int)resultsAL.get(0).get(5) == 2.)
                output += "Spicy";
            else 
                output += "Other";
            return output;
        }
        else 
            return "";
    }
    
    public static String getPackageData(String inputPackageId) {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT Name, Description, Meal_Category, Price, Is_Special, Meal_Type from Packages WHERE Package_Id="+inputPackageId);
        if (resultsAL!=null) {
            String output = "Name: "+resultsAL.get(0).get(0)+"\nDesription: "+resultsAL.get(0).get(1)+"\nMeal Category: ";
            if ((int)resultsAL.get(0).get(2) == 0)
                output += "Breakfast";
            else if ((int)resultsAL.get(0).get(2) == 1)
                output += "Lunch";
            else if ((int)resultsAL.get(0).get(2) == 2)
                output += "Dinner";
            else 
                output += "Other";
            output += "\nPrice: "+resultsAL.get(0).get(3)+"\nSpecialty Item: ";
            if ((int)resultsAL.get(0).get(4) == 0)
                output += "No";
            else if ((int)resultsAL.get(0).get(4) == 1)
                output += "Yes";
            else 
                output += "";
            output += "\nMeal Type: ";
            if ((int)resultsAL.get(0).get(5) == 0)
                output += "Vegetarian";
            else if ((int)resultsAL.get(0).get(5) == 1)
                output += "Non Vegetarian";
            else if ((int)resultsAL.get(0).get(5) == 2.)
                output += "Spicy";
            else 
                output += "Other";
            return output;
        }
        else 
            return "";
    }
}
