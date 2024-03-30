package dressshop.domain.qna;

import dressshop.domain.BaseEntity;
import dressshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class CmntQna extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "q_cmnt_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qna_id")
    private BoardQna qnaId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "q_c_writer_id")
    private Member qCWriterId;

    private LocalDateTime qCmntDate;
    private String qCmntContent;
}