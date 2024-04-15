package jovelAsirot.U5W3D1.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Record with id: " + id + " was not found ꜀( ˊ̠˂˃ˋ̠ )꜆.");
    }

    public NotFoundException(String email) {
        super("The email: " + email + " was not found");
    }
}
