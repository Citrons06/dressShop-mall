package dressshop.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dressshop.domain.item.Cart;
import dressshop.domain.qna.CmntQna;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "auth_id")
    private MemberAuth memberAuth;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String email;

    @NotEmpty
    private String tel;

    @Embedded
    private Address address;
}
