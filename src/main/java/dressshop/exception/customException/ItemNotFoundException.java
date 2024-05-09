package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class ItemNotFoundException extends DressshopException {

    private static final String message = "해당 상품을 찾을 수 없습니다.";

    public ItemNotFoundException() {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 404;
    }
}
