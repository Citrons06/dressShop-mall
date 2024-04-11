package dressshop.domain.qna;

import dressshop.domain.BaseEntity;
import dressshop.domain.item.Item;
import dressshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class BoardQna extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "qna_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String qnaTitle;
    private String qnaPwd;
    private String qnaContent;
    private String qnaCategory;
    private LocalDateTime qnaDate;
    private Integer qnaReadcount;
}
