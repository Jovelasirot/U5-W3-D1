package jovelAsirot.U5W3D1.repositories;

import jovelAsirot.U5W3D1.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDAO extends JpaRepository<Device, Long> {
}
