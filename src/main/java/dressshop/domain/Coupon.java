package dressshop.domain;

import dressshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Coupon {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cpn_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    private String cpnName;
    private int cpnDiscountPrice;  //할인 금액
    private int cpnDiscountRate;   //할인율
    private int cpnCount;          //쿠폰 수량
    private LocalDateTime cpnExpirePer;  //만료 기간
    private String cpnInfo;        //쿠폰 정보

    @Builder
    public Coupon(Member member,
                  String cpnName,
                  int cpnDiscountPrice,
                  int cpnDiscountRate,
                  int cpnCount,
                  LocalDateTime cpnExpirePer,
                  String cpnInfo) {
        this.member = member;
        this.cpnName = cpnName;
        this.cpnDiscountPrice = cpnDiscountPrice;
        this.cpnDiscountRate = cpnDiscountRate;
        this.cpnCount = cpnCount;
        this.cpnExpirePer = cpnExpirePer;
        this.cpnInfo = cpnInfo;
    }
}
