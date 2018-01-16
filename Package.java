

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Package {
    private int packageId;
    private String name;
    private String description;
    private int mealCategory; //0=Breakfast, 1=Lunch, 2=Dinner
    private String imageSource;
    private double price;
    private int isSpecial;
    private int mealType; //0=Veg, 1=Non-Veg, 2=Spicy

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
    
    public static void searchPackageUI(){
        Scanner read = new Scanner(System.in);
        System.out.println(">>>>Search Packages");
        System.out.println("-->Enter 0 to search by packageId\n"
                + "-->Enter 1 to search by package name\n"
                + "-->Enter 2 to search by meal category\n"
                + "-->Enter 3 to search by price\n"
                + "-->Enter 4 to search for specials\n"
                + "-->Enter 5 to search by meal types\n");
        String userInput = read.next();
        if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
            if (userInput.trim().equals("0")) {
                System.out.println("---->Enter the package id to search by");
                userInput = read.next();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Package_Id="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("1")) {
                System.out.println("---->Enter the package name to search by");
                userInput = read.next();
                System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE UPPER(Name)='"+userInput.toUpperCase()+"'")));
            }
            else if (userInput.trim().equals("2")) {
                System.out.println("---->Enter 0 to get all breakfast items, enter 1 to get all lunch items, enter 2 to get all dinner items");
                userInput = read.next();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Meal_Category="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("3")) {
                System.out.println("---->Enter lowest price first and enter highest price second");
                userInput = read.next();
                String userInput2 = read.next();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Price>"+userInput+" AND Price<"+userInput2)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("4")) {
                System.out.println("---->Enter 0 for regular packages and 1 for specials");
                userInput = read.next();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Is_Special="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("5")) {
                System.out.println("---->Enter 0 for Vegetarian, enter 1 for Non-Vegetarian, enter 2 for Spicy");
                userInput = read.next();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Meal_Type="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
        }
        else {
            System.out.println(">>>>Invalid input");
        }
    }
    public static void addPackageUI(){
        Scanner read = new Scanner(System.in);
        String addName="", addDescription="", addMealCategory="", addImageSource="", addPrice="", addIsSpecial="", addMealType="";
        System.out.println(">>>>Add Packages");
        boolean done=false;
        while (done==false) {
            System.out.println("-->Enter the Name of the package: ");
            addName = read.nextLine();
            System.out.println("-->Enter the Description of the package: ");
            addDescription = read.nextLine();
            boolean ok=false;
            while (ok==false){
                System.out.println("-->Enter the Meal Category of the package (0 for Breakfast, 1 for Lunch, 2 for Dinner): ");
                addMealCategory = read.nextLine();
                if (addMealCategory.matches("[-+]?\\d*\\.?\\d+")) {
                    if (Integer.parseInt(addMealCategory)==0 || Integer.parseInt(addMealCategory)==1 || Integer.parseInt(addMealCategory)==2)
                        ok=true;
                    else
                        System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
            }
            System.out.println("-->Enter the Image Source URL of the package (If no image, enter 0): ");
            addImageSource = read.nextLine();
            ok=false;
            while (ok==false){
                System.out.println("-->Enter the Price of the package: ");
                addPrice = read.nextLine();
                if (addPrice.matches("[-+]?\\d*\\.?\\d+"))
                    ok=true;
                else
                    System.out.println(">>>>Invalid Input: you must enter a number");
            }
            ok=false;
            while (ok==false){
                System.out.println("-->Enter the 1 if the package is a special, and 0 if not: ");
                addIsSpecial = read.nextLine();
                if (addIsSpecial.matches("[-+]?\\d*\\.?\\d+")) {
                    if (Integer.parseInt(addIsSpecial)==0 || Integer.parseInt(addIsSpecial)==1)
                        ok=true;
                    else
                        System.out.println(">>>>Invalid Input: you must enter either 0 or 1");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter either 0 or 1");
            }
            ok=false;

            while (ok==false){
                System.out.println("-->Enter the Meal Type of the package (0 for Vegetarian, 1 for Non-Vegetarian, 2 for Spicy): ");
                addMealType = read.nextLine();
                if (addMealType.matches("[-+]?\\d*\\.?\\d+")) {
                    if (Integer.parseInt(addMealType)==0 || Integer.parseInt(addMealType)==1 || Integer.parseInt(addMealType)==2)
                        ok=true;
                    else
                        System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
            }
            System.out.println("Is the below information correct:\nName: "+addName
                    + "\nDescription: "+addDescription+"\nMeal Category (0=Breakfast,1=Lunch,2=Dinner: "+addMealCategory
                    + "\nImage Source: "+addImageSource+"\nPrice: "+addPrice+"\nIs Special(0=Regular,1=Special): "+addIsSpecial
                    + "\nMeal Type: "+addMealType);
            ok=false;
            while (ok==false){
                System.out.println("-->Enter 0 for Not Correct and Re-enter Data, enter 1 for Correct and Add to database");
                String userInput=read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    if (Integer.parseInt(userInput)==1) {
                        Package.addPackage(addName, addDescription, Integer.parseInt(addMealCategory), addImageSource, Double.parseDouble(addPrice), Integer.parseInt(addIsSpecial), Integer.parseInt(addMealType));
                        System.out.println(">>>>Added");
                        done=true;
                        ok=true;
                    }
                    else if (Integer.parseInt(userInput)==0) {
                        ok=true;
                    }
                    else
                        System.out.println(">>>>Invalid Input: you must enter either 0 or 1");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter either 0 or 1");
            }
        }
    }
    
    public static void editDeletePackageUI () {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        while (done==false) {
            System.out.println(">>>>Edit/Delete Packages\n-->Enter 0 to edit and 1 to delete\n");
            String userInput = read.nextLine();

            if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                if (userInput.trim().equals("0")) {
                    System.out.println("---->Enter the package id to edit");
                    userInput = read.nextLine();
                    if (userInput.trim().matches("[-+]?\\d*\\.?\\d+")) {
                        if (Package.sendQuery("SELECT COUNT(*) FROM Packages WHERE Package_Id="+userInput.trim()).equals("1")) {
                            System.out.println("-->Enter 0 to search by packageId\n"
                            + "-->Enter 1 to search by package name\n"
                            + "-->Enter 2 to search by meal category\n"
                            + "-->Enter 3 to search by price\n"
                            + "-->Enter 4 to search for specials\n"
                            + "-->Enter 5 to search by meal types\n");
                        }
                        else
                            System.out.println("Error: Package Id not found");
                    }
                    else 
                        System.out.println("Error: Invalid Input");
                }
                else if (userInput.trim().equals("0")) {
                    System.out.println(">>>>Delete");
                }
            }
        }
    }
}
