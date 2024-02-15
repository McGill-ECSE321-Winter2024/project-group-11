package ca.mcgill.ecse321.SportsCenterApp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 2 "model.ump"
// line 67 "model.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Integer id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aId)
  {
    firstName = aFirstName;
    lastName = aLastName;
    email = aEmail;
    password = aPassword;
    id = aId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(Integer aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public Integer getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "id" + ":" + getId()+ "]";
  }
}