package dressshop.domain.item;

import dressshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Cart {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart", fetch = LAZY)
    private List<Item> items = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer cart_quantity;
}