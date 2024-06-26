package dressshop.domain.member;

import dressshop.domain.BaseEntity;
import dressshop.domain.member.dto.MemberDto;
import dressshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(value = STRING)
    private MemberAuth memberAuth;

    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String tel;

    @Column(unique = true)
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", fetch = LAZY)
    public List<Order> orderList = new ArrayList<>();

    private String provider;
    private String providerId;

    @Builder
    public Member(Long id,
                  MemberAuth memberAuth,
                  String username,
                  String password,
                  String nickname,
                  String email,
                  String tel,
                  Address address,
                  String provider,
                  String providerId) {
        this.id = id;
        this.memberAuth = memberAuth;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.provider = provider;
        this.providerId = providerId;
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(id)
                .name(username)
                .password(password)
                .email(email)
                .tel(tel)
                .build();
    }

    public MemberDto.MemberDtoBuilder toEditor() {
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .tel(tel)
                .build().toEditor();
    }

    //수정 로직
    public void editMember(MemberDto memberDto) {
        this.username = memberDto.getUsername();
        this.password = memberDto.getPassword();
        this.email = memberDto.getEmail();
        this.tel = memberDto.getTel();
    }

    public void passwordEncode(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void disableMember() {
        this.memberAuth = MemberAuth.ROLE_DISABLE;
    }
}