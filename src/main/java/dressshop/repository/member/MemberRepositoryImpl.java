package dressshop.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.member.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dressshop.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Member> findList() {
        return query.selectFrom(member)
                .orderBy(member.id.desc())
                .offset(0)
                .limit(20)
                .fetch();
    }
}
