package ca.mcgill.ecse321.SportsCenterApp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;

// line 11 "model.ump"
// line 72 "model.ump"
@Entity
public class Owner extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aId)
  {
    super(aFirstName, aLastName, aEmail, aPassword, aId);
  }

  public Owner() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}