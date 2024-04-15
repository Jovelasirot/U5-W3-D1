package jovelAsirot.U5W3D1.controllers;

import jovelAsirot.U5W3D1.exceptions.BadRequestException;
import jovelAsirot.U5W3D1.payloads.EmployeeDTO;
import jovelAsirot.U5W3D1.payloads.EmployeeLoginDTO;
import jovelAsirot.U5W3D1.payloads.EmployeeLoginResponseDTO;
import jovelAsirot.U5W3D1.payloads.EmployeeResponseDTO;
import jovelAsirot.U5W3D1.services.AuthService;
import jovelAsirot.U5W3D1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorized")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public EmployeeLoginResponseDTO loginEmployee(@RequestBody EmployeeLoginDTO payload) {
        return new EmployeeLoginResponseDTO(this.authService.authenticateEmployeeAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDTO registerEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new EmployeeResponseDTO(this.employeeService.save(body).getId());
    }


}
