package dressshop.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    @Column(unique = true)
    private String email;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String tel;

    @NotBlank(message = "도시를 입력하세요.")
    private String city;

    @NotBlank(message = "거리를 입력하세요.")
    private String street;

    @NotBlank(message = "우편번호를 입력하세요.")
    private String zipcode;

    @Builder
    @QueryProjection
    public MemberDto(String name,
                     String password,
                     String nickname,
                     String email,
                     String tel,
                     String city,
                     String street,
                     String zipcode) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.tel = tel;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public MemberDto(Member member) {
    }
}