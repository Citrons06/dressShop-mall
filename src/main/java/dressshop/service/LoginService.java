package dressshop.service;

import dressshop.config.auth.PrincipalDetails;
import dressshop.domain.member.Member;
import dressshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
        } else {
            log.info("로그인을 성공하였습니다.={}", member.getEmail());

            return User.builder()
                    .username(member.getEmail())
                    .password(member.getPassword())
                    .roles(member.getMemberAuth().toString())
                    .build();
        }
    }
}
