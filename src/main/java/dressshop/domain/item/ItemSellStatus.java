package dressshop.domain.item;

import lombok.Getter;

@Getter
public enum ItemSellStatus {
    SELL("판매중"),
    SOLD_OUT("품절"),
    STOP_SELL("판매 중지");

    private final String displayStatus;

    ItemSellStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }
}