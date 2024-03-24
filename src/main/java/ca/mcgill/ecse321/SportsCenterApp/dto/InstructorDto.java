package ca.mcgill.ecse321.SportsCenterApp.dto;

public class InstructorDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;
    private Integer aYearsOfExperience;
    private String aBiography;

    public InstructorDto(String firstName, String lastName, String email, String password, String token, Integer YearsOfExperience, String Biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.aYearsOfExperience = YearsOfExperience;
        this.aBiography = Biography;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Integer getYearsOfExperience() {
        return aYearsOfExperience;
    }

    public String getBiography() {
        return aBiography;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setYearsOfExperience(Integer aYearsOfExperience) {
        this.aYearsOfExperience = aYearsOfExperience;
    }

    public void setBiography(String aBiography) {
        this.aBiography = aBiography;
    }
}
