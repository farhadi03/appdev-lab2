package ie.spring.labs.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// This is a DTO (data transfer object) because it is only used to
// transfer data between layers in the application. It really doesn't
// do anything else. It could, therefore, be a record but I am leaving
// it as a class. We will use records later.
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Guest {
    private Long guestId;
    private Person name;
    private Person plusOne;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String weddingId;
}
