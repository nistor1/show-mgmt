package ro.ps.showmgmtbackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents an enumeration with required exceptions
 */
@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    ERR001_SHOW_NOT_FOUND("Show with ID %s not found"),
    ERR099_INVALID_CREDENTIALS("Invalid credentials.");

    private final String message;

}
