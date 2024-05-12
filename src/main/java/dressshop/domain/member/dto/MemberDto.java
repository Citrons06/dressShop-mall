package dressshop.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static dressshop.domain.member.MemberAuth.ROLE_USER;

@Data
@ToString
public class MemberDto {

    private Long id;

    @NotBlank(message = "이름을 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "잘못된 형식입니다.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String tel;

    private Address address;

    @Builder
    @QueryProjection
    public MemberDto(Long id,
                     String name,
                     String password,
                     String email,
                     String tel,
                     Address address) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.address = address;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .tel(tel)
                .memberAuth(ROLE_USER)
                .build();
    }
}