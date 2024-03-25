package ca.mcgill.ecse321.SportsCenterApp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// line 47 "model.ump"
// line 97 "model.ump"
@Entity
public class ClassType
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DifficultyLevel { Beginner, Intermediate, Advanced }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClassType Attributes
  private String name;
  private String description;
  private boolean approved;
  private DifficultyLevel difficultyLevel;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClassType(String aName, String aDescription, boolean aApproved, DifficultyLevel aDifficultyLevel, Integer aId)
  {
    name = aName;
    description = aDescription;
    approved = aApproved;
    difficultyLevel = aDifficultyLevel;
    id = aId;
  }

  public ClassType(String name, String description, boolean approved, DifficultyLevel difficultyLevel) {
    this.name = name;
    this.description = description;
    this.approved = approved;
    this.difficultyLevel = difficultyLevel;
  }

  public ClassType() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setApproved(boolean aApproved)
  {
    boolean wasSet = false;
    approved = aApproved;
    wasSet = true;
    return wasSet;
  }

  public boolean setDifficultyLevel(DifficultyLevel aDifficultyLevel)
  {
    boolean wasSet = false;
    difficultyLevel = aDifficultyLevel;
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

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public boolean getApproved()
  {
    return approved;
  }

  public DifficultyLevel getDifficultyLevel()
  {
    return difficultyLevel;
  }

  public Integer getId()
  {
    return id;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isApproved()
  {
    return approved;
  }

  public void delete()
  {}


  @SuppressWarnings("unlikely-arg-type")
  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "approved" + ":" + getApproved()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "difficultyLevel" + "=" + (getDifficultyLevel() != null ? !getDifficultyLevel().equals(this)  ? getDifficultyLevel().toString().replaceAll("  ","    ") : "this" : "null");
  }
}