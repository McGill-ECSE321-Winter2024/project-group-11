/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Date;
import java.sql.Time;

// line 29 "model.ump"
// line 87 "model.ump"
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  private Date date;
  private Time startTime;
  private Time endTime;
  private float price;
  private int remainingCapacity;
  private int roomNumber;
  private int id;

  //Session Associations
  private Instructor instructor;
  private ClassType classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(Date aDate, Time aStartTime, Time aEndTime, float aPrice, int aRemainingCapacity, int aRoomNumber, int aId, ClassType aClassType)
  {
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    price = aPrice;
    remainingCapacity = aRemainingCapacity;
    roomNumber = aRoomNumber;
    id = aId;
    if (!setClassType(aClassType))
    {
      throw new RuntimeException("Unable to create Session due to aClassType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(float aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setRemainingCapacity(int aRemainingCapacity)
  {
    boolean wasSet = false;
    remainingCapacity = aRemainingCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(int aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public float getPrice()
  {
    return price;
  }

  public int getRemainingCapacity()
  {
    return remainingCapacity;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Instructor getInstructor()
  {
    return instructor;
  }

  public boolean hasInstructor()
  {
    boolean has = instructor != null;
    return has;
  }
  /* Code from template association_GetOne */
  public ClassType getClassType()
  {
    return classType;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setInstructor(Instructor aNewInstructor)
  {
    boolean wasSet = false;
    instructor = aNewInstructor;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setClassType(ClassType aNewClassType)
  {
    boolean wasSet = false;
    if (aNewClassType != null)
    {
      classType = aNewClassType;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    instructor = null;
    classType = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "," +
            "remainingCapacity" + ":" + getRemainingCapacity()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "classType = "+(getClassType()!=null?Integer.toHexString(System.identityHashCode(getClassType())):"null");
  }
}