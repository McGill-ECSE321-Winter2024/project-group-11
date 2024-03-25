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
  private float accountBalance;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public Customer(String firstName, String lastName, String email, String password, Integer id, String token, float accountBalance) {
    super(firstName, lastName, email, password, id, token);
    this.accountBalance = accountBalance;
  }

  public Customer() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccountBalance(float aAccountBalance)
  {
    boolean wasSet = false;
    accountBalance = aAccountBalance;
    wasSet = true;
    return wasSet;
  }

  public float getAccountBalance()
  {
    return accountBalance;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "accountBalance" + ":" + getAccountBalance()+ "]";
  }
}