package dressshop.service.member;

import dressshop.domain.member.dto.MemberDto;

import java.util.List;

public interface MemberService {

    void join(MemberDto memberDto);

    void editMember(Long memberId, MemberDto memberDto);

    MemberDto findByEmail(String email);
    MemberDto findById(Long memberId);

    List<MemberDto> findList();

    void delete(Long memberId);
    void disableMember(Long memberId);
}
