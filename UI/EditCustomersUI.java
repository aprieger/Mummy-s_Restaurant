/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MummysRestaurant.UI;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class EditCustomersUI {

    public void goToEditCustomersUI() {
        Scanner sc = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "hr", "hr");
            Statement stmt = conn.createStatement();
            String statement;
            ResultSet rs;
            while (true) {
                System.out.println("Enter the username of a customer to edit their profile or enter 'quit' to quit");
                String user = sc.next();
                if (user.equals("quit")) {
                    break;
                }
                statement = "SELECT count(*) AS total from CustomerAccounts where Username = '" + user + "'";
                rs = stmt.executeQuery(statement);
                rs.next();
                if (rs.getInt("total") == 0) {
                    rs.close();
                    System.out.println("The given username does not exist");
                    continue;
                }
                int op = 1;
                while (op == 1) {
                    System.out.println("Enter 1 to ban/enable the user or 2 to delete them or 3 to pick a different user");
                    op = sc.nextInt();
                    switch (op) {
                        case 1:
                            statement = "SELECT enabled AS flag FROM CustomerAccounts WHERE Username = '" + user + "'";
                            rs = stmt.executeQuery(statement);
                            rs.next();
                            int flag = rs.getInt("flag");
                            if (flag == 0) {
                                flag = 1;
                            } else {
                                flag = 0;
                            }
                            statement = "UPDATE CustomerAccounts SET enabled = " + flag + " WHERE Username = '" + user + "'";
                            stmt.executeQuery(statement);
                            break;
                        case 2:
                            stmt.executeQuery("DELETE FROM CustomerAccounts WHERE Username = '" + user + "'");
                            break;
                        case 3:
                            break;
                    }
                }
            }
            System.out.println("Enter 1 for packages, 2 for orders, 3 for packages and areas, or 4 for service areas");
            int op = sc.nextInt();
            // Please make sure this links to your ui if you created any of the below
            switch (op) {
                case 1:
                    AdminPackageUI apui = new AdminPackageUI();
                    apui.goToAdminPackageUI();
                case 2:
                    AdminPkgOrderUI apoui = new AdminPkgOrderUI();
                    apoui.goToAdminPkgOrderUI();
                case 3:
                    AdminPkgsAndAreasUI apaui = new AdminPkgsAndAreasUI();
                    apaui.goToAdminPackagesUI();
                case 4:
                    AdminServiceAreaUI asaui = new AdminServiceAreaUI();
                    asaui.goToAdminServiceAreaUI();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
