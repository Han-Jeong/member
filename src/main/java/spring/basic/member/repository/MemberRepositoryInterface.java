package spring.basic.member.repository;

import spring.basic.member.domain.Member;

import java.util.List;

public interface MemberRepositoryInterface {

    void saveMember(Member m);
    Member findById(int id);
    List<Member> findAll();
}
