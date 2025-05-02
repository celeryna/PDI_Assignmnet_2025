/************************************
 * Author: Celerina Reyes           *
 * Created: 07/10/2024              *
 * Purpose: Contains Main Menu      *
 ************************************/

import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        MissionController controller = new MissionController();
        controller.readFile("StartingDataFile_1.csv");
        controller.createMissionDatabase();

        int choice = 0;
      
        // A loop for the main library menu. It will keep running until the user chooses to exit :)
        do
        {
            System.out.println("\n=============================================");  
            System.out.println(" Welcome to Mission Command and Control ");
            System.out.println("=============================================");
            System.out.println("Your options for this system are listed below: ");
            System.out.println(" 1 > View all Missions");
            System.out.println(" 2 > View all manned missions");
            System.out.println(" 3 > View all unmanned missions");
            System.out.println(" 4 > View a mission's astronauts");
            System.out.println(" 5 > Add a new mission");
            System.out.println(" 6 > Edit an existing mission");
            System.out.println(" 7 > Summary of missions' success rates (average, highest, lowest)");
            System.out.println(" 8 > List astronauts for a given nationality");
            System.out.println(" 9 > Exit Mission Command and Control");
            System.out.println("************************************");
            System.out.print("Your choice: ");

        
            // Try-catch block to handle invalid inputs for the menu choices :)
            try 
            {
                choice = sc.nextInt();
                sc.nextLine();
                
                // Switch-case for the different user choices :o
                switch(choice)
                {
                    case 1:
                        // View ALL missions from the library :D
                        System.out.println("\n=========================================");
                        System.out.println("           All Missions");
                        System.out.println("=========================================");
                        manager.viewAllBooks();
                        break;

                    default:
                        // To handle invalid menu choices (oopsies)
                        System.out.println("Invalid choice :( Try again.\n");
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid choice :( Please enter a number.");
                sc.next();  // Clear the invalid input :(
            }
        }
        while (choice != 7); // Loop until the user chooses to leave (until 7 is input) :)

        sc.close(); // Bye-bye Scanner
    }
    }
}