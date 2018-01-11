package consolapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CreditCards {
    
    public void viewWholeTable(){
        try{
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM CREDIT_CARDS");
            while (rs.next())
            {
                System.out.println(
                        rs.getString("CREDIT_ID") +
                        rs.getString("CUSTOMER_ID") +
                        rs.getString("CARD_NUMBER") +
                        rs.getString("BRAND") +
                        rs.getString("SECURITY_NUMBER") +
                        rs.getString("EXPERATION_DATE") +
                        rs.getString("NAME_ON_CARD") +
                        rs.getString("STREET") +
                        rs.getString("CITY") +
                        rs.getString("AREA_CODE"));  
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        } 
    }
    
    public void addCard(int CUSTOMER_ID){
        Scanner scanner = new Scanner(System.in);
        int CREDIT_ID = getNextPrimaryKey()+1;
        String NAME_ON_CARD = getUserInputString("Enter the name on the card");
        int CARD_NUMBER = getUserInputInt("Enter you credit card number");
        String BRAND = getUserInputString("Enter your card type: Visa, Mastercard, Amex...");
        int SECURITY_NUMBER = getUserInputInt("Enter your security code");
        String EXPERATION_DATE = getUserInputString("Enter the experation date in the format DD/MMM/YYYY");
        String STREET = getUserInputString("Enter your street address");
        String CITY = getUserInputString("Enter your city name");
        int AREA_CODE = getUserInputInt("Enter your area code");

        try{
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("INSERT INTO CREDIT_CARDS VALUES (" 
                                            +CREDIT_ID + ","
                                            + CUSTOMER_ID + ","
                                            + CARD_NUMBER + ",'"
                                            + BRAND + "',"
                                            + SECURITY_NUMBER + ",'"
                                            + EXPERATION_DATE + "','"
                                            + NAME_ON_CARD + "','"
                                            + STREET + "','"
                                            + CITY + "',"
                                            + AREA_CODE + ")");
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        } 
    }
    
    public String getUserInputString(String msg)
    {
        String results;
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println(msg);
            try {
                results = scanner.nextLine();
                return results;
            }
            catch(Exception e){getUserInputString(msg);}
        }
        
    }
    public int getUserInputInt(String msg)
    {
        int results;
        
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println(msg);
            try {
                results = scanner.nextInt();
                return results;
            }
            catch(Exception e){ getUserInputInt(msg);}
        }
        
    }
    public int getNextPrimaryKey(){
        int max = 0;
        try{
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT MAX(CREDIT_ID) FROM CREDIT_CARDS");
            while (rs.next())
            {
                max =  Integer.parseInt(rs.getString("MAX(CREDIT_ID)"));
            }
            con.close();
            return max;
        }
        catch(Exception e){
            System.out.println(e);
            return max;
        } 
    }
    
    public ArrayList getTableAsArrayList(){
        ArrayList listOfCards = new ArrayList();
        
        try{
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM CREDIT_CARDS");
            while (rs.next())
            {
                CreditCard card = new CreditCard();
                card.setCREDIT_ID(rs.getInt("CREDIT_ID"));
                card.setCUSTOMER_ID(rs.getInt("CUSTOMER_ID")); 
                card.setCARD_NUMBER(rs.getInt("CARD_NUMBER"));
                card.setBRAND(rs.getString("BRAND")); 
                card.setSECURITY_NUMBER(rs.getInt("SECURITY_NUMBER"));
                card.setEXPERATION_DATE(rs.getString("EXPERATION_DATE"));
                card.setNAME_ON_CARD(rs.getString("NAME_ON_CARD")); 
                card.setSTREET(rs.getString("STREET")); 
                card.setCITY(rs.getString("CITY")); 
                card.setAREA_CODE(rs.getInt("AREA_CODE")); 
                listOfCards.add(card);
            }
            con.close();
            return listOfCards;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        } 
    }
    
    public void deleteEntryByCredit_ID(int CREDIT_ID){
        try{
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("DELETE FROM CREDIT_CARDS WHERE CREDIT_ID =" + CREDIT_ID);
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        } 
    }
    
    public void deleteEntryByCustomer_ID(int CUSTOMER_ID){
        try{
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("DELETE FROM CREDIT_CARDS WHERE CUSTOMER_ID =" + CUSTOMER_ID);
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        } 
    }
}


