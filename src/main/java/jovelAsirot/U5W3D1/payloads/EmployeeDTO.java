package jovelAsirot.U5W3D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(@NotEmpty(message = "The username is required")
                          String username,
                          @NotEmpty(message = "The name is required")
                          @Size(min = 2, max = 30, message = "The name can't be less than two characters and more than 30 characters")
                          String name,
                          @NotEmpty(message = "The surname is required")
                          @Size(min = 2, max = 30, message = "The surname can't be less than two characters and more than 30 characters")
                          String surname,
                          @NotEmpty(message = "The email is required")
                          @Email(message = "The email given is invalid")
                          String email,
                          @NotEmpty(message = "The password is required")
                          @Size(min = 8, message = "The password can't be less than eight characters characters")
                          String password
) {
}
