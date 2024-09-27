package ro.ps.showmgmtbackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a generic Exception
 */
@Getter
@RequiredArgsConstructor
public class ExceptionBody {
    private final String message;
    private final LocalDateTime timestamp;

    /**
     * Constructor for the ExceptionBody class
     *
     * @param message The message of the exception
     */
    public ExceptionBody(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
