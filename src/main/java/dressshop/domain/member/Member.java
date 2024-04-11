package dressshop.domain.member;

import dressshop.domain.BaseEntity;
import dressshop.domain.member.dto.MemberDto;
import dressshop.domain.order.Order;
import dressshop.domain.qna.BoardQna;
import dressshop.domain.qna.CmntQna;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(value = STRING)
    private MemberAuth memberAuth;

    private String name;

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

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<BoardQna> boardQnaList = new ArrayList<>();

    @OneToMany(mappedBy = "qCWriterId", fetch = LAZY)
    private List<CmntQna> cmntQnaList = new ArrayList<>();

    @Builder
    public Member(MemberAuth memberAuth, String name, String password, String nickname, String email, String tel, Address address) {
        this.memberAuth = memberAuth;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.tel = tel;
        this.address = address;
    }

    public MemberDto.MemberDtoBuilder toEditor() {
        return Member.builder()
                .name(name)
                .password(password)
                .nickname(nickname)
                .email(email)
                .tel(tel)
                .address(address)
                .build().toEditor();
    }

    //수정 로직
    public void editMember(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.password = memberDto.getPassword();
        this.nickname = memberDto.getNickname();
        this.email = memberDto.getEmail();
        this.tel = memberDto.getTel();
        this.address = memberDto.getAddress();
    }
}
