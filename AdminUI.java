import java.sql.*;
import java.util.*;


public class AdminUI {
    
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        while (true) {
            System.out.println("-->Enter 0 to view/change packages\n"
                    + "-->Enter 1 to view/change package orders\n"
                    + "-->Enter 2 to view/change service areas");
            String userInput="";
            userInput = read.next();
            if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                if (userInput.trim().equals("0")) {
                    AdminPackageUI toAdminPackageUI = new AdminPackageUI();
                    toAdminPackageUI.goToAdminPackageUI();
                }
                else if (userInput.trim().equals("1")) {
                    AdminPkgOrderUI toAdminPkgOrderUI = new AdminPkgOrderUI();
                    toAdminPkgOrderUI.goToAdminPkgOrderUI();
                }
                else if (userInput.trim().equals("2")) {
                    AdminServiceAreaUI toAdminServiceAreaUI = new AdminServiceAreaUI();
                    toAdminServiceAreaUI.goToAdminServiceAreaUI();
                }
            }
            else {
                System.out.println(">>>>Invalid input");
            }
        }
    }
}
