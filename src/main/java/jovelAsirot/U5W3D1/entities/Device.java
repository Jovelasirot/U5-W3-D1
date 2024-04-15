package jovelAsirot.U5W3D1.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    private String type;

    private String status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(String type, String status, Employee employee) {
        this.type = type;
        this.status = status;
        this.employee = employee;
    }
}
