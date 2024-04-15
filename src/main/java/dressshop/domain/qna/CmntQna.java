package dressshop.domain.qna;

import dressshop.domain.BaseEntity;
import dressshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class CmntQna extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "q_cmnt_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "qna_id")
    private BoardQna qnaId;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member qCWriterId;

    private LocalDateTime qCmntDate;
    private String qCmntContent;
}