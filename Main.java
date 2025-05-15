/************************************
 * Author: Celerina Reyes           *
 * Created: 16/04/2025              *
 * Purpose: Contains Main Menu      *
 ************************************/

import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        MissionController controller = new MissionController();
        controller.readFile("data.csv");
        controller.createMissionDatabase();
        controller.viewAllMissions();

        int choice = 0;
      
        // A loop for the main library menu. It will keep running until the user chooses to exit 
        do
        {
            System.out.println("\n=============================================");  
            System.out.println("     Welcome to Mission Command and Control    ");
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
            System.out.println(" 9 > Save changes and exit Mission Command and Control");
            System.out.println("==============================================");
            System.out.print("Your choice: ");

        
            // Try-catch block to handle invalid inputs for the menu choices
            try 
            {
                choice = sc.nextInt();
                sc.nextLine();
                
                // Switch-case for the different user choices 
                switch(choice)
                {
                    case 1:
                        // View ALL missions from the library 
                        System.out.println("\n========================================");
                        System.out.println("             All Missions");
                        System.out.println("========================================");
                        controller.viewAllMissions();
                        break;

                    case 2:
                        // View Manned Missions
                        System.out.println("\n========================================");
                        System.out.println("           Manned Missions");
                        System.out.println("========================================");
                        controller.filterMissions(true);
                        break;

                    case 3: 
                        // View Unmanned Missions
                        System.out.println("\n========================================");
                        System.out.println("         Unmanned Missions");
                        System.out.println("========================================");
                        controller.filterMissions(false);
                        break;

                    case 4:
                        // View a mission's astronauts
                        System.out.println("\n========================================");
                        System.out.println("          View Astronauts");
                        System.out.println("========================================");
                        System.out.print("Enter mission name or code: ");
                        String input = sc.nextLine();
                        controller.viewAstronautsByMission(input);
                        break;

                    case 5:
                        // Add a mission
                        System.out.println("\n========================================");
                        System.out.println("            Add a Mission");
                        System.out.println("========================================");
                        controller.addMission();
                        break;

                    case 6:
                        // EDIT an existing mission
                        System.out.println("\n========================================");  
                        System.out.println("            Edit Missions");
                        System.out.println("========================================");
                    
                        // List ALL missions with a number for selection
                        for (int i = 0; i < controller.missions.length; i++)
                        {
                            if (controller.missions[i] != null)
                            {
                                int missionNum = i + 1;
                                System.out.println("[" + missionNum + "] " + controller.missions[i].getMissionName());
                            }
                        }
                    
                        int missionChoice = -1;
                    
                        // Loop until valid mission number is selected
                        while (missionChoice < 0 || missionChoice >= controller.missions.length)
                        {
                            System.out.print("\nSelect a mission to edit by the number\nChoice: ");
                            try
                            {
                                missionChoice = sc.nextInt();
                                sc.nextLine();
                                missionChoice -= 1; // adjust for 0-based indexing
                    
                                if (missionChoice < 0 || missionChoice >= controller.missions.length)
                                {
                                    System.out.println("Invalid mission number. Please try again.");
                                }
                            }
                            catch (InputMismatchException e)
                            {
                                System.out.println("Invalid input. Please enter a number.");
                                sc.nextLine(); // clear bad input
                            }
                        }
                    
                        // Now ask which field to edit
                        System.out.println("\nWhat value would you like to change from that mission?\n");
                        System.out.println("[1] Mission Name: " + controller.missions[missionChoice].getMissionName());
                        System.out.println("[2] Mission Code: " + controller.missions[missionChoice].getMissionCode());
                        System.out.println("[3] Destination: " + controller.missions[missionChoice].getDestPlanet());
                        System.out.println("[4] Launch Year: " + controller.missions[missionChoice].getLaunchYear());
                        System.out.println("[5] Success Rate: " + controller.missions[missionChoice].getSuccessRate());
                        System.out.println("[6] Manned: " + controller.missions[missionChoice].isManned());
                    
                        int detailChoice = -1;
                    
                        // Loop until valid field number is chosen
                        while (detailChoice < 1 || detailChoice > 6)
                        {
                            System.out.print("\nSelect a value to edit by the number\nChoice: ");
                            try
                            {
                                detailChoice = sc.nextInt();
                                sc.nextLine();
                                if (detailChoice < 1 || detailChoice > 6)
                                {
                                    System.out.println("Invalid field number. Please try again.");
                                }
                            }
                            catch (InputMismatchException e)
                            {
                                System.out.println("Invalid input. Please enter a number.");
                                sc.nextLine();
                            }
                        }
                    
                        // Ask for new value
                        System.out.print("New value: ");
                        String detailValue = sc.nextLine();
                    
                        // Call the edit method
                        controller.editMission(missionChoice, detailChoice, detailValue);
                        System.out.println("\nMission edited successfully!");
                        break;       
                        
                    case 7:
                        System.out.println("\n========================================");
                        System.out.println("          Mission Success Rates       ");
                        System.out.println("========================================");
                        controller.sumMissionSuccessRate();
                        System.out.println("========================================\n");
                        break;
                    
                    case 8:
                        System.out.println("\n========================================");
                        System.out.println("        Astronauts by Nationality       ");
                        System.out.println("========================================");
                        System.out.print("Enter nationality to search: ");
                        String nationality = sc.nextLine();
                        controller.viewAstronautsByNationality(nationality);
                        break;

                    case 9:
                        controller.writeFile("data.csv");
                        System.out.println("Exiting Mission Command and Control...");
                        break;
                    

                    default:
                        // To handle invalid menu choices
                        System.out.println("Invalid choice :( Try again.\n");
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid choice :( Please enter a number.");
                sc.next();  // Clear the invalid input 
            }
        }
        while (choice != 9); // Loop until the user chooses to leave (until 9 is input) 

        sc.close(); 
    }
}
