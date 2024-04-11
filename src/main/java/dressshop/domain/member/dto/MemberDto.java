package dressshop.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor
public class MemberDto {

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "패스워드를 입력하세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Column(unique = true)
    private String nickname;

    @NotBlank(message = "이메일을 입력하세요.")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String tel;

    @NotBlank(message = "주소를 입력하세요.")
    @Column(unique = true)
    private Address address;

    @Builder
    @QueryProjection
    public MemberDto(String name,
                     String password,
                     String nickname,
                     String email,
                     String tel,
                     Address address) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.tel = tel;
        this.address = address;
    }

    public MemberDto(Member member) {
    }
}