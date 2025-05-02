import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/******************************************
 * Author: Celerina Reyes                 *
 * Created: 17/04/2025                    *
 * Purpose: Manages the system's missions *
 *****************************************/

public class MissionController
{
    public String[][] missionsData; // A 2D array to store raw mission data from the CVS file      
    public Mission[] missions; // An array to store mission obects after processing the data from the CVS file

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

    public void createMissions()
    {
        String[] astronautsList = new String[5];

        //initialise the mission array
        missions = new Mission[missionsData.length];

        for (int i = 0; i < missionsData.length; i++)
        {
            if (missionsData[i][0] == null)
            {
                continue; // So empty rows are skipped :))
            }

             = missionsData[i][6].split(;);

            // This will create a new Book object for each row of data...
            Mission newMission = new Mission(missionsData[i][0], Integer.parseInt(booksData[i][13]), booksData[i][14], Boolean.parseBoolean(booksData[i][15]), Integer.parseInt(booksData[i][16]));
            
        }

    // NAME: viewAllBooks
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: prints the details of ALL the books of the library :)
//     public void viewAllBooks()
//     {
//         for (int i = 0; i < books.length; i++)
//         {
//             if (books[i] == null)
//             {
//                 continue; // NULL entries will be SKIPPED >:D
//             }

//             System.out.println();
//             System.out.println("Title: " + books[i].getTitle());
//             System.out.println("Author 1: " + books[i].getAuthors()[0].getFirstName() + " " + books[i].getAuthors()[0].getLastName() + " (" + books[i].getAuthors()[0].getNationality() + ", Born: " + books[i].getAuthors()[0].getYearOfBirth() + ")");

//             // If theres more than 1 author, their details will also be printed
//             if ((books[i].getAuthors().length > 1) && (books[i].getAuthors()[1] != null))
//             {
//                 System.out.println("Author 2: " + books[i].getAuthors()[1].getFirstName() + " " + books[i].getAuthors()[1].getLastName() + " (" + books[i].getAuthors()[1].getNationality() + ", Born: " + books[i].getAuthors()[1].getYearOfBirth() + ")");
//             }

//             if ((books[i].getAuthors().length > 2) && (books[i].getAuthors()[2] != null))
//             {
//                 System.out.println("Author 3: " + books[i].getAuthors()[2].getFirstName() + " " + books[i].getAuthors()[2].getLastName() + " (" + books[i].getAuthors()[2].getNationality() + ", Born: " + books[i].getAuthors()[2].getYearOfBirth() + ")");
//             }

//             System.out.println("Year: " + books[i].getYear());
//             System.out.println("ISBN: " + books[i].getIsbn());
//             System.out.println("eBook: " + books[i].isEbook());
//             System.out.println("Edition: " + books[i].getEdition());
//             System.out.println();
//         }
//     }
// }
