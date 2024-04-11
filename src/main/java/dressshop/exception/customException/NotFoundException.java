package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class NotFoundException extends DressshopException {

    private static final String message = "해당하는 정보를 찾을 수 없습니다.";

    public NotFoundException() {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 404;
    }
}
