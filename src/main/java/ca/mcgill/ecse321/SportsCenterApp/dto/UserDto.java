package ca.mcgill.ecse321.SportsCenterApp.dto;



public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public UserDto(String firstName, String lastName, String email, String password, Integer id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public UserDto() {
    }
}
