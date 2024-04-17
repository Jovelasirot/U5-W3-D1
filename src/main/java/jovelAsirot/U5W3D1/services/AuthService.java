package jovelAsirot.U5W3D1.services;

import jovelAsirot.U5W3D1.entities.Employee;
import jovelAsirot.U5W3D1.exceptions.UnauthorizedException;
import jovelAsirot.U5W3D1.payloads.EmployeeLoginDTO;
import jovelAsirot.U5W3D1.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateEmployeeAndGenerateToken(EmployeeLoginDTO payload) {

        Employee employee = this.employeeService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(), employee.getPassword())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Invalid email or password,  try again.");
        }
    }
}
