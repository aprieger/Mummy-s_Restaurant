import java.util.Scanner;

public class AdminPkgsAndAreasUI {
    public static void goToAdminPackagesUI () {
        Scanner read = new Scanner(System.in);
        while (true) {
            System.out.println("-->Enter 0 to view/change packages\n"
                    + "-->Enter 1 to view/change package orders\n"
                    + "-->Enter 2 to view/change service areas");
            String userInput="";
            userInput = read.next().trim();
            if (userInput.matches("[-+]?\\d*\\.?\\d+")) {
                if (userInput.equals("0")) {
                    AdminPackageUI toAdminPackageUI = new AdminPackageUI();
                    toAdminPackageUI.goToAdminPackageUI();
                }
                else if (userInput.equals("1")) {
                    AdminPkgOrderUI toAdminPkgOrderUI = new AdminPkgOrderUI();
                    toAdminPkgOrderUI.goToAdminPkgOrderUI();
                }
                else if (userInput.equals("2")) {
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
