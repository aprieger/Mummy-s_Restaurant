import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;

public class AdminServiceAreaUI {
    public void goToAdminServiceAreaUI() {
        Scanner read = new Scanner(System.in);
        boolean done=false;
        while (done==false) {
            System.out.println("-->Enter 'add' to add a new service area\n"
                    + "-->Enter 'edit' to edit a service area\n"
                    + "-->Enter 'delete' to delete a service area\n"
                    + "-->Enter 'search' to search for service area\n"
                    + "-->Enter 'done' to finish editing service area");

            String userInput="";
            userInput = read.nextLine();

            if (userInput.toLowerCase().trim().equals("add")) {
                AdminServiceAreaUI.adminAddServiceAreaUI();
            }
            else if (userInput.toLowerCase().trim().equals("edit")) {
                AdminServiceAreaUI.adminEditServiceAreaUI();
            }
            else if (userInput.toLowerCase().trim().equals("delete")) {
                AdminServiceAreaUI.adminDeleteServiceAreaUI();
            }
            else if (userInput.toLowerCase().trim().equals("search")) {
                AdminServiceAreaUI.adminSearchServiceAreaUI();
            }
            else if (userInput.toLowerCase().trim().equals("done")) {
                System.out.println(">>>>Done Editing");
                done=true;
            }
        }
    }
    
    public static void adminAddServiceAreaUI(){
        Scanner read = new Scanner(System.in);
        String addName="", addAreaCode="", addPackageId="", addTaxRate="";
        System.out.println(">>>>Add Service Area");
        boolean done=false;
        while (done==false) {
            //Get Name
            System.out.println("-->Enter the name of the service area: ");
            addName = read.nextLine();
            //Get Area Code
            System.out.println("-->Enter the area code of the service area: ");
            addName = read.nextLine();
            //Get Package ID
            boolean ok=false;
            while (ok==false){
                System.out.println("-->Enter the package id: ");
                addPackageId = read.nextLine();
                //Check if input is a number
                if (addPackageId.matches("[-+]?\\d*\\.?\\d+")) {
                    ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM Packages WHERE Package_Id="+addPackageId);
                    //Check if Package Id is in the Packages table
                    if (!resultsAL.isEmpty())
                       ok=true; 
                    else
                        System.out.println(">>>>Package Id not in the Packages table");
                }
                else
                    System.out.println(">>>>Invalid Input: you must enter a number");
            }
            //Get Tax Rate
            ok=false;
            while (ok==false){
                System.out.println("-->Enter the Tax Rate: ");
                addTaxRate = read.nextLine();
                //Check if input is a number
                if (addTaxRate.matches("[-+]?\\d*\\.?\\d+"))
                    ok=true;
                else
                    System.out.println(">>>>Invalid Input: you must enter a number");
            }
            //Send update SQL transaction to add the data to the ServiceAreas Table
            ServiceArea.addServiceArea(addName, Integer.parseInt(addAreaCode), Integer.parseInt(addPackageId), Double.parseDouble(addTaxRate));
        }
    }
    
    public static void adminEditServiceAreaUI () {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int editServiceAreaId=0;
        while (done==false) {
            System.out.println("---->Enter the service area id to edit");
            String userInput = read.nextLine();
            //Check if input is a number
            if (userInput.trim().matches("[-+]?\\d*\\.?\\d+")) {
                //Send the query and get the results of the count of service areas with the specified service area id
                ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM ServiceAreas WHERE Area_Id="+userInput.trim());
                if (!resultsAL.isEmpty()) {
                    editServiceAreaId=Integer.parseInt(userInput);
                    System.out.println("Service Area Info:\n"+ServiceArea.getStringFromJSON(ServiceArea.getSingleServiceAreaData(editServiceAreaId)));
                    System.out.println("-->Enter 1 to edit service area name\n"
                            + "-->Enter 2 to edit service area code\n"
                            + "-->Enter 3 to edit service area package id\n"
                            + "-->Enter 4 to edit service area tax rate");
                    userInput = read.nextLine();
                    //Check if input is a number
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        //Edit name
                        if (userInput.trim().equals("1")) {
                            System.out.println("-->Enter the new name: ");
                            userInput = read.nextLine();
                            ServiceArea.editName(editServiceAreaId, userInput);
                        }
                        //Edit Area Code
                        else if (userInput.trim().equals("2")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new area code: ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ServiceArea.editAreaCode(editServiceAreaId, Integer.parseInt(userInput));
                                    ok=true;
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        //Edit Package Id
                        else if (userInput.trim().equals("3")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new package id: ");
                                userInput = read.nextLine();
                                //Check if input is a number
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ArrayList<JSONObject> packageIdResultsAL = Utilities.sendQuery("SELECT * FROM Packages WHERE Package_Id="+userInput);
                                    //Check if Package Id is in the Packages table
                                    if (!packageIdResultsAL.isEmpty()) {
                                        ServiceArea.editPackageId(editServiceAreaId, Integer.parseInt(userInput));
                                        ok=true;
                                    }
                                    else
                                        System.out.println(">>>>Package Id not in the Packages table");
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        //Edit Area Code
                        else if (userInput.trim().equals("4")) {
                            boolean ok=false;
                            while (ok==false){
                                System.out.println("-->Enter the new tax rate: ");
                                userInput = read.nextLine();
                                if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                                    ServiceArea.editTaxRate(editServiceAreaId, Double.parseDouble(userInput));
                                    ok=true;
                                }
                                else
                                    System.out.println(">>>>Invalid Input: you must enter a number");
                            }
                        }
                        System.out.println("Updated Service Area: "+ServiceArea.getStringFromJSON(ServiceArea.getSingleServiceAreaData(editServiceAreaId)));
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

    public static void adminDeleteServiceAreaUI() {
        boolean done=false;
        Scanner read = new Scanner(System.in);
        int deleteServiceAreaId=0;
        while (done==false) {
            System.out.println("---->Enter the service area id to delete");
            String userInput = read.nextLine();
            if (userInput.trim().matches("[-+]?\\d*\\.?\\d+")) {
                ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT * FROM ServiceAreas WHERE Area_Id="+userInput.trim());
                if (!resultsAL.isEmpty()) {
                    deleteServiceAreaId=Integer.parseInt(userInput);
                    System.out.println("Service Area Info:\n"+ServiceArea.getStringFromJSON(ServiceArea.getSingleServiceAreaData(deleteServiceAreaId))+"\nConfirm Delete: 1 for yes, 0 for no");
                    userInput = read.nextLine();
                    if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                        if (userInput.trim().equals("1")) {
                            ServiceArea.deleteServiceArea(deleteServiceAreaId);
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
                    System.out.println("Error: Service Area Id not found");
            }
            else 
                System.out.println("Error: Invalid Input");
        }
    }
    
    public static void adminSearchServiceAreaUI(){
        Scanner read = new Scanner(System.in);
        System.out.println(">>>>Search Service Areas");
        System.out.println("-->Enter 1 to search by service area id\n"
                + "-->Enter 2 to search by service area name\n"
                + "-->Enter 3 to search by service area code\n"
                + "-->Enter 4 to search by service area package id\n"
                + "-->Enter 5 to search for service area tax rate\n");
        String userInput = read.nextLine();
        if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
            //Search by service area id
            if (userInput.trim().equals("1")) {
                System.out.println("---->Enter the service area id to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(ServiceArea.getStringFromJSON(Utilities.sendQuery("SELECT * from ServiceAreas WHERE Area_Id="+userInput.trim())));
                else
                    System.out.println(">>>>Invalid Input");
            }
            //Search by service area name
            else if (userInput.trim().equals("2")) {
                System.out.println("---->Enter the service area name to search by");
                userInput = read.nextLine();
                System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from ServiceAreas WHERE UPPER(Name)='"+userInput.toUpperCase()+"'")));
            }
            //Search by service area code
            else if (userInput.trim().equals("3")) {
                System.out.println("---->Enter service area code to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from ServiceAreas WHERE Area_Code="+userInput)));
                else
                    System.out.println(">>>>Invalid Input");
            }
            //Search by service package id
            else if (userInput.trim().equals("4")) {
                System.out.println("---->Enter service area package id to search by");
                userInput = read.nextLine();
                String userInput2 = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from ServiceAreas WHERE Package_Id="+userInput2)));
                else
                    System.out.println(">>>>Invalid Input");
            }
            //Search by service tax rate
            else if (userInput.trim().equals("5")) {
                System.out.println("---->Enter service area tax rate to search by");
                userInput = read.nextLine();
                if (userInput.matches("[-+]?\\d*\\.?\\d+"))
                    System.out.println(Package.getStringFromJSON(Utilities.sendQuery("SELECT * from ServiceAreas WHERE Tax_Rate="+userInput)));
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
