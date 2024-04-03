package ca.mcgill.ecse321.SportsCenterApp.dto;

import ca.mcgill.ecse321.SportsCenterApp.services.utilities.UserType;

public class AuthenticationDto {
    private String token;
    private UserType userType;

    public AuthenticationDto(String token, UserType userType) {
        this.token = token;
        this.userType = userType;
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
