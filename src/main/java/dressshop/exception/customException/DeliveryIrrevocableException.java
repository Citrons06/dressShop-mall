package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class DeliveryIrrevocableException extends DressshopException {

    private static final String message = "배송이 완료되어 취소할 수 없습니다. 환불 문의 부탁드립니다.";

    public DeliveryIrrevocableException() {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 400;
    }
}
