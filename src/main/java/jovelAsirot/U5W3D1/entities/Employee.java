package jovelAsirot.U5W3D1.entities;

import jakarta.persistence.*;
import jovelAsirot.U5W3D1.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    private String username;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(String username, String name, String surname, String email, String password, String profileImage, String role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.role = Role.valueOf(role);
    }
}
