import java.util.Scanner;

public class CustomerPackageUI {
    public void goToPackageUI(int packageId, int customerId) {
        Scanner read = new Scanner(System.in);
        System.out.println(Package.getStringFromJSON(Package.getSinglePackageData(packageId)));
        System.out.println("-->Enter 1 to add the package to cart\n"
                + "-->Enter 2 to go back to the menu");

        String userInput;
        userInput = read.nextLine().trim();

        if (userInput.equals("1")) {
            int quantity;
            System.out.println("-->Enter the quantity of packages");
            userInput = read.nextLine();
            boolean ok=false;
            while (ok==false) {
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    quantity = Integer.parseInt(userInput);
                    PkgOrder.addOpenPkgOrder(packageId, customerId, quantity);
                    System.out.println(">>>>Package added to cart");
                    ok = true;
                }
                else
                    System.out.println(">>>>Invalid Input: must enter a number");
            }
        }
        else if (userInput.matches("2")) {
            System.out.println(">>>>Back to menu");
        }
    }
    
    public static void searchPackageUI(){
        Scanner read = new Scanner(System.in);
        System.out.println(">>>>Search Packages");
        System.out.println("-->Enter 1 to search by packageId\n"
                + "-->Enter 2 to search by package name\n"
                + "-->Enter 3 to search by meal category\n"
                + "-->Enter 4 to search by price\n"
                + "-->Enter 5 to search for specials\n"
                + "-->Enter 6 to search by meal types\n");
        String userInput = read.nextLine().trim();
        if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
            if (userInput.equals("1")) {
                System.out.println("---->Enter the package id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Package_Id="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.equals("2")) {
                System.out.println("---->Enter the package name to search by");
                userInput = read.nextLine();
                System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE UPPER(Name)='"+userInput.toUpperCase()+"'")));
            }
            else if (userInput.equals("3")) {
                System.out.println("---->Enter 0 to get all breakfast items, enter 1 to get all lunch items, enter 2 to get all dinner items");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Meal_Category="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.equals("4")) {
                System.out.println("---->Enter lowest price first and enter highest price second");
                userInput = read.nextLine();
                String userInput2 = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Price>"+userInput+" AND Price<"+userInput2)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.equals("5")) {
                System.out.println("---->Enter 0 for regular packages and 1 for specials");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Is_Special="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.equals("6")) {
                System.out.println("---->Enter 0 for Vegetarian, enter 1 for Non-Vegetarian, enter 2 for Spicy");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Meal_Type="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else
                System.out.println(">>>>Invalid Input: must enter 1, 2, 3, 4, 5 or 6");
        }
        else {
            System.out.println(">>>>Invalid input: must enter a number");
        }
    }
}