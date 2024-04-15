package jovelAsirot.U5W3D1.controllers;

import jovelAsirot.U5W3D1.payloads.EmployeeDTO;
import jovelAsirot.U5W3D1.payloads.EmployeeLoginDTO;
import jovelAsirot.U5W3D1.payloads.EmployeeResponseDTO;
import jovelAsirot.U5W3D1.repositories.EmployeeDAO;
import jovelAsirot.U5W3D1.services.AuthService;
import jovelAsirot.U5W3D1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorized")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public EmployeeResponseDTO loginEmployee(@RequestBody EmployeeLoginDTO payload){
        return "token"
    }

}
