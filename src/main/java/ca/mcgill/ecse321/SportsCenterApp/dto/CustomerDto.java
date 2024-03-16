package ca.mcgill.ecse321.SportsCenterApp.dto;

public class CustomerDto extends UserDto {

    private float aAccoutBalance;


    public CustomerDto() {
        super();
    }

    public CustomerDto(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aId, float aAccoutBalance) {
        super(aFirstName, aLastName, aEmail, aPassword, aId);
        this.aAccoutBalance = aAccoutBalance;
    }

    public float getAccoutBalance() {
        return aAccoutBalance;
    }

    public void setAccountBalance(float aAccoutBalance) {
        this.aAccoutBalance = aAccoutBalance;
    }

    

}
