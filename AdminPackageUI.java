import java.sql.*;
import java.util.*;


public class AdminPackageUI {
    Scanner read = new Scanner(System.in);
    public void goToAdminPackageUI() {
        boolean done=false;
        while (done==false) {
            System.out.println("-->Enter the package id to edit/delete packages\n"
                    + "-->Enter 'search' to search for package to edit/delete\n"
                    + "-->Enter 'add' to add a new package\n"
                    + "-->Enter 'done' to finish editing packages");

            String userInput="";
            userInput = read.next();

            if (userInput.toLowerCase().trim().equals("search")) {
                Package.searchPackageUI();
            }
            else if (userInput.toLowerCase().trim().equals("add")) {
                Package.addPackageUI();
            }
            else if (userInput.toLowerCase().trim().equals("done")) {
                System.out.println(">>>>Done Editing");
                done=true;
                //PkgOrder.addOpenPkgOrder();
                //TODO: Create a PkgOrder and back to menu
            }
            else if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                System.out.println(">>>>View Package");
                //TODO: Just go back to the menu
            }
        }
    }
}
