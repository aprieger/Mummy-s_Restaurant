import java.sql.*;
import java.util.*;


public class PackageUI {
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
}
