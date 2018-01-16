import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;

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

            String userInput;
            userInput = read.nextLine().trim();
            
            if (userInput.toLowerCase().equals("edit")) {
                AdminPackageUI.adminEditPackageUI();
            }
            else if (userInput.toLowerCase().equals("delete")) {
                AdminPackageUI.adminDeletePackageUI();
            }
            else if (userInput.toLowerCase().equals("search")) {
                AdminPackageUI.adminSearchPackageUI();
            }
            else if (userInput.toLowerCase().equals("add")) {
                AdminPackageUI.adminAddPackageUI();
            }
            else if (userInput.toLowerCase().equals("done")) {
                System.out.println(">>>>Done Editing");
                done=true;
            }
            else
                System.out.println(">>>>Invalid Input");
        }
    }
        
    public static void adminSearchPackageUI(){
        Scanner read = new Scanner(System.in);
        System.out.println(">>>>Search Packages");
        System.out.println("-->Enter 1 to search by packageId\n"
                + "-->Enter 2 to search by package name\n"
                + "-->Enter 3 to search by meal category\n"
                + "-->Enter 4 to search by price\n"
                + "-->Enter 5 to search for specials\n"
                + "-->Enter 6 to search by meal types\n");
        String userInput = read.nextLine().trim();
        //Check if input is a number
        if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
            //User enters 1, search by package id
            if (userInput.equals("1")) {
                System.out.println("---->Enter the package id to search by");
                userInput = read.nextLine().trim();
                //Check if the package id entered is a number
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Package_Id="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input: must enter a number");
            }
            //Users enters 2, search by package name
            else if (userInput.equals("2")) {
                System.out.println("---->Enter the package name to search by");
                userInput = read.nextLine().trim();
                System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE LOWER(Name)='"+userInput.toLowerCase()+"'")));
            }
            //User enters 3, search by meal category
            else if (userInput.equals("3")) {
                System.out.println("---->Enter 0 to get all breakfast items, enter 1 to get all lunch items, enter 2 to get all dinner items");
                userInput = read.nextLine().trim();
                //Check if user entered a number
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Meal_Category="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input: must enter a number");
            }
            //User enters 4, search between lowest price and highest price
            else if (userInput.equals("4")) {
                System.out.println("---->Enter lowest price first and enter highest price second");
                userInput = read.nextLine().trim();
                String userInput2 = read.nextLine().trim();
                //check if both inputs are numbers
                if (userInput.matches("[-+]?\\d*\\.?\\d+") && userInput2.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Price>"+userInput+" AND Price<"+userInput2)));
                }
                else
                    System.out.println(">>>>Invalid Input: must enter a number");
            }
            //User enters 5, search by specials
            else if (userInput.equals("5")) {
                System.out.println("---->Enter 0 for regular packages and 1 for specials");
                userInput = read.nextLine().trim();
                //Check if input is a number
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Is_Special="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input");
            }
            //User enters 6, search by meal type
            else if (userInput.equals("6")) {
                System.out.println("---->Enter 0 for Vegetarian, enter 1 for Non-Vegetarian, enter 2 for Spicy");
                userInput = read.nextLine().trim();
                //Check if input is a number
                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from Packages WHERE Meal_Type="+userInput)));
                }
                else
                    System.out.println(">>>>Invalid Input: must enter a number");
            }
            else
                System.out.println(">>>>Invalid input: must enter a number 1-6");
        }
        else {
            System.out.println(">>>>Invalid input: must enter a number");
        }
    }
    
    public static void adminAddPackageUI(){
        Scanner read = new Scanner(System.in);
        String addName="", addDescription="", addMealCategory="", addImageSource="", addPrice="", addIsSpecial="", addMealType="";
        System.out.println(">>>>Add Packages");
        boolean done=false;
        while (done==false) {
            System.out.println("-->Enter the Name of the package: ");
            addName = read.nextLine().trim();
            System.out.println("-->Enter the Description of the package: ");
            addDescription = read.nextLine().trim();
            boolean ok=false;
            while (ok==false){
                System.out.println("-->Enter the Meal Category of the package (0 for Breakfast, 1 for Lunch, 2 for Dinner): ");
                addMealCategory = read.nextLine().trim();
                //Check if user input is a number
                if (addMealCategory.matches("[-+]?\\d*\\.?\\d+")) {
                    //Check if the input is a 0, 1 or 2
                    if (Integer.parseInt(addMealCategory)==0 || Integer.parseInt(addMealCategory)==1 || Integer.parseInt(addMealCategory)==2)
                        ok=true;
                    else
                        System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
            }
            System.out.println("-->Enter the Image Source URL of the package (If no image, enter 0): ");
            addImageSource = read.nextLine().trim();
            ok=false;
            while (ok==false){
                System.out.println("-->Enter the Price of the package: ");
                addPrice = read.nextLine().trim();
                //check if the input is a number
                if (addPrice.matches("[-+]?\\d*\\.?\\d+"))
                    ok=true;
                else
                    System.out.println(">>>>Invalid Input: you must enter a number");
            }
            ok=false;
            while (ok==false){
                System.out.println("-->Enter the 1 if the package is a special, and 0 if not: ");
                addIsSpecial = read.nextLine().trim();
                //check if input is a number
                if (addIsSpecial.matches("[-+]?\\d*\\.?\\d+")) {
                    //check if input is either 1 or 2
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
                addMealType = read.nextLine().trim();
                //check if the input is a number
                if (addMealType.matches("[-+]?\\d*\\.?\\d+")) {
                    //check if the input is a 0, 1 or 2
                    if (Integer.parseInt(addMealType)==0 || Integer.parseInt(addMealType)==1 || Integer.parseInt(addMealType)==2)
                        ok=true;
                    else
                        System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter either 0, 1 or 2");
            }
            //Display the package info before editing
            System.out.println("Is the below information correct:\nName: "+addName
                    + "\nDescription: "+addDescription+"\nMeal Category (0=Breakfast,1=Lunch,2=Dinner: "+addMealCategory
                    + "\nImage Source: "+addImageSource+"\nPrice: "+addPrice+"\nIs Special(0=Regular,1=Special): "+addIsSpecial
                    + "\nMeal Type: "+addMealType);
            ok=false;
            while (ok==false){
                System.out.println("-->Enter 0 for Not Correct and Re-enter Data, enter 1 for Correct and Add to database");
                String userInput=read.nextLine().trim();
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
            String userInput = read.nextLine().trim();
            if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM Packages WHERE Package_Id="+userInput);
                if (!resultsAL.isEmpty()) {
                    editPackageId=Integer.parseInt(userInput);
                    System.out.println("Package Info:\n"+Package.getStringFromJSON(Package.getSinglePackageData(editPackageId)));
                    System.out.println("-->Enter 1 to edit package name\n"
                    + "-->Enter 2 to edit description\n"
                    + "-->Enter 3 to edit meal category\n"
                    + "-->Enter 4 to edit price\n"
                    + "-->Enter 5 to edit image source\n"    
                    + "-->Enter 6 to edit special or regular\n"
                    + "-->Enter 7 to edit meal types");
                    userInput = read.nextLine().trim();
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        //Edit name
                        if (userInput.equals("1")) {
                            System.out.println("-->Enter the new Name of the package: ");
                            userInput = read.nextLine().trim();
                            Package.editName(editPackageId, userInput);
                        }
                        //Edit description
                        else if (userInput.equals("2")) {
                            System.out.println("-->Enter the new Description of the package: ");
                            userInput = read.nextLine().trim();
                            Package.editDescription(editPackageId, userInput);
                        }
                        //edit meal category
                        else if (userInput.equals("3")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new Meal Category of the package (0 for Breakfast, 1 for Lunch, 2 for Dinner): ");
                                userInput = read.nextLine().trim();
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
                        //edit image source
                        else if (userInput.equals("4")) {
                            System.out.println("-->Enter the Image Source URL of the package (If no image, enter 0): ");
                            userInput = read.nextLine().trim();
                            Package.editImageSource(editPackageId, userInput);
                        }
                        //edit price
                        else if (userInput.equals("5")) {
                            boolean ok=false;                           
                            while (ok==false){
                                System.out.println("-->Enter the new Price of the package: ");
                                userInput = read.nextLine().trim();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ok=true;
                                    Package.editPrice(editPackageId, Double.parseDouble(userInput));
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        //edit special
                        else if (userInput.equals("6")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the 1 if the package is a special, and 0 if not: ");
                                userInput = read.nextLine().trim();
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
                        //edit meal type
                        else if (userInput.equals("7")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new Meal Type of the package (0 for Vegetarian, 1 for Non-Vegetarian, 2 for Spicy): ");
                                userInput = read.nextLine().trim();
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
                        System.out.println("\n>>>>Updated Package: \n"+Package.getStringFromJSON(Package.getSinglePackageData(editPackageId)));
                    }
                    else 
                        System.out.println(">>>>Invalid input");
                }
                else
                    System.out.println(">>>>Error: Package Id not found");
            }
            else 
                System.out.println(">>>>Error: Invalid Input");
        }
    }

    public static void adminDeletePackageUI() {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int deletePackageId;
        while (done==false) {
            System.out.println("---->Enter the package id to delete");
            String userInput = read.nextLine().trim();
            if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM Packages WHERE Package_Id="+userInput);
                if (!resultsAL.isEmpty()) {
                    deletePackageId=Integer.parseInt(userInput);
                    System.out.println("Package Info:\n"+Package.getStringFromJSON(Package.getSinglePackageData(deletePackageId))+"\nConfirm Delete: 1 for yes, 0 for no");
                    userInput = read.nextLine().trim();
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        if (userInput.equals("1")) {
                            Package.deletePackage(deletePackageId);
                            System.out.println(">>>>Package deleted");
                            done=true;
                        }
                        else if (userInput.equals("1"))
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
