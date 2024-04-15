package jovelAsirot.U5W3D1.services;

import jovelAsirot.U5W3D1.entities.Device;
import jovelAsirot.U5W3D1.entities.Employee;
import jovelAsirot.U5W3D1.exceptions.BadRequestException;
import jovelAsirot.U5W3D1.exceptions.InvalidStatusException;
import jovelAsirot.U5W3D1.exceptions.InvalidTypeException;
import jovelAsirot.U5W3D1.exceptions.NotFoundException;
import jovelAsirot.U5W3D1.payloads.DeviceDTO;
import jovelAsirot.U5W3D1.repositories.DeviceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceDAO dDAO;

    @Autowired
    private EmployeeService employeeService;

    public Page<Device> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.dDAO.findAll(pageable);
    }


    public Device save(DeviceDTO payload) {

        Device newDevice;

        switch (payload.status()) {
            case "available", "maintenance", "dismissed" -> {
                newDevice = new Device(typeChecker(payload.type()), payload.status(), null);
            }
            case "assigned" -> {
                Employee employee = employeeService.findById(payload.employeeId());
                if (employee == null) {
                    throw new NotFoundException(payload.employeeId());
                } else {
                    newDevice = new Device(typeChecker(payload.type()), payload.status(), employee);
                }
            }
            default -> throw new InvalidStatusException(payload.status());
        }

        return dDAO.save(newDevice);
    }

    public Device findById(Long deviceId) {

        return this.dDAO.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
    }

    public Device updateById(Long deviceId, DeviceDTO updatedDevice) {
        Device deviceFound = this.findById(deviceId);

        if (updatedDevice.type() == null) {
            deviceFound.setType(deviceFound.getType());
        } else {
            deviceFound.setType(typeChecker(updatedDevice.type()));
        }

        if (updatedDevice.employeeId() != null) {
            Employee employee = employeeService.findById(updatedDevice.employeeId());

            if (deviceFound.getEmployee() != null && updatedDevice.employeeId().equals(deviceFound.getEmployee().getId())) {
                throw new BadRequestException("The employee with id: " + employee.getId() + " already has the device " + deviceId + " - " + deviceFound.getType());
            }

            if (deviceFound.getStatus().equals("assigned")) {
                throw new BadRequestException("The device with id: " + deviceId + " is already assigned to another employee");
            }

            deviceFound.setEmployee(employee);
            deviceFound.setStatus("assigned");
        } else {

            deviceFound.setEmployee(null);
            deviceFound.setStatus("available");
        }

        if (updatedDevice.status() != null) {
            switch (updatedDevice.status()) {
                case "available", "maintenance", "dismissed" -> {
                    deviceFound.setStatus(updatedDevice.status());
                    deviceFound.setEmployee(null);
                }
                default -> throw new InvalidStatusException(updatedDevice.status());
            }
        } else {
            deviceFound.setStatus(deviceFound.getStatus());
        }

        return this.dDAO.save(deviceFound);
    }

    public String typeChecker(String type) {
        switch (type) {
            case "desktop", "laptop", "mobile" -> {
                return type;
            }
            default -> throw new InvalidTypeException(type);
        }
    }

    public void deleteById(Long deviceId) {
        Device deviceFound = this.findById(deviceId);

        this.dDAO.delete(deviceFound);
    }

}
