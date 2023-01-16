package nl.novi.EindopdrachtBackend.exceptions;

public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException() {
        super("This item already exists");
    }

    public DuplicateRecordException(String message) {
        super(message);
    }
}
