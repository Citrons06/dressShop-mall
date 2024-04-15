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

import static dressshop.domain.member.MemberAuth.USER;

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
        String name = provider + "_" + providerId;

        String password = pwdEncoder.encode("패스워드");

        String email = oAuth2User.getAttribute("email");

        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            member = Member.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .provider(provider)
                    .providerId(providerId)
                    .memberAuth(USER)
                    .build();

            memberRepository.save(member);
            log.info("최초로 로그인하여 회원가입을 완료하였습니다.");
        } else {
            log.info("이미 가입된 회원입니다.");
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
