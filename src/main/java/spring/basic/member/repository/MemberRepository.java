package spring.basic.member.repository;

import org.springframework.stereotype.Repository;
import spring.basic.member.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberRepository implements MemberRepositoryInterface {

    public static Map<Integer, Member> dbMap = new HashMap<>();
    public static int index = 0;
    @Override
    public void saveMember(Member m){
        m.setId(index++);
        dbMap.put(m.getId(), m);
    }

    @Override
    public Member findById(int id) {
        return dbMap.get(id);
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
