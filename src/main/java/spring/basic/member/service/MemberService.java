package spring.basic.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.basic.member.domain.Member;
import spring.basic.member.repository.MemberRepositoryInterface;

import java.util.List;


public class MemberService {

    private MemberRepositoryInterface repository;

    @Autowired
    public MemberService(MemberRepositoryInterface repository){
        this.repository = repository;
    }
    public void join(Member m){
        repository.saveMember(m);
    }
    public Member findMemberById(int id) {
        return repository.findById(id);
    }

    public List<Member> findAllMember(){
        return repository.findAll();
    }

}
