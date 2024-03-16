package ca.mcgill.ecse321.SportsCenterApp.dto;

public class OwnerDto extends UserDto {

    public OwnerDto(){
        super();
    }
    public OwnerDto(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aId)
    {
      super(aFirstName, aLastName, aEmail, aPassword, aId);
    }
}