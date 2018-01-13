import java.sql.*;
import java.util.*;


public class CustomerPackageUI {
    Scanner read = new Scanner(System.in);
    public void goToPackageUI(String packageID) {
        System.out.println(Package.getPackageString(packageID));
        System.out.println("-->Enter 0 to add the package to cart\n"
                + "-->Enter 1 to go back to the menu");

        String userInput="";
        userInput = read.next();

        if (userInput.equals("0")) {
            System.out.println(">>>>Package added to cart");
            //PkgOrder.addOpenPkgOrder();
            //TODO: Create a PkgOrder and back to menu
        }
        else if (userInput.matches("1")) {
            System.out.println(">>>>Back to menu");
            //TODO: Just go back to the menu
        }
    }
    
    public static void adminSearchPackageUI(){
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
}