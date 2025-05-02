/*******************************************
 * Author: Celerina Reyes                 *
 * Created: 17/04/2025                    *
 * Purpose: Represents a space Mission   *
 *******************************************/

 public class Mission
 {
    // These are the private fields to store mission details 
    private String missionName;
    private String missionCode;
    private String destPlanet;
    private int launchYear;
    private double successRate;
    private boolean isManned;
    private Astronaut[] astronauts;

    // NAME: Mission
    // IMPORT: none
    // EXPORT: none
    // PURPOSE: default constructor with basic values 
    public Mission()
    {
    this.missionName = "None";
    this.missionCode = "None";
    this.destPlanet = "None";
    this.launchYear = 0;
    this.successRate = 0.0;
    this.isManned = false;
    this.astronauts = null;
    }

    // NAME: Mission
    // IMPORT: all mission values including astronaut array 
    // EXPORT: none
    // PURPOSE: full constructor to build a Mission object from CSV or user input 
    public Mission(String missionName, String missionCode, String destPlanet, int launchYear, double successRate, boolean isManned, Astronaut[] astronauts)
    {
        this.missionName = missionName;
        this.missionCode = missionCode;
        this.destPlanet = destPlanet;
        this.launchYear = launchYear;
        this.successRate = successRate;
        this.isManned = isManned;
        this.astronauts = astronauts;
    }

    // All the getters and setters for the mission fields

    public String getMissionName()
    {
        return missionName;
    }
    public void setMissionName(String missionName)
    {
        this.missionName = missionName;
    }

    public String getMissionCode()
    {
        return missionCode;
    }
    public void setMissionCode(String missionCode)
    {
        this.missionCode = missionCode;
    }

    public String getDestPlanet()
    {
        return destPlanet;
    }
    public void setDestPlanet(String destPlanet)
    {
        this.destPlanet = destPlanet;
    }

    public int getLaunchYear()
    {
        return launchYear;
    }
    public void setLaunchYear(int launchYear)
    {
        this.launchYear = launchYear;
    }

    public double getSuccessRate()
    {
        return successRate;
    }
    public void setSuccessRate(double successRate)
    {
        this.successRate = successRate;
    }

    public boolean isManned()
    {
        return isManned;
    }
    public void setManned(boolean isManned)
    {
        this.isManned = isManned;
    }

    public Astronaut[] getAstronauts()
    {
        return astronauts;
    }
    public void setAstronauts(Astronaut[] astronauts)
    {
        this.astronauts = astronauts;
    }
 }