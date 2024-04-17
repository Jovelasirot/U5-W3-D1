package jovelAsirot.U5W3D1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jovelAsirot.U5W3D1.entities.Employee;
import jovelAsirot.U5W3D1.exceptions.BadRequestException;
import jovelAsirot.U5W3D1.exceptions.NotFoundException;
import jovelAsirot.U5W3D1.payloads.EmployeeDTO;
import jovelAsirot.U5W3D1.repositories.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO eDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<Employee> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.eDAO.findAll(pageable);
    }

    public Employee save(EmployeeDTO payload) {
        this.eDAO.findByEmail(payload.email()).ifPresent(
                employee -> {
                    throw new BadRequestException("Email " + employee.getEmail() + " is already being used (ᗒᗣᗕ)՞ ");
                }
        );
        Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname(), payload.role());

        return eDAO.save(newEmployee);
    }

    public Employee findById(Long employeeId) {
        return this.eDAO.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public String uploadProfileImage(Long employeeId, MultipartFile image) throws IOException {
        Employee employee = findById(employeeId);

        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");

        employee.setProfileImage(url);
        eDAO.save(employee);
        return url;
    }

    public Employee updateById(Long employeeId, Employee updatedEmployee) {
        Employee employeeFound = this.findById(employeeId);

        String employeeEmail = updatedEmployee.getName() + updatedEmployee.getSurname() + "@gmail.com";
        String employeeProfileImg = "https://ui-avatars.com/api/?name=" + updatedEmployee.getName() + "+" + updatedEmployee.getSurname();

        this.eDAO.findByEmailAndId(updatedEmployee.getEmail(), employeeId).ifPresent(
                employee -> {
                    throw new BadRequestException("Email " + employee.getEmail() + " is already being used by another employee (ᗒᗣᗕ)՞ ");
                }
        );

        employeeFound.setUsername(updatedEmployee.getUsername());
        employeeFound.setName(updatedEmployee.getName());
        employeeFound.setSurname(updatedEmployee.getSurname());
        employeeFound.setEmail(updatedEmployee.getEmail() == null ? employeeEmail : updatedEmployee.getEmail());
        employeeFound.setProfileImage(employeeProfileImg);

        return this.eDAO.save(employeeFound);
    }

    public void deleteById(Long employeeId) {
        Employee employeeFound = this.findById(employeeId);

        this.eDAO.delete(employeeFound);
    }

    public Employee findByEmail(String email) {
        return eDAO.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

}
