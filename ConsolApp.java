package app;
import app.UI.LogInUI;
import app.UI.CustomerMenuUI;
import app.UI.CustomerUI;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsolApp {

    public static void main(String[] args) 
    {
        while (true)
        {   
            int caseInput = 0;
            System.out.println("Welcome to mummys restaurant");
            //System.out.println("Enter 1 to view the menu");
            System.out.println("Enter 1 for employee login");
            System.out.println("Enter 2 for customer login");
            System.out.println("Enter 3 to leave the website");
            caseInput = getCaseInput();

            switch (caseInput)
            {
                case 1:
                    LogInUI.employeeLoginUI();
                    continue;
                case 2:
                    CustomerUI customerUI = new CustomerUI();
                    customerUI.goToCustomerUI();
                    continue;
                case 3:
                    System.out.println("Good bye");
                    return;
                default:
            }
        }    
    } 
    
    public static int getCaseInput()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
        
    }
}
