package dressshop.service;

import dressshop.domain.member.Member;
import dressshop.domain.member.dto.MemberDto;
import dressshop.exception.customException.MemberJoinException;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder pwdEncoder;

    //회원 가입
    public void join(MemberDto memberDto) {
        Member member = memberDto.toEntity();
        member.passwordEncode(pwdEncoder.encode(member.getPassword()));
        validateDuplicateMember(member);

        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member emailMember = memberRepository.findByEmail(member.getEmail());
        Member nicknameMember = memberRepository.findByNickname(member.getNickname());
        if (emailMember != null) {
            throw new MemberJoinException("이미 가입된 이메일입니다.");
        }
        if (nicknameMember != null) {
            throw new MemberJoinException("중복된 닉네임입니다.");
        }
    }

    //회원 수정
    public void editMember(Long memberId, MemberDto memberDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);

        MemberDto memberEdit = member.toEditor()
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
                .email(memberDto.getEmail())
                .tel(memberDto.getTel())
                .city(memberDto.getCity())
                .street(memberDto.getStreet())
                .zipcode(memberDto.getZipcode())
                .build();

        member.editMember(memberEdit);
    }

    //회원 단건 조회
    @Transactional(readOnly = true)
    public MemberDto findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);

        return MemberDto.builder()
                .name(member.getName())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .tel(member.getTel())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }

    //회원 리스트 조회
    @Transactional(readOnly = true)
    public List<MemberDto> findList() {
        return memberRepository.findList().stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }

    //회원 삭제
    public void delete(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);

        memberRepository.delete(findMember);
    }
}