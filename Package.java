package Package;

import java.sql.*;
import java.util.*;

public class Package {
    private int packageId;
    private String name;
    private String description;
    private int mealCategory; //Breakfast, Lunch, Dinner
    private String imageSource;
    private double price;
    private int isSpecial;
    private int mealType; //Veg, Non-Veg, Spicy

    public Package() {
        this.packageId = 0;
        this.name = null;
        this.description = null;
        this.mealCategory = 0;
        this.imageSource = null;
        this.price = 0;
        this.isSpecial = 0;
        this.mealType = 0;
    }
    
    public Package(int packageId, String name, String description, 
            int mealCategory, String imageSource, double price, int isSpecial, 
            int mealType) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.mealCategory = mealCategory;
        this.imageSource = imageSource;
        this.price = price;
        this.isSpecial = isSpecial;
        this.mealType = mealType;
    }
    
    public static void addPackage(String addName, String addDescription, 
            int addMealCategory, String addImageSource, double addPrice, 
            int addIsSpecial, int addMealType) {

            String updateStr = ("INSERT INTO Packages (Package_Id, Name, Description, Meal_Category, Image_Source, Price, Is_Special, Meal_Type)"
                    + " VALUES ("+Package.getNextPackageId()+",'"+addName+"','"+addDescription+"',"+addMealCategory+",'"+addImageSource+"',"+addPrice+","+addIsSpecial+","+addMealType+")");
            Package.sendUpdate(updateStr);
    }
    
    public static void editPackage(String editPackageId, String columnNameSQL, 
            String editNewData, boolean isString) {
        if (isString==true)    
            Package.sendUpdate("UPDATE Packages SET "+columnNameSQL+"='"
                    +editNewData+"' WHERE Package_Id="+editPackageId);
        else
            Package.sendUpdate("UPDATE Packages SET "+columnNameSQL+"="
                    +editNewData+" WHERE Package_Id="+editPackageId);
    }
    
    public static void deletePackage(int deletePackageId) {
        Package.sendUpdate("DELETE FROM Packages WHERE Package_Id="+deletePackageId);
    }
    
    public static String printAllPackages() {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT * from Packages");
        String output=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package ID","|Name",
                "|Description","|Meal Category","|Image Source","|Price",
                "|Is Special?","|Meal Type");
        int aLIndex = 0;
        while (resultsAL.size()>aLIndex) {
            int subALIndex = 0;
            while (resultsAL.get(aLIndex).size()>subALIndex) {
                output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                subALIndex++;
            }
            aLIndex++;
            output += "\n";
        }
        return output;
    }
    
    public static String printArrayList(ArrayList<ArrayList> resultsAL){
        String output = "";
        int aLCount=0;
        while (resultsAL.size()>aLCount) {
            int subALCount=0;
            while (resultsAL.get(aLCount).size()>subALCount) {
                output += resultsAL.get(aLCount).get(subALCount).toString() + " | ";
                subALCount++;
            }
            output += "\n";
            aLCount++;
        }
        return output;
    }
    
    public static String printMenu() {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT Package_Id, Name, Description, Meal_Category, Price, Is_Special, Meal_Type from Packages");
        String output=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package Number","|Name",
                "|Description","|Meal Category","|Price", "|Specialty Item","|Meal Type");
        int aLIndex = 0;
        while (resultsAL.size()>aLIndex) {
            int subALIndex = 0;
            while (resultsAL.get(aLIndex).size()>subALIndex) {
                switch (subALIndex) {
                    case 0:
                        output += String.format("%-20s","|"+(int)(double)resultsAL.get(aLIndex).get(subALIndex)).toString();
                        break;
                    case 1:
                        output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                        break;
                    case 2:
                        output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                        break;    
                    case 3:
                        if ((double)resultsAL.get(aLIndex).get(subALIndex)==0.0)
                            output += String.format("%-20s","|Breakfast");
                        else if ((double)resultsAL.get(aLIndex).get(subALIndex)==1.0)
                            output += String.format("%-20s","|Lunch");
                        else
                            output += String.format("%-20s","|Dinner");
                        break; 
                    case 4:
                        output += String.format("%-20s","|$"+resultsAL.get(aLIndex).get(subALIndex));
                        break; 
                    case 5:
                        if ((double)resultsAL.get(aLIndex).get(subALIndex)==0.0)
                            output += String.format("%-20s","|No");
                        else
                            output += String.format("%-20s","|Yes");
                        break;
                    case 6:
                        if ((double)resultsAL.get(aLIndex).get(subALIndex)==0.0)
                            output += String.format("%-20s","|Vegetarian");
                        else if ((double)resultsAL.get(aLIndex).get(subALIndex)==1.0)
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
        return output;
    }
    
    public String printPackageObject() {
        String output = "Name: "+this.name+"\nDesription: "+this.description+"\nMeal Category: ";
        if (this.mealCategory == 0)
            output += "Breakfast";
        else if (this.mealCategory == 1)
            output += "Lunch";
        else if (this.mealCategory == 2)
            output += "Dinner";
        else 
            output += "Other";
        output += "\nPrice: "+this.price+"\nSpecialty Item: ";
        if (this.mealCategory == 0)
            output += "No";
        else if (this.mealCategory == 1)
            output += "Yes";
        else 
            output += "";
        output += "\nMeal Type: ";
        if (this.mealType == 0)
            output += "Vegetarian";
        else if (this.mealType == 1)
            output += "Non Vegetarian";
        else if (this.mealType == 2)
            output += "Spicy";
        else 
            output += "Other";
        return output;
    }
    
    public static String printPackage(int inputPackageId) {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT Name, Description, Meal_Category, Price, Is_Special, Meal_Type from Packages WHERE Package_Id="+inputPackageId);
        String output = "Name: "+resultsAL.get(0).get(0)+"\nDesription: "+resultsAL.get(0).get(1)+"\nMeal Category: ";
        if ((double)resultsAL.get(0).get(2) == 0.0)
            output += "Breakfast";
        else if ((double)resultsAL.get(0).get(2) == 1.0)
            output += "Lunch";
        else if ((double)resultsAL.get(0).get(2) == 2.0)
            output += "Dinner";
        else 
            output += "Other";
        output += "\nPrice: "+resultsAL.get(0).get(3)+"\nSpecialty Item: ";
        if ((double)resultsAL.get(0).get(4) == 0.0)
            output += "No";
        else if ((double)resultsAL.get(0).get(4) == 1.0)
            output += "Yes";
        else 
            output += "";
        output += "\nMeal Type: ";
        if ((double)resultsAL.get(0).get(5) == 0.0)
            output += "Vegetarian";
        else if ((double)resultsAL.get(0).get(5) == 1.0)
            output += "Non Vegetarian";
        else if ((double)resultsAL.get(0).get(5) == 2.0)
            output += "Spicy";
        else 
            output += "Other";
        return output;
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
            return resultsAL;
        } catch (Exception ex) {return null;}
    }
    
    public static void sendUpdate(String queryStr) {
        try {
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","hr","hr");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(queryStr);
        } catch (Exception ex) {}
    }
    
    public static int getNextPackageId() {
        ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT MAX(Package_Id) FROM Packages");
        return (int)Double.parseDouble(resultsAL.get(0).get(0).toString())+1;
    }
    
    public static int isNameInTable(int existName) {
        String queryStr = ("Select COUNT(*) from Packages WHERE Name="+existName);
        ArrayList<ArrayList> resultsAL = Package.sendQuery(queryStr);
        return Integer.parseInt(resultsAL.get(0).get(0).toString());
    }
    
    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(int mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int isIs_Special() {
        return isSpecial;
    }

    public void setIsSpecial(int isSpecial) {
        this.isSpecial = isSpecial;
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }  
}
