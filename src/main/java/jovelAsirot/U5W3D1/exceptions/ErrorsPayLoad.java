package jovelAsirot.U5W3D1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorsPayLoad {
    private String message;
    private LocalDateTime timestamp;
}
