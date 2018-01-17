/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class CustomerUI {

    /**
     * @param args the command line arguments
     */
    private static String user = "";

    public void goToCustomerUI() {
        Scanner sc = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "hr", "hr");
            Statement stmt = conn.createStatement();
            String id, pwd, check;
            ResultSet rs;
            while (user.equals("")) {
                System.out.println("Enter 1 to sign in or 2 to create an account");
                int op = sc.nextInt();
                switch (op) {
                    case 1:
                        System.out.println("Please enter your username followed by your password");
                        id = sc.next();
                        pwd = sc.next();
                        // make sure the credentials match an existing account
                        check = "SELECT count(*) AS total from CustomerAccounts where Username = '" + id + "'";
                        rs = stmt.executeQuery(check);
                        rs.next();
                        if (rs.getInt("total") == 0) {
                            rs.close();
                            System.out.println("The given username does not exist");
                            break;
                        }
                        check = "SELECT * from CustomerAccounts where Username = '" + id + "'";
                        rs = stmt.executeQuery(check);
                        rs.next();
                        if (!rs.getString(10).equals(pwd)) {
                            rs.close();
                            System.out.println("Invalid password");
                            break;
                        }
                        rs.close();
                        user = id;
                        break;
                    case 2:
                        System.out.println("Please enter a username and password for your new account");
                        id = sc.next();
                        pwd = sc.next();
                        // make sure username isn't already taken
                        check = "SELECT count(*) AS total from CustomerAccounts where Username = '" + id + "'";
                        rs = stmt.executeQuery(check);
                        rs.next();
                        if (rs.getInt("total") > 0) {
                            rs.close();
                            System.out.println("An account with this username already exists");
                            break;
                        }
                        System.out.println("Please enter your first name");
                        String firstName = sc.next();
                        System.out.println("Please enter your last name");
                        String lastName = sc.next();
                        sc.nextLine();
                        System.out.println("Please enter your street address");
                        String street = sc.nextLine();
                        System.out.println("Please enter your city");
                        String city = sc.next();
                        System.out.println("Please enter your state");
                        String state = sc.next();
                        System.out.println("Please enter your area code");
                        int areaCode = sc.nextInt();
                        System.out.println("Please enter your phone number");
                        String phoneNumber = sc.next();
                        rs = stmt.executeQuery("SELECT max(Customer_id) AS key FROM CustomerAccounts");
                        rs.next();
                        int key = rs.getInt("key") + 1;
                        rs.close();
                        String longstmt = "INSERT into CustomerAccounts "
                                + "values (" + key + ", '" + firstName + "', '" + lastName + "', '" + street + "', '" + city + "', '" + state + "', " + areaCode + ", '" + phoneNumber + "', '" + id + "', '" + pwd + "', 0)";
                        stmt.executeQuery(longstmt);
                        break;
                }
            }
            /*System.out.println("\nUser is done");
            if (user.equals("")) {
                System.out.println("Nobody ");
            } else {
                System.out.println(user);
            }
            System.out.println("is logged in");*/
            System.out.println("Enter 1 to go to menu, 2 to go to orders, or 3 to go to payment methods");
            int op = sc.nextInt();
            rs = stmt.executeQuery("SELECT Customer_id AS key FROM CustomerAccounts WHERE ");
            rs.next();
            int key = rs.getInt("key");
            rs.close();
            // Please make sure this links to your ui if you created any of the below
            switch (op) {
                case 1:
                    CustomerMenuUI cmui = new CustomerMenuUI();
                    cmui.goToMenuUI(key);
                case 2:
                    PackageOrderUI oui = new PackageOrderUI();
                    oui.mainViewofPackageOrder(key);
                case 3:
                    CreditCardUI cui = new CreditCardUI();
                    cui.CustomerCreditView(key);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    // returns whoever is currently logged in
    // or the empty string if no one is logged in
    public String getLoggedInUser() {
        return user;
    }
}
