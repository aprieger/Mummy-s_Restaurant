import java.sql.*;
import java.util.*;


public class AdminPackageUI {
    public void goToAdminPackageUI() {
        Scanner read = new Scanner(System.in);
        boolean done=false;
        while (done==false) {
            System.out.println("-->Enter 'edit' to edit a package\n"
                    + "-->Enter 'delete' to delete a package\n"
                    + "-->Enter 'search' to search for package\n"
                    + "-->Enter 'add' to add a new package\n"
                    + "-->Enter 'done' to finish editing packages");

            String userInput="";
            userInput = read.nextLine();

            if (userInput.toLowerCase().trim().equals("search")) {
                AdminPackageUI.adminSearchPackageUI();
            }
            else if (userInput.toLowerCase().trim().equals("add")) {
                AdminPackageUI.adminAddPackageUI();
            }
            else if (userInput.toLowerCase().trim().equals("done")) {
                System.out.println(">>>>Done Editing");
                done=true;
            }
            else if (userInput.toLowerCase().trim().equals("edit")) {
                AdminPackageUI.adminEditPackageUI();
            }
            else if (userInput.toLowerCase().trim().equals("delete")) {
                AdminPackageUI.adminDeletePackageUI();
            }
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
        String userInput = read.nextLine();
        if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
            if (userInput.trim().equals("0")) {
                System.out.println("---->Enter the package id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Package_Id="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("1")) {
                System.out.println("---->Enter the package name to search by");
                userInput = read.nextLine();
                System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE UPPER(Name)='"+userInput.toUpperCase()+"'")));
            }
            else if (userInput.trim().equals("2")) {
                System.out.println("---->Enter 0 to get all breakfast items, enter 1 to get all lunch items, enter 2 to get all dinner items");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Meal_Category="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("3")) {
                System.out.println("---->Enter lowest price first and enter highest price second");
                userInput = read.nextLine();
                String userInput2 = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Price>"+userInput+" AND Price<"+userInput2)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("4")) {
                System.out.println("---->Enter 0 for regular packages and 1 for specials");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.printArrayList(Package.sendQuery("SELECT * from Packages WHERE Is_Special="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            else if (userInput.trim().equals("5")) {
                System.out.println("---->Enter 0 for Vegetarian, enter 1 for Non-Vegetarian, enter 2 for Spicy");
                userInput = read.nextLine();
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
    
    public static void adminAddPackageUI(){
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
    
    public static void adminEditPackageUI () {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int editPackageId=0;
        while (done==false) {
            System.out.println("---->Enter the package id to edit");
            String userInput = read.nextLine();
            if (userInput.trim().matches("[-+]?\\d*\\.?\\d+")) {
                ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT COUNT(*) FROM Packages WHERE Package_Id="+userInput.trim());
                if (resultsAL.get(0).get(0).equals("1")) {
                    editPackageId=Integer.parseInt(userInput);
                    System.out.println("Package Info:\n"+Package.getPackageData(userInput));
                    System.out.println("-->Enter 1 to edit package name\n"
                    + "-->Enter 2 to edit description\n"
                    + "-->Enter 3 to edit meal category\n"
                    + "-->Enter 4 to edit price\n"
                    + "-->Enter 5 to edit image source\n"    
                    + "-->Enter 6 to edit special or regular\n"
                    + "-->Enter 7 to edit meal types\n");
                    userInput = read.nextLine();
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        if (userInput.trim().equals("0")) {
                            System.out.println("-->Enter the new Name of the package: ");
                            userInput = read.nextLine();
                            Package.editName(editPackageId, userInput);
                        }
                        else if (userInput.trim().equals("1")) {
                            System.out.println("-->Enter the new Description of the package: ");
                            userInput = read.nextLine();
                            Package.editDescription(editPackageId, userInput);
                        }
                        else if (userInput.trim().equals("2")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new Meal Category of the package (0 for Breakfast, 1 for Lunch, 2 for Dinner): ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    if (Integer.parseInt(userInput)==0 || Integer.parseInt(userInput)==1 || Integer.parseInt(userInput)==2) {
                                        ok=true;
                                        Package.editMealCategory(editPackageId, Integer.parseInt(userInput));
                                    }
                                    else
                                        System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                            }
                        }                                
                        else if (userInput.trim().equals("3")) {
                            System.out.println("-->Enter the Image Source URL of the package (If no image, enter 0): ");
                            userInput = read.nextLine();
                            Package.editImageSource(editPackageId, userInput);
                        }
                        else if (userInput.trim().equals("4")) {
                            boolean ok=false;                           
                            while (ok==false){
                                System.out.println("-->Enter the new Price of the package: ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ok=true;
                                    Package.editPrice(editPackageId, Double.parseDouble(userInput));
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        else if (userInput.trim().equals("5")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the 1 if the package is a special, and 0 if not: ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    if (Integer.parseInt(userInput)==0 || Integer.parseInt(userInput)==1) {
                                        ok=true;
                                        Package.editIsSpecial(editPackageId, Integer.parseInt(userInput));
                                    }
                                    else
                                        System.out.println(">>>>Invalid Input: you must enter either 0 or 1");
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter either 0 or 1");
                            }
                        }  
                        else if (userInput.trim().equals("6")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new Meal Type of the package (0 for Vegetarian, 1 for Non-Vegetarian, 2 for Spicy): ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    if (Integer.parseInt(userInput)==0 || Integer.parseInt(userInput)==1 || Integer.parseInt(userInput)==2) {
                                        ok=true;
                                        Package.editMealType(editPackageId, Integer.parseInt(userInput));
                                    }
                                    else
                                        System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                            }
                        }
                        System.out.println("Updated Package: "+Package.getPackageData(userInput));
                    }
                    else 
                        System.out.println(">>>>Invalid input");
                }
                else
                    System.out.println("Error: Package Id not found");
            }
            else 
                System.out.println("Error: Invalid Input");
        }
    }

    public static void adminDeletePackageUI() {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int deletePackageId=0;
        while (done==false) {
            System.out.println("---->Enter the package id to delete");
            String userInput = read.nextLine();
            if (userInput.trim().matches("[-+]?\\d*\\.?\\d+")) {
                ArrayList<ArrayList> resultsAL = Package.sendQuery("SELECT COUNT(*) FROM Packages WHERE Package_Id="+userInput.trim());
                if (resultsAL.get(0).get(0).equals("1")) {
                    deletePackageId=Integer.parseInt(userInput);
                    System.out.println("Package Info:\n"+Package.getPackageData(userInput)+"\nConfirm Delete: 1 for yes, 0 for no");
                    userInput = read.nextLine();
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        if (userInput.trim().equals("1")) {
                            Package.deletePackage(deletePackageId);
                            System.out.println(">>>>Package deleted");
                            done=true;
                        }
                        else if (userInput.trim().equals("1"))
                            done=true;
                    }
                    else 
                        System.out.println(">>>>Invalid input");
                }
                else
                    System.out.println("Error: Package Id not found");
            }
            else 
                System.out.println("Error: Invalid Input");
        }
    }
}
