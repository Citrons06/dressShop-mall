package dressshop.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static dressshop.domain.member.MemberAuth.USER;

@Getter @Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    private Long id;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Column(unique = true)
    private String nickname;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "잘못된 형식입니다.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String tel;

    @Builder
    @QueryProjection
    public MemberDto(Long id,
                     String name,
                     String password,
                     String nickname,
                     String email,
                     String tel) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.tel = tel;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .nickname(nickname)
                .email(email)
                .tel(tel)
                .memberAuth(USER)
                .build();
    }
}