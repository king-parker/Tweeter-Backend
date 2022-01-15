package edu.byu.cs.tweeter.model.net.request;

public class RegisterRequest extends Request {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String imageBytesBase64;

    private RegisterRequest() {super();}

    public RegisterRequest(String firstName, String lastName, String username, String password, String imageBytesBase64) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.imageBytesBase64 = imageBytesBase64;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageBytesBase64() {
        return imageBytesBase64;
    }

    public void setImageBytesBase64(String imageBytesBase64) {
        this.imageBytesBase64 = imageBytesBase64;
    }
}
