import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/******************************************
 * Author: Celerina Reyes                 *
 * Created: 17/04/2025                    *
 * Purpose: Manages the system's missions *
 *****************************************/

public class MissionController
{
    public String[][] missionsData; // A 2D array to store raw mission data from the CVS file      
    public Mission[] missions; // An array to store mission obects after processing the data from the CVS file

    // NAME: readFile
    // IMPORT: filename (String)
    // EXPORT: none
    // PURPOSE: Reads the contents of the CSV file into the missionsData array

    public void readFile(String filename)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        String line;

        try
        {
            fileStream = new FileInputStream(filename);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);

            // Count the number of lines in the file to determine number of rows
            int rows = 0;
            while (bufRdr.readLine() != null)
            {
                rows++;
            }
            fileStream.close();

            // Reopen the file to actually read the data
            fileStream = new FileInputStream(filename);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);

            line = bufRdr.readLine(); // Skip header row
            String[] headers = line.split(",");
            int columns = headers.length;

            // Create a 2D array for storing mission data (excluding header)
            missionsData = new String[rows - 1][columns];

            int i = 0;
            while ((line = bufRdr.readLine()) != null)
            {
                String[] values = line.split(",", -1); // Include empty values
                for (int j = 0; j < values.length; j++)
                {
                    missionsData[i][j] = values[j];
                }
                i++;
            }

            fileStream.close();
        }
        catch (IOException e)
        {
            if (fileStream != null)
            {
                try { fileStream.close(); } catch (IOException ex2) {}
            }
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // NAME: createMissionDatabase
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: Converts the raw mission data into Mission and Astronaut objects
    public void createMissionDatabase()
    {
        missions = new Mission[missionsData.length];

        for (int i = 0; i < missionsData.length; i++)
        {
            // Extract base mission data from the 2D array
            String missionName = missionsData[i][0];
            String missionCode = missionsData[i][1];
            String destPlanet = missionsData[i][2];
            int launchYear = Integer.parseInt(missionsData[i][3]);
            double successRate = Double.parseDouble(missionsData[i][4]);
            boolean ismanned = Boolean.parseBoolean(missionsData[i][5]);

            Astronaut[] astronauts = new Astronaut[5];

            // Only parse astronaut data if the mission is manned and not empty
            if (ismanned && !missionsData[i][6].isEmpty())
            {
                String[] astronautFields = missionsData[i][6].split("\\|");
                astronauts = new Astronaut[astronautFields.length];

                for (int j = 0; j < astronautFields.length; j++)
                {
                    String[] parts = astronautFields[j].split(":");
                    String[] nameParts = parts[0].split(" ");

                    String firstName = nameParts[0];
                    String lastName;
                    // Check if the astronaut has both a first and last name
                    if (nameParts.length > 1)
                    {
                        lastName = nameParts[1];
                    }
                    else
                    {
                        lastName = "";
                    }

                    String role = parts[1];
                    int yearOfBirth = Integer.parseInt(parts[2]);
                    String nationality = parts[3];

                    astronauts[j] = new Astronaut(firstName, lastName, role, yearOfBirth, nationality);
                }
            }

            // Create a mission object and store it in an array
            missions[i] = new Mission(missionName, missionCode, destPlanet, launchYear, successRate, ismanned, astronauts);
        }
    }

    // NAME: viewAllMissions
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: prints the details of ALL the missions from the system

    public void viewAllMissions()
    {
        for (int i = 0; i < missions.length; i++)
        {
            if (missions[i] == null)
            {
                continue; // Null entries will be skipped
            }

            System.out.println("========================================");
            System.out.println("Mission Name: " + missions[i].getMissionName());
            System.out.println("Code: " + missions[i].getMissionCode());
            System.out.println("Destination: " + missions[i].getDestPlanet());
            System.out.println("Launch Year: " + missions[i].getLaunchYear());
            System.out.println("Success Rate: " + missions[i].getSuccessRate() + "%");
            System.out.println("Manned Mission: " + missions[i].isManned());

            // Display astronauts if the mission is manned
            if (missions[i].isManned() && missions[i].getAstronauts() != null)
            {
                System.out.println("Astronauts:");
                Astronaut[] crew = missions[i].getAstronauts();

                for (int j = 0; j < crew.length; j++)
                {
                    if (crew[j] != null)
                    {
                        System.out.println(" - " + crew[j].getFirstName() + " " + crew[j].getLastName()
                                        + " (" + crew[j].getRole() + ", " + crew[j].getNationality()
                                        + ", Born: " + crew[j].getYearOfBirth() + ")");
                    }
                }
            }

            System.out.println("========================================\n");
        }
    }

    // NAME: filterMissions
    // IMPORT: isManned (boolean)
    // EXPORT: none
    // PURPOSE: Displays only manned or unmanned missions based on the input
    public void filterMissions(boolean isManned)
    {
        for (int i = 0; i < missions.length; i++)
        {
            if (missions[i] == null)
            {
                continue; // Skip empty entries
            }

            // Only display missions that match the requested type
            if (missions[i].isManned() == isManned)
            {
                System.out.println("========================================");
                System.out.println("Mission Name: " + missions[i].getMissionName());
                System.out.println("Code: " + missions[i].getMissionCode());
                System.out.println("Destination: " + missions[i].getDestPlanet());
                System.out.println("Launch Year: " + missions[i].getLaunchYear());
                System.out.println("Success Rate: " + missions[i].getSuccessRate() + "%");

                if (isManned && missions[i].getAstronauts() != null)
                {
                    System.out.println("Astronauts:");
                    Astronaut[] crew = missions[i].getAstronauts();

                    for (int j = 0; j < crew.length; j++)
                    {
                        if (crew[j] != null)
                        {
                            System.out.println(" - " + crew[j].getFirstName() + " " + crew[j].getLastName()
                                            + " (" + crew[j].getRole() + ", " + crew[j].getNationality()
                                            + ", Born: " + crew[j].getYearOfBirth() + ")");
                        }
                    }
                }

                System.out.println("========================================\n");
            }
        }
    }

    // NAME: viewAstronautsByMission
    // IMPORT: missionIdentifier (String)
    // EXPORT: found (boolean)
    // PURPOSE: Displays astronauts for a specific mission, if it exists and is manned
    public boolean viewAstronautsByMission(String missionIdentifier)
    {
        boolean found = false;

        for (int i = 0; i < missions.length; i++)
        {
            if (missions[i] == null)
            {
                continue;
            }

            // Match by mission name OR mission code (case insensitive)
            if (missions[i].getMissionName().equalsIgnoreCase(missionIdentifier)
            || missions[i].getMissionCode().equalsIgnoreCase(missionIdentifier))
            {
                found = true;

                System.out.println("========================================");
                System.out.println("Mission: " + missions[i].getMissionName() + " (" + missions[i].getMissionCode() + ")");
                System.out.println("Destination: " + missions[i].getDestPlanet());
                System.out.println("Manned: " + missions[i].isManned());

                if (missions[i].isManned() && missions[i].getAstronauts() != null)
                {
                    System.out.println("Astronauts:");
                    Astronaut[] crew = missions[i].getAstronauts();

                    for (int j = 0; j < crew.length; j++)
                    {
                        if (crew[j] != null)
                        {
                            System.out.println(" - " + crew[j].getFirstName() + " " + crew[j].getLastName()
                                            + " (" + crew[j].getRole() + ", " + crew[j].getNationality()
                                            + ", Born: " + crew[j].getYearOfBirth() + ")");
                        }
                    }
                }
                else
                {
                    System.out.println("This mission is unmanned.");
                }

                System.out.println("========================================\n");
            }
        }

        if (!found)
        {
            System.out.println("No mission found with name or code: " + missionIdentifier);
        }

        return found;
    }

    // NAME: addMission
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: Adds a new mission to the missions array by collecting and validating user input
    public void addMission()
    {
        Scanner sc = new Scanner(System.in);

        String name = "", code = "", destination = "";

        // Validate non-empty mission name
        while (name.isEmpty())
        {
            System.out.print("Mission Name: ");
            name = sc.nextLine();
            if (name.isEmpty())
            {
                System.out.println("Mission name cannot be empty. Please try again.");
            }
        }

        // Validate non-empty mission code
        while (code.isEmpty())
        {
            System.out.print("Mission Code: ");
            code = sc.nextLine();
            if (code.isEmpty())
            {
                System.out.println("Mission code cannot be empty. Please try again.");
            }
        }

        // Validate non-empty destination
        while (destination.isEmpty())
        {
            System.out.print("Destination Planet: ");
            destination = sc.nextLine();
            if (destination.isEmpty())
            {
                System.out.println("Destination cannot be empty. Please try again.");
            }
        }

        // Validate launch year (1900–2100)
        int year = -1;
        while (year < 1900 || year > 2100)
        {
            System.out.print("Launch Year (1900–2100): ");
            try
            {
                year = sc.nextInt();
                sc.nextLine();
                if (year < 1900 || year > 2100)
                {
                    System.out.println("Invalid year. Please enter a number between 1900 and 2100.");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input. Please enter a valid year.");
                sc.nextLine();
            }
        }

        // Validate success rate (0–100)
        double successRate = -1;
        while (successRate < 0 || successRate > 100)
        {
            System.out.print("Success Rate (0–100): ");
            try
            {
                successRate = sc.nextDouble();
                sc.nextLine();
                if (successRate < 0 || successRate > 100)
                {
                    System.out.println("Invalid percentage. Please enter a number between 0 and 100.");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
        }

        // Ask if mission is manned (true/false)
        boolean manned = false;
        String input = "";
        while (!input.equals("true") && !input.equals("false"))
        {
            System.out.print("Is this a manned mission? (true/false): ");
            input = sc.nextLine().toLowerCase().trim();

            if (!input.equals("true") && !input.equals("false"))
            {
                System.out.println("Invalid input. Please type 'true' or 'false'.");
            }
        }
        manned = Boolean.parseBoolean(input);

        Astronaut[] astronauts = null;

        // If manned, collect astronaut details
        if (manned)
        {
            int numAstronauts = 0;
            while (numAstronauts < 1 || numAstronauts > 5)
            {
                System.out.print("How many astronauts? (1–5): ");
                try
                {
                    numAstronauts = sc.nextInt();
                    sc.nextLine();

                    if (numAstronauts < 1 || numAstronauts > 5)
                    {
                        System.out.println("Invalid number. Please enter between 1 and 5.");
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }

            astronauts = new Astronaut[numAstronauts];

            for (int i = 0; i < numAstronauts; i++)
            {
                System.out.println("\nAstronaut " + (i + 1));

                String firstName = "";
                while (firstName.isEmpty())
                {
                    System.out.print("First Name: ");
                    firstName = sc.nextLine();
                    if (firstName.isEmpty())
                    {
                        System.out.println("First name cannot be empty. Please try again.");
                    }
                }

                String lastName = "";
                while (lastName.isEmpty())
                {
                    System.out.print("Last Name: ");
                    lastName = sc.nextLine();
                    if (lastName.isEmpty())
                    {
                        System.out.println("Last name cannot be empty. Please try again.");
                    }
                }

                System.out.print("Role: ");
                String role = sc.nextLine();

                int age = 0;
                while (age <= 0)
                {
                    System.out.print("Age: ");
                    try
                    {
                        age = sc.nextInt();
                        sc.nextLine();
                        if (age <= 0)
                        {
                            System.out.println("Age must be a positive number.");
                        }
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Invalid input. Please enter a valid age.");
                        sc.nextLine();
                    }
                }

                String nationality = "";
                while (nationality.isEmpty())
                {
                    System.out.print("Nationality: ");
                    nationality = sc.nextLine();
                    if (nationality.isEmpty())
                    {
                        System.out.println("Nationality cannot be empty. Please try again.");
                    }
                }

                // Convert age to year of birth (you can adjust or store age directly if preferred)
                int yearOfBirth = 2025 - age;

                astronauts[i] = new Astronaut(firstName, lastName, role, yearOfBirth, nationality);
            }
        }

        // Create the new Mission object
        Mission newMission = new Mission(name, code, destination, year, successRate, manned, astronauts);

        // Add it to a new array (dynamic resizing)
        Mission[] newMissionArray = new Mission[missions.length + 1];
        for (int i = 0; i < missions.length; i++)
        {
            newMissionArray[i] = missions[i];
        }
        newMissionArray[missions.length] = newMission;

        missions = newMissionArray;

        System.out.println("\nMission added successfully.");
    }

    // NAME: editMission
    // IMPORT: missionIndex (int), field (int), value (String)
    // EXPORT: none
    // PURPOSE: Edits a selected field of a mission with full validation and clean structure
    public void editMission(int missionIndex, int field, String value)
    {
        Mission mission = missions[missionIndex];

        switch (field)
        {
            case 1:
                if (!value.isEmpty())
                {
                    mission.setMissionName(value);
                }
                else
                {
                    System.out.println("Mission name cannot be empty.");
                }
                break;

            case 2:
                if (!value.isEmpty())
                {
                    mission.setMissionCode(value);
                }
                else
                {
                    System.out.println("Mission code cannot be empty.");
                }
                break;

            case 3:
                if (!value.isEmpty())
                {
                    mission.setDestPlanet(value);
                }
                else
                {
                    System.out.println("Destination cannot be empty.");
                }
                break;

            case 4:
                try
                {
                    int year = Integer.parseInt(value);
                    if (year >= 1900 && year <= 2100)
                    {
                        mission.setLaunchYear(year);
                    }
                    else
                    {
                        System.out.println("Launch year must be between 1900 and 2100.");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Invalid input. Please enter a valid year.");
                }
                break;

            case 5:
                try
                {
                    double rate = Double.parseDouble(value);
                    if (rate >= 0 && rate <= 100)
                    {
                        mission.setSuccessRate(rate);
                    }
                    else
                    {
                        System.out.println("Success rate must be between 0 and 100.");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
                break;

            case 6:
                if (value.equals("true") || value.equals("false"))
                {
                    boolean isManned = Boolean.parseBoolean(value);
                    mission.setManned(isManned);

                    if (!isManned)
                    {
                        mission.setAstronauts(null); // Clear astronauts if unmanned
                    }
                }
                else
                {
                    System.out.println("Invalid input. Please enter 'true' or 'false'.");
                }
                break;

            default:
                System.out.println("Invalid field number. Please try again.");
        }
    }

   // NAME: sumMissionSuccessRate
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: Displays average, highest, and lowest mission success rates
    public void sumMissionSuccessRate()
    {
        // If there are no missions, exit early
        if (missions.length == 0)
        {
            System.out.println("No missions available to summarise.");
            return;
        }

        double total = 0;
        double highest = -1;
        double lowest = 101;

        Mission highestMission = null;
        Mission lowestMission = null;

        // Loop through all missions
        for (int i = 0; i < missions.length; i++)
        {
            Mission m = missions[i];

            // Skip null entries
            if (m == null)
            {
                continue;
            }

            // Add the mission's success rate to the total
            double rate = m.getSuccessRate();
            total += rate;

            // Check for highest success rate so far
            if (rate > highest)
            {
                highest = rate;
                highestMission = m;
            }

            // Check for lowest success rate so far
            if (rate < lowest)
            {
                lowest = rate;
                lowestMission = m;
            }
        }

        // Calculate the average success rate
        double average = total / missions.length;

        // Display average success rate (rounded to 2 decimal places)
        System.out.println("Average Success Rate: " + Math.round(average * 100.0) / 100.0 + "%");

        // Display highest success rate and the mission it belongs to
        if (highestMission != null)
        {
            System.out.println("Highest Success Rate: " + highest + "% (" + highestMission.getMissionName() + ")");
        }

        // Display lowest success rate and the mission it belongs to
        if (lowestMission != null)
        {
            System.out.println("Lowest Success Rate: " + lowest + "% (" + lowestMission.getMissionName() + ")");
        }
    }

    // NAME: viewAstronautsByNationality
    // IMPORT: nationality (String)
    // EXPORT: none
    // PURPOSE: Displays all astronauts with the given nationality
    public void viewAstronautsByNationality(String nationality)
    {
        boolean found = false;

        // Loop through all missions
        for (int i = 0; i < missions.length; i++)
        {
            Mission m = missions[i];

            // Skip empty or unmanned missions
            if (m == null || !m.isManned() || m.getAstronauts() == null)
            {
                continue;
            }

            Astronaut[] crew = m.getAstronauts();

            // Loop through the astronaut list of each mission
            for (int j = 0; j < crew.length; j++)
            {
                Astronaut a = crew[j];

                // If nationality matches (ignoring case), display astronaut info
                if (a != null && a.getNationality().equalsIgnoreCase(nationality))
                {
                    if (!found)
                    {
                        System.out.println("\nAstronauts from " + nationality + ":");
                        System.out.println("========================================");
                        found = true;
                    }

                    System.out.println("Name: " + a.getFirstName() + " " + a.getLastName());
                    System.out.println("Role: " + a.getRole());
                    System.out.println("Age: " + (2025 - a.getYearOfBirth()));
                    System.out.println("Mission: " + m.getMissionName());
                    System.out.println();
                }
            }
        }

        // If no astronaut matched, print a message
        if (!found)
        {
            System.out.println("No astronauts found with nationality: " + nationality);
        }
    }

    // NAME: writeFile
    // IMPORT: filename (String)
    // EXPORT: none
    // PURPOSE: Saves the current mission list to a CSV file
    public void writeFile(String filename)
    {
        PrintWriter writer = null;

        try
        {
            writer = new PrintWriter(new FileWriter(filename));

            // Write header line
            writer.println("Mission Name,Mission Code,Destination Planet,Launch Year,Success Rate,Manned Mission,Astronauts");

            // Loop through all missions and write each one
            for (int i = 0; i < missions.length; i++)
            {
                Mission m = missions[i];

                if (m == null)
                {
                    continue;
                }

                String line = m.getMissionName() + "," +
                            m.getMissionCode() + "," +
                            m.getDestPlanet() + "," +
                            m.getLaunchYear() + "," +
                            m.getSuccessRate() + "," +
                            m.isManned() + ",";

                // For manned missions, write astronaut list
                if (m.isManned() && m.getAstronauts() != null)
                {
                    Astronaut[] crew = m.getAstronauts();
                    StringBuilder crewData = new StringBuilder();

                    for (int j = 0; j < crew.length; j++)
                    {
                        Astronaut a = crew[j];
                        if (a != null)
                        {
                            // Convert year of birth back into age
                            int age = 2025 - a.getYearOfBirth();
                            crewData.append(a.getFirstName()).append(" ").append(a.getLastName())
                                    .append(":").append(a.getRole())
                                    .append(":").append(age)
                                    .append(":").append(a.getNationality());

                            // Separate astronauts with |
                            if (j < crew.length - 1 && crew[j + 1] != null)
                            {
                                crewData.append("|");
                            }
                        }
                    }

                    line += crewData.toString();
                }

                // For unmanned missions, leave astronauts field blank
                writer.println(line);
            }

            System.out.println("Mission data saved to file successfully.");
        }
        catch (IOException e)
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }

}



   