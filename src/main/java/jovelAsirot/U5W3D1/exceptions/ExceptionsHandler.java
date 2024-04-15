package jovelAsirot.U5W3D1.exceptions;


import jovelAsirot.U5W3D1.payloads.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsPayLoad handleNotFound(NotFoundException ex) {
        return new ErrorsPayLoad(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponseDTO handleBadRequest(BadRequestException ex) {
        if (ex.getErrorsList() != null) {
            String message = ex.getErrorsList().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(" | "));
            return new ErrorResponseDTO(message, LocalDateTime.now());

        } else {
            return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponseDTO handleInvalidStatus(InvalidStatusException ex) {
        ex.printStackTrace();
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(InvalidTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponseDTO handleInvalidType(InvalidTypeException ex) {
        ex.printStackTrace();
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponseDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ErrorResponseDTO("Missing a value", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorResponseDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsPayLoad handleGenericErrors(Exception ex) {
        ex.printStackTrace();
        return new ErrorsPayLoad("Error server side", LocalDateTime.now());
    }
}
