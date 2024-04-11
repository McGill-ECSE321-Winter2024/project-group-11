
package ca.mcgill.ecse321.SportsCenterApp.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

// line 29 "model.ump"
// line 87 "model.ump"
@Entity
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
  private Integer remainingCapacity;
  private Integer roomNumber;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  //Session Associations
  @ManyToOne
  private Instructor instructor;
  @ManyToOne(optional = false) /*cascade = CascadeType.ALL)*/
  private ClassType classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(Date aDate, Time aStartTime, Time aEndTime, float aPrice, Integer aRemainingCapacity, Integer aRoomNumber, Integer aId, ClassType aClassType)
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

  public Session(Date date, Time startTime, Time endTime, float price, Integer remainingCapacity, Integer roomNumber, Instructor instructor, ClassType classType) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.price = price;
    this.remainingCapacity = remainingCapacity;
    this.roomNumber = roomNumber;
    this.instructor = instructor;
    this.classType = classType;
  }

  public Session(Date date, Time startTime, Time endTime, float price, Integer remainingCapacity, Integer roomNumber, ClassType classType) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.price = price;
    this.remainingCapacity = remainingCapacity;
    this.roomNumber = roomNumber;
    this.classType = classType;
  }

  public Session() {

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

  public boolean setRemainingCapacity(Integer aRemainingCapacity)
  {
    boolean wasSet = false;
    remainingCapacity = aRemainingCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(Integer aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
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

  public Integer getRemainingCapacity()
  {
    return remainingCapacity;
  }

  public Integer getRoomNumber()
  {
    return roomNumber;
  }

  public Integer getId()
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


  @SuppressWarnings("unlikely-arg-type")
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