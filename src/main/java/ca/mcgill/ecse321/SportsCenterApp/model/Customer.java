package ca.mcgill.ecse321.SportsCenterApp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;

// line 23 "model.ump"
// line 82 "model.ump"
@Entity
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private float accoutBalance;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public Customer(String firstName, String lastName, String email, String password, String token, float accoutBalance) {
    super(firstName, lastName, email, password, token);
    this.accoutBalance = accoutBalance;
  }

  public Customer() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccoutBalance(float aAccoutBalance)
  {
    boolean wasSet = false;
    accoutBalance = aAccoutBalance;
    wasSet = true;
    return wasSet;
  }

  public float getAccoutBalance()
  {
    return accoutBalance;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "accoutBalance" + ":" + getAccoutBalance()+ "]";
  }
}