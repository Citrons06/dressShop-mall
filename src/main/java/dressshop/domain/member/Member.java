package dressshop.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "auth_id")
    private MemberAuth memberAuth;

    private String name;

    private String password;

    private String nickname;

    private String email;

    private String tel;

    @Embedded
    private Address address;
}
