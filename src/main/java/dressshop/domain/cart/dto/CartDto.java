package dressshop.domain.cart.dto;

import dressshop.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class CartDto {

    private Long id;
    private Member member;
    private int totalPrice;

    @Builder
    public CartDto(Long id, Member member, int totalPrice) {
        this.id = id;
        this.member = member;
        this.totalPrice = totalPrice;
    }
}
