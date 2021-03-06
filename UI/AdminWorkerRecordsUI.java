

package app.UI;

import java.sql.SQLException;
import java.util.Scanner;
import app.*;


public class AdminWorkerRecordsUI {

    public static void adminUI() {
        int userInput;
        boolean loopLogin = true;
        
        do { 
            System.out.println("\nPlease choose and enter an option:\n"
                + "[1]: Enter a new worker.\n"
                + "[2]: Delete a worker.\n"
                + "[3]: Change a worker status to active or inactive.\n"    
                + "[4]: Search for a worker.\n"
                + "[5]: Back to main menu.\n"
                + "[6]: Exit program");
        
            Scanner scanner = new Scanner(System.in);
        
            userInput = scanner.nextInt();
           
            try {
                switch(userInput){
                    case 1:
                        Admin.enterNewWorker();
                        break;       
                    case 2: 
                        Admin.deleteWorker();
                        break;
                    case 3:
                        Admin.setWorkerStatus();
                        break;
                    case 4:
                        Admin.searchForWorker();
                        break;
                    case 5:
                        loopLogin = false;
                        break;
                    case 6:
                        System.exit(0);
                    default:
                        System.out.println("Not a vaild choice!\n");
                }
            }
            catch (SQLException e) {
                e.getMessage();
            }
        } while (loopLogin);
    }
}
