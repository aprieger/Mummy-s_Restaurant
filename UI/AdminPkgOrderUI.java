package app.UI;
import app.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;

public class AdminPkgOrderUI {
    public void goToAdminPkgOrderUI() {
        Scanner read = new Scanner(System.in);
        boolean done=false;
        while (done==false) {
            System.out.println("-->Enter 'edit' to edit a service area\n"
                    + "-->Enter 'delete' to delete a service area\n"
                    + "-->Enter 'search' to search for service area\n"
                    + "-->Enter 'done' to finish editing service area");

            String userInput;
            userInput = read.nextLine().trim();

            if (userInput.toLowerCase().equals("edit")) {
                AdminPkgOrderUI.adminEditPkgOrderUI();
            }
            else if (userInput.toLowerCase().equals("delete")) {
                AdminPkgOrderUI.adminDeletePkgOrderUI();
            }
            else if (userInput.toLowerCase().equals("search")) {
                AdminPkgOrderUI.adminSearchPkgOrderUI();
            }
            else if (userInput.toLowerCase().equals("done")) {
                System.out.println(">>>>Done Editing");
                done=true;
            }
        }
    }
    
    public static void adminEditPkgOrderUI () {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int editPkgOrderId=0;
        while (done==false) {
            System.out.println("---->Enter the pkg order id to edit");
            String userInput = read.nextLine().trim();
            //Check if input is a number
            if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                //Send the query and get the results of the count of pkg orders with the specified pkg order id
                ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM PkgOrders WHERE Pkg_Order_Id="+userInput);
                if (!resultsAL.isEmpty()) {
                    editPkgOrderId=Integer.parseInt(userInput);
                    System.out.println("Service Area Info:\n"+PkgOrder.getStringFromJSON(PkgOrder.getSinglePkgOrder(editPkgOrderId)));
                    System.out.println("-->Enter 1 to edit order id\n"
                            + "-->Enter 2 to edit package id\n"
                            + "-->Enter 3 to edit price per package\n"
                            + "-->Enter 4 to edit quantity\n"
                            + "-->Enter 5 to edit customer id\n"
                            + "-->Enter 6 to edit if the pkg order is open");
                    userInput = read.nextLine();
                    //Check if input is a number
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        //Edit order id
                        if (userInput.equals("1")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new order id: ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ArrayList<JSONObject> orderIdResultsAL = Utilities.sendQuery("SELECT * FROM Orders WHERE Order_Id="+userInput);
                                    if (!orderIdResultsAL.isEmpty()) {
                                        PkgOrder.editOrderId(editPkgOrderId, Integer.parseInt(userInput));
                                        ok=true;
                                    }
                                    else 
                                        System.out.println(">>>>Order Id not found");   
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        //Edit Package Id
                        else if (userInput.equals("2")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new package id: ");
                                userInput = read.nextLine();
                                //Check if input is a number
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ArrayList<JSONObject> packageIdResultsAL = Utilities.sendQuery("SELECT * FROM Packages WHERE Package_Id="+userInput);
                                    //Check if Package Id is in the Packages table
                                    if (!packageIdResultsAL.isEmpty()) {
                                        ServiceArea.editPackageId(editPkgOrderId, Integer.parseInt(userInput));
                                        ok=true;
                                    }
                                    else
                                        System.out.println(">>>>Package Id not found");
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        //Edit quantity
                        else if (userInput.equals("3")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new quantity: ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    PkgOrder.editQuantity(editPkgOrderId, Integer.parseInt(userInput));
                                    ok=true;
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        System.out.println("Updated Pkg Order: "+PkgOrder.getStringFromJSON(PkgOrder.getSinglePkgOrder(editPkgOrderId)));
                    }
                    else 
                        System.out.println(">>>>Invalid input");
                }
                else
                    System.out.println("Error: Service Area Id not found");
            }
            else 
                System.out.println("Error: Invalid Input");
        }
    }

    public static void adminDeletePkgOrderUI() {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int deletePkgOrderId=0;
        while (done==false) {
            System.out.println("---->Enter the pkg order id to delete");
            String userInput = read.nextLine();
            //Check if the user input is a number
            if (userInput.trim().matches("[-+]?\\d*\\.?\\d+")) {
                //Check if the pkg order exists in the table
                ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM PkgOrders WHERE Pkg_Order_Id="+userInput.trim());
                if (!resultsAL.isEmpty()) {
                    deletePkgOrderId=Integer.parseInt(userInput);
                    System.out.println("Pkg Order Info:\n"+PkgOrder.getStringFromJSON(PkgOrder.getSinglePkgOrder(deletePkgOrderId))+"\nConfirm Delete: 1 for yes, 0 for no");
                    userInput = read.nextLine();
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        if (userInput.trim().equals("1")) {
                            PkgOrder.deletePkgOrder(deletePkgOrderId);
                            System.out.println(">>>>Pkg Order deleted");
                            done=true;
                        }
                        else if (userInput.trim().equals("1"))
                            done=true;
                    }
                    else 
                        System.out.println(">>>>Invalid input");
                }
                else
                    System.out.println("Error: Pkg Order Id not found");
            }
            else 
                System.out.println("Error: Invalid Input");
        }
    }
    
    public static void adminSearchPkgOrderUI(){
        Scanner read = new Scanner(System.in);
        System.out.println(">>>>Search Pkg Orders");
        System.out.println("-->Enter 1 to search by pkg order id\n"
                + "-->Enter 2 to search by order id\n"
                + "-->Enter 3 to search by package id\n"
                + "-->Enter 4 to search by customer id\n");
        String userInput = read.nextLine().trim();
        if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
            //Search by service area id
            if (userInput.equals("1")) {
                System.out.println("---->Enter the pkg order id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(PkgOrder.getStringFromJSON(Utilities.sendQuery("SELECT * from PkgOrders WHERE Pkg_Order_Id="+userInput)));
                else
                    System.out.println(">>>>Invalid Input");
            }
            //Search by service area name
            else if (userInput.equals("2")) {
                System.out.println("---->Enter the customer id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(PkgOrder.getStringFromJSON(Utilities.sendQuery("SELECT * from PkgOrders WHERE Customer_Id="+userInput)));
                else
                    System.out.println(">>>>Invalid Input");
            }
            //Search by service area code
            else if (userInput.equals("3")) {
                System.out.println("---->Enter package id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(PkgOrder.getStringFromJSON(Utilities.sendQuery("SELECT * from PkgOrders WHERE Package_Id="+userInput)));
                else
                    System.out.println(">>>>Invalid Input");
            }
            //Search by service package id
            else if (userInput.equals("4")) {
                System.out.println("---->Enter customer id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(PkgOrder.getStringFromJSON(Utilities.sendQuery("SELECT * from PkgOrders WHERE Customer_Id="+userInput)));
                else
                    System.out.println(">>>>Invalid Input");
            }
            else
                System.out.println(">>>>Invalid Input");
        }
        else
            System.out.println(">>>>Invalid input");
    }
}
