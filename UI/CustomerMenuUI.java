package app.UI;
import app.*;
import app.Package;
import java.util.Scanner;

public class CustomerMenuUI {
    public static void goToMenuUI (int customerId) {
        try (Scanner read = new Scanner(System.in)) {
        while (true) {
            System.out.println(Package.getMenuString()
                    + "-->Enter the package number to view\n"
                    + "-->Enter \"checkout\" to go to Checkout");
            String userInput;
            userInput = read.nextLine().trim();
            if (userInput.toLowerCase().equals("checkout")) {
                System.out.println(">>>>To checkout");
                PackageOrderUI toPackageOrder= new PackageOrderUI();
                toPackageOrder.mainViewOfPackageOrder(customerId);
            }
            else if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                //Goes to Package Information UI
                CustomerPackageUI toPackage = new CustomerPackageUI();
                toPackage.goToPackageUI(Integer.parseInt(userInput), customerId);
            }
            else
                System.out.println(">>>>Invalid input");
        }
    }
    }
}
