package jovelAsirot.U5W3D1.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("The status: " + "'" + status + "'" + " is not valid, input one of the following: 'available' - 'assigned' - 'maintenance' - 'dismissed' ಥ‿ಥ");
    }
}
