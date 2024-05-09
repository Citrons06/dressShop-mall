package dressshop.domain.cart.dto;

import dressshop.domain.cart.Cart;
import dressshop.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class CartDto {

    private Long id;
    private Member member;

    @Builder
    public CartDto(Long id, Member member) {
        this.id = id;
        this.member = member;
    }
}
