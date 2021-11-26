package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


//@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


    /**
    * 회원가입
    */
    @Transactional //jpa 사용시 데이터를 저장하거나, 변경할때는 해당 @이 존재해야함
    public Long join(Member member){

        validateDuplicateMember(member); //중복회원검증
        memberRepository.save(member);
        return member.getId();
    }


    /**
     * 중복회원검증
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다!!");
                        });
    }

//        Optional<Member> result = memberRepository.findByName(member.getName());
//
//        //만약, 값이 있으면 동작 (기존에는 null이 아니면- 조건문처리)
//        //Null가능성 있는경우 Optional로 감싸서 반환
//        result.ifPresent( m -> {
//                    throw new IllegalAccessException("이미 존재하는 회원입니다.!!")
//                });



    /**
     * 전체회원조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    };


    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

};