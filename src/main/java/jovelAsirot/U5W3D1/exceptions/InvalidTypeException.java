package jovelAsirot.U5W3D1.exceptions;

public class InvalidTypeException extends RuntimeException {
    public InvalidTypeException(String type) {
        super("The type: " + "'" + type + "'" + " is not valid , input one of the following: 'desktop' - 'laptop' - 'mobile' ヽ(ヅ)ノ.");
    }
}
