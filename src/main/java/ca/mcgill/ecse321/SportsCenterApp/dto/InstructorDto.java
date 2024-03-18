package ca.mcgill.ecse321.SportsCenterApp.dto;

public class InstructorDto extends UserDto {

    private Integer aYearsOfExperience;
    private String aBiography;


    public InstructorDto() {
        super();
    }


    public InstructorDto(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aId, Integer aYearsOfExperience, String aBiography) {
        super(aFirstName, aLastName, aEmail, aPassword, aId);
        this.aYearsOfExperience = aYearsOfExperience;
        this.aBiography = aBiography;
    }
    

    public Integer getYearsOfExperience(){
        return this.aYearsOfExperience;
    }


    public String getBiography(){
        return this.aBiography;
    }

    public void setYearsOfExperience(Integer yearsofexp){
        this.aYearsOfExperience = yearsofexp;
    }

    public void setBiography(String biography){
        this.aBiography = biography;
    }
}
