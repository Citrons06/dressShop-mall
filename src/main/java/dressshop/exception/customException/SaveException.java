package dressshop.exception.customException;

import dressshop.exception.DressshopException;

public class SaveException extends DressshopException {

    private static final String message = "저장에 실패했습니다. 다시 시도해주세요.";

    public SaveException() {
        super(message);
    }

    @Override
    public int StatusCode() {
        return 400;
    }
}
