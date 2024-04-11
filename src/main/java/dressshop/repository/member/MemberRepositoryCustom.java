package dressshop.repository.member;

import dressshop.domain.member.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findList();
}
