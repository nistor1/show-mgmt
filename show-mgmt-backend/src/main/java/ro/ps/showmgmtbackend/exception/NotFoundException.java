package ro.ps.showmgmtbackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a not found exception
 */
@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String message;
}
