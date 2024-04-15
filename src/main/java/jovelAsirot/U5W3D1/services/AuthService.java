package jovelAsirot.U5W3D1.services;

import jovelAsirot.U5W3D1.entities.Employee;
import jovelAsirot.U5W3D1.exceptions.UnauthorizedException;
import jovelAsirot.U5W3D1.payloads.EmployeeLoginDTO;
import jovelAsirot.U5W3D1.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtTools jwtTools;

    public String authenticateEmployeeAndGenerateToken(EmployeeLoginDTO payload) {

        Employee employee = this.employeeService.findByEmail(payload.email());

        if (employee.getPassword().equals(payload.password())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Invalid email or password,  try again.");
        }
    }
}
