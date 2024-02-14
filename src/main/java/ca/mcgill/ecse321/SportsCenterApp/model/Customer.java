/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 23 "model.ump"
// line 82 "model.ump"
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

  public Customer(String aFirstName, String aLastName, String aEmail, String aPassword, int aId, float aAccoutBalance)
  {
    super(aFirstName, aLastName, aEmail, aPassword, aId);
    accoutBalance = aAccoutBalance;
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