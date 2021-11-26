package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

// 단위테스트
class MemberServiceTest {

/*
    MemberService클래스의 memberRepository와  해당클래스의 memberRepository 서로다른객체(애매)
    서로다른 객체= 즉, 다른 인스턴스이기 때문에 문제가생길수있다.
    해결방법) 생성자를 이용해 외부에서 넣어주는방법 (about DI의 개념)
             => 생성자를 이용: MemberRepository를 외부에서 넣어주는 방법을 이용해 같은 객체를 공유할수있도록

    MemberService memberService = new MemberService();
    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); /
*/


    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); //Dependency Injection(의존성주입)관련...
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore(); // clear하기위해 가져옴(MemoryMemberRepository)
    }


    @Test
    void 회원가입() {

        // # 테스트는 정상플로우보다 예외플로우가 더 중요하다
        // given> when> then

        //given(조건이 주어졌을때)
        Member member = new Member();
        member.setName("spring");

        //when(이를 실행하면)
        Long saveId = memberService.join(member);

        //then(결과가 이렇게 나와야해)
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }


    @Test
    public void 중복_회원_예외(){  //예외 발생 시험(일부러 발생시켜 확인)

        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");


        //when
        memberService.join(member1);

        /*  기존 try catch 구문
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail(); //실패시켜 예외발생시킴
        }catch (IllegalStateException e){
            //assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다!!");
        }
        */

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, ()-> memberService.join(member2));
                                                //뒤에있는 로직을 태울때 앞에있는 예외가 터져야한다.
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다!!");

    }


    @Test
    void findMembers() {

    }

    @Test
    void findOne() {
    }
}