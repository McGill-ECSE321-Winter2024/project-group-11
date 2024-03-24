package ca.mcgill.ecse321.SportsCenterApp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;

// line 16 "model.ump"
// line 77 "model.ump"
@Entity
public class Instructor extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Attributes
  private Integer yearsOfExperience;
  private String biography;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public Instructor(String firstName, String lastName, String email, String password, String token, Integer yearsOfExperience, String biography) {
    super(firstName, lastName, email, password, token);
    this.yearsOfExperience = yearsOfExperience;
    this.biography = biography;
  }

  public Instructor() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setYearsOfExperience(Integer aYearsOfExperience)
  {
    boolean wasSet = false;
    yearsOfExperience = aYearsOfExperience;
    wasSet = true;
    return wasSet;
  }

  public boolean setBiography(String aBiography)
  {
    boolean wasSet = false;
    biography = aBiography;
    wasSet = true;
    return wasSet;
  }

  public Integer getYearsOfExperience()
  {
    return yearsOfExperience;
  }

  public String getBiography()
  {
    return biography;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "yearsOfExperience" + ":" + getYearsOfExperience()+ "," +
            "biography" + ":" + getBiography()+ "]";
  }
}