package jovelAsirot.U5W3D1.controllers;

import jovelAsirot.U5W3D1.entities.Device;
import jovelAsirot.U5W3D1.payloads.DeviceDTO;
import jovelAsirot.U5W3D1.payloads.DeviceResponseDTO;
import jovelAsirot.U5W3D1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponseDTO saveDevice(@RequestBody DeviceDTO payload) {
        return new DeviceResponseDTO(this.deviceService.save(payload).getId());
    }

    @GetMapping
    public Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.deviceService.getAll(page, size, sortBy);
    }

    @GetMapping("{deviceId}")
    public Device getDevice(@PathVariable Long deviceId) {
        return this.deviceService.findById(deviceId);
    }

    @PutMapping("{deviceId}")
    public Device updateDevice(@PathVariable Long deviceId, @RequestBody DeviceDTO deviceBody) {
        return this.deviceService.updateById(deviceId, deviceBody);
    }

    @DeleteMapping("{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long deviceId) {
        this.deviceService.deleteById(deviceId);
    }

}
