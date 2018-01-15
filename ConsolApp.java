import java.util.ArrayList;
import java.util.Scanner;

public class ConsolApp {

    public static void main(String[] args) 
    {
        while (true)
        {   
            int caseInput = 0;
            System.out.println("Welcome to mummys restaurant");
            System.out.println("Enter 1 to view the menu");
            System.out.println("Enter 2 for employee login");
            System.out.println("Enter 3 for customer login");
            System.out.println("Enter 4 to leave the website");
            caseInput = getCaseInput();

            switch (caseInput)
            {
                case 1:
                    MenuUI.MenuUI();
                    continue;
                case 2:
                    continue;
                case 3:
                    CustomerAccounts.mainUI();
                    continue;
                case 4:
                    System.out.println("Good bye");
                    break;
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