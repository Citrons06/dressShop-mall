package dressshop.service;

import dressshop.config.auth.PrincipalDetails;
import dressshop.domain.member.Member;
import dressshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            return User.builder()
                    .username(member.getEmail())
                    .password(member.getPassword())
                    .authorities(member.getMemberAuth().toString())
                    .build();

        } else {
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
    }
}
