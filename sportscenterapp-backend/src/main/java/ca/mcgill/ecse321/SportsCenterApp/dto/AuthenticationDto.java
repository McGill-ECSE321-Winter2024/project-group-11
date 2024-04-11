package ca.mcgill.ecse321.SportsCenterApp.dto;

import ca.mcgill.ecse321.SportsCenterApp.services.utilities.UserType;

public class AuthenticationDto {
    private String token;
    private UserType userType;
    private Integer id;

    public AuthenticationDto(){
        
    }

    public AuthenticationDto(String token, UserType userType, Integer id) {
        this.token = token;
        this.userType = userType;
        this.id = id;
    }

    public AuthenticationDto(String token, Integer id) {
        this.token = token;
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public UserType getUserType() {
        return userType;
    }
}
