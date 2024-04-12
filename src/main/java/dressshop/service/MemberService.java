package dressshop.service;

import dressshop.domain.member.Member;
import dressshop.domain.member.dto.MemberDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    public final MemberRepository memberRepository;

    //회원 가입
    public void join(MemberDto memberDto) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
                .email(memberDto.getEmail())
                .address(memberDto.getAddress())
                .tel(memberDto.getTel())
                .build();

        memberRepository.save(member);
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
                .address(memberDto.getAddress())
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
                .address(member.getAddress())
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