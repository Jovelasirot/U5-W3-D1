package jovelAsirot.U5W3D1.controllers;

import jovelAsirot.U5W3D1.entities.Employee;
import jovelAsirot.U5W3D1.exceptions.BadRequestException;
import jovelAsirot.U5W3D1.payloads.EmployeeDTO;
import jovelAsirot.U5W3D1.payloads.EmployeeResponseDTO;
import jovelAsirot.U5W3D1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDTO saveEmployee(@RequestBody @Validated EmployeeDTO payload, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new EmployeeResponseDTO(this.employeeService.save(payload).getId());
    }

    @PostMapping("/profile/image/upload/{employeeId}")
    public String uploadProfileImg(@PathVariable Long employeeId, @RequestParam("image") MultipartFile image) throws IOException {
        return this.employeeService.uploadProfileImage(employeeId, image);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MODERATOR')")
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.employeeService.getAll(page, size, sortBy);
    }

    @GetMapping("/me")
    public Employee getEmployee(@AuthenticationPrincipal Employee employee) {
        return employee;
    }

    @PutMapping("/me")
    public Employee updateMe(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @Validated @RequestBody Employee employeeBody) {
        return this.employeeService.updateById(currentAuthenticatedEmployee.getId(), employeeBody);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        this.employeeService.deleteById(currentAuthenticatedEmployee.getId());
    }

}
