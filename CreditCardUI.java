package consolapp;

import java.util.ArrayList;
import java.util.Scanner;

public class CreditCardUI {
    
    public static ArrayList<CreditCard> CustomerCreditView(int CUSTOMER_ID)
    {
        while(true)
        {
            System.out.println("Enter 1 to add a new credit card");
            System.out.println("Enter 2 to choose an exisiting cards");
            CreditCards cc = new CreditCards();
            ArrayList<CreditCard> card = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            int userInput = scanner.nextInt();


            switch (userInput)
            {
                case 1:
                    int CREDIT_ID = cc.getNextPrimaryKey()+1;
                    String NAME_ON_CARD = cc.getUserInputString("Enter the name on the card");
                    int CARD_NUMBER = cc.getUserInputInt("Enter you credit card number");
                    String BRAND = cc.getUserInputString("Enter your card type: Visa, Mastercard, Amex...");
                    int SECURITY_NUMBER = cc.getUserInputInt("Enter your security code");
                    String EXPERATION_DATE = cc.getUserInputString("Enter the experation date in the format DD/MMM/YYYY");
                    String STREET = cc.getUserInputString("Enter your street address");
                    String CITY = cc.getUserInputString("Enter your city name");
                    int AREA_CODE = cc.getUserInputInt("Enter your area code");

                    cc.addCard(CREDIT_ID,CUSTOMER_ID, CARD_NUMBER,BRAND,SECURITY_NUMBER,EXPERATION_DATE,NAME_ON_CARD,STREET,CITY,AREA_CODE);
                    card = cc.getCreditCardsByCredit_idAsArrayList(CREDIT_ID);
                    return card;
                case 2:
                    card = cc.getCreditCardsByCustomer_idAsArrayList(CUSTOMER_ID);
                    for (CreditCard cards : card )
                    {
                        System.out.println(cards.toString());
                    }
                    System.out.println("Please enter the Credit_ID of the card you would like to use");
                    userInput = scanner.nextInt();
                    card = cc.getCreditCardsByCredit_idAsArrayList(userInput);
                    return card;

            }
            
        }
    }
        
    
    public int AdminCreditView()
    {
        CreditCards cc = new CreditCards();
        ArrayList<CreditCard> card = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        while(true)
        {
            System.out.println("Please Enter 1 of the following options");
            System.out.println("1. View all Credit Cards");
            System.out.println("2. Get all Credit Cards by Customer ID");
            System.out.println("3. Get Credit card info by Credit_ID");
            System.out.println("4. Delete Credit Card by Credit_id");
            System.out.println("5. Delete all cards by User_ID");
            System.out.println("6. Exit");
            int userInput = scanner.nextInt();

            switch (userInput)
            {
                case 1:
                    card = cc.getTableAsArrayList();
                    display(card);
                    break;
                case 2:
                    System.out.println("Enter the User_ID");
                    userInput = scanner.nextInt();
                    card = cc.getCreditCardsByCustomer_idAsArrayList(userInput);
                    display(card);
                    break;
                case 3:
                    System.out.println("Enter the Credit_ID");
                    userInput = scanner.nextInt();
                    card = cc.getCreditCardsByCredit_idAsArrayList(userInput);
                    display(card);
                    break;
                case 4:
                    System.out.println("Enter the Credit_ID");
                    userInput = scanner.nextInt();
                    cc.deleteEntryByCredit_ID(userInput);
                    break;
                case 5:
                    System.out.println("Enter the Customer_ID_ID");
                    userInput = scanner.nextInt();
                    cc.deleteEntryByCustomer_ID(userInput);
                    break;
                case 6:
                    return 0;
            }       
        }
    }
    
    public void display(ArrayList cards)
    {
        for (int x = 0; x < cards.size(); x++ )
        {
            System.out.println(cards.get(x).toString());
        }
    }
}
