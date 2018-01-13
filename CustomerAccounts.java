
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class CustomerAccounts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "hr", "hr");
            Statement stmt = conn.createStatement();
            Scanner sc = new Scanner(System.in);
            String user = "";
            while (true) {
                System.out.println("\nPlease log in with your username and password or type 'create' to create an account");
                System.out.println("To sign out, please type 'out'");
                System.out.println("To quit, please type 'quit'");
                String input = sc.next();
                if (input.equals("quit")) {
                    break;
                }
                if (input.equals("out")) {
                    // make sure someone is actually signed in first
                    if (user.equals("")) {
                        System.out.println("There is nobody to sign out");
                    } else {
                        System.out.println(user + " has been signed out succesfully");
                    }
                    user = "";
                    continue;
                }
                if (input.equals("create")) { // add the account to the database
                    System.out.println("Please enter a username and password for your new account");
                    String id = sc.next();
                    String pwd = sc.next();
                    // make sure username isn't already taken
                    String check = "SELECT count(*) AS total from CustomerAccounts where Username = '" + id + "'";
                    ResultSet rs = stmt.executeQuery(check);
                    rs.next();
                    if (rs.getInt("total") > 0) {
                        rs.close();
                        System.out.println("An account with this username already exists");
                        continue;
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
                            + "values (" + key + ", '" + firstName + "', '" + lastName + "', '" + street + "', '" + city + "', '" + state + "', " + areaCode + ", '" + phoneNumber + "', '" + id + "', '" + pwd + "')";
                    stmt.executeQuery(longstmt);
                } else { // allow an existing user to login
                    // make sure no one is logged in already
                    if (!user.equals("")) {
                        System.out.println(user + " is already signed in\nPlease sign them out first");
                        continue;
                    }
                    String id = input;
                    String pwd = sc.next();
                    // make sure the credentials match an existing account
                    String check = "SELECT count(*) AS total from CustomerAccounts where Username = '" + id + "'";
                    ResultSet rs = stmt.executeQuery(check);
                    rs.next();
                    if (rs.getInt("total") == 0) {
                        rs.close();
                        System.out.println("The given username does not exist");
                        continue;
                    }
                    check = "SELECT * from CustomerAccounts where Username = '" + id + "'";
                    rs = stmt.executeQuery(check);
                    rs.next();
                    if (!rs.getString(10).equals(pwd)) {
                        rs.close();
                        System.out.println("Invalid password");
                        continue;
                    }
                    rs.close();
                    user = id;
                    System.out.println("Sign in with username " + user + " successful");
                }
            }
            sc.close();
            if (user.equals("")) {
                user = "Nobody";
            }
            System.out.println("\nUser is done\n" + user + " is logged in");
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}
