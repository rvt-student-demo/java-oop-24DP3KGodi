package rvt;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class student {
    private String firstName;
    private String lastName;
    private String email;
    private String personalCode;
    private String registrationDate;

    public void Student(String firstName, String lastName, String email, String personalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalCode = personalCode;
        this.registrationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPersonalCode() { return personalCode; }
    public String getRegistrationDate() { return registrationDate; }

     public String toCSV() {
        return String.join(",", firstName, lastName, email, personalCode, registrationDate);
    }
}