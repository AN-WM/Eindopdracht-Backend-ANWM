package nl.novi.EindopdrachtBackend.exceptions;

public class IndexOutOfBoundsException extends RuntimeException {
    public IndexOutOfBoundsException() {
        super("This index is out of bounds");
    }

    public IndexOutOfBoundsException(String message) {
        super(message);
    }
}
