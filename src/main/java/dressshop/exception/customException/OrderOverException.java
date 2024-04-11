package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class OrderOverException extends DressshopException {

    private static final String message = "주문은 한 번에 20가지 까지만 가능합니다.";

    public OrderOverException() {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 307;
    }
}
