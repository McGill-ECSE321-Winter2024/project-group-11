package ca.mcgill.ecse321.SportsCenterApp.dto;


public class CustomerDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private float accountBalance;

    private String token;

    public CustomerDto(String firstName, String lastName, String email, String password, String token, float accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.accountBalance = accountBalance;
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

    public float getAccountBalance() {
        return accountBalance;
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

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
}
