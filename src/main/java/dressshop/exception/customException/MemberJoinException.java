package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class MemberJoinException extends DressshopException {

    public MemberJoinException(String message) {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 400;
    }
}
