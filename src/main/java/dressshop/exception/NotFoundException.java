package dressshop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class NotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public NotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_FOUND;
    }

    public NotFoundException() {

    }
}
