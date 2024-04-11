package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class ItemNotStockException extends DressshopException {

    private static final String message = "재고가 부족하여 주문할 수 없습니다.";

    public ItemNotStockException() {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 400;
    }
}
