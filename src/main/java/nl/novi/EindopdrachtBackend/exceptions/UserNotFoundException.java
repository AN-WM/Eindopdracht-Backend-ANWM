package nl.novi.EindopdrachtBackend.exceptions;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long employeeId) {
        super("Cannot find user " + employeeId);
    }

}