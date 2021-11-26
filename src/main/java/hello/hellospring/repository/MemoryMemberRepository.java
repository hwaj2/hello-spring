package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;


//@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence =0L; //키값생성을 위해 시퀀스부여(pk)


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }


    @Override
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(store.get(id)); // Null 반환될 가능성 있으면 Optional 사용함
    }


    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()   //해당 map에서 루프를 돌려 (Java8 Lamda식표현)
                .filter(member -> member.getName().equals(name)) //조건이 같은경우 필터링되고
                .findAny(); //하나라도 찾으면 반환됨
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
};