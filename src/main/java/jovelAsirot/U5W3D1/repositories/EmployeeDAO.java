package jovelAsirot.U5W3D1.repositories;


import jovelAsirot.U5W3D1.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmailAndId(String email, Long employeeId);
}
