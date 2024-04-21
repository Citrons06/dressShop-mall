package dressshop.config.auth;

import dressshop.domain.member.Member;
import dressshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static dressshop.domain.member.MemberAuth.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder pwdEncoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = oAuth2User.getAttribute("email");

        String password = pwdEncoder.encode("패스워드");

        String email = oAuth2User.getAttribute("email");

        Member member = memberRepository.findByEmail(username);
        if (member == null) {
            member = Member.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .provider(provider)
                    .providerId(providerId)
                    .memberAuth(ROLE_USER)
                    .build();

            memberRepository.save(member);
            log.info("최초로 로그인하여 회원가입을 완료하였습니다.");
        } else {
            log.info("이미 가입된 회원입니다.");
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
