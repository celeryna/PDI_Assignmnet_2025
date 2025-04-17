/***********************************************
 * Author: Celerina Reyes                      *
 * Created: 17/04/2025                         *
 * Purpose: Represents an Astronaut on Mission *
 ***********************************************/

public class Astronaut 
{
     // These are the private fields to store astronaut details :)
     private String firstName;
     private String lastName;
     private String role;
     private int yearOfBirth;
     private String nationality;

    // NAME: Astronaut
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: constructor to initialise the astronaut with default values :P
    public Astronaut()
    {
        this.firstName = "None";
        this.lastName = "None";
        this.role = "None";
        this.yearOfBirth = 0;
        this.nationality = "None";
    }

    // NAME: Astronaut
    // IMPORT: firstName (String), lastName (String), role (String), yearOfBirth (int), nationality (String)
    // EXPORT: none
    // PURPOSE: this constructor sets all fields for the astronaut :D
    public Astronaut(String firstName, String lastName, String role, int yearOfBirth, String nationality)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.yearOfBirth = yearOfBirth;
        this.nationality = nationality;
    }

     // Getters and Setters for all fields, just like in your Author class :)

     public String getFirstName()
     {
         return firstName;
     }
     public void setFirstName(String firstName)
     {
         this.firstName = firstName;
     }
 
     public String getLastName()
     {
         return lastName;
     }
     public void setLastName(String lastName)
     {
         this.lastName = lastName;
     }
 
     public String getRole()
     {
         return role;
     }
     public void setRole(String role)
     {
         this.role = role;
     }
 
     public int getYearOfBirth()
     {
         return yearOfBirth;
     }
     public void setYearOfBirth(int yearOfBirth)
     {
         this.yearOfBirth = yearOfBirth;
     }
 
     public String getNationality()
     {
         return nationality;
     }
     public void setNationality(String nationality)
     {
         this.nationality = nationality;
     }
}
