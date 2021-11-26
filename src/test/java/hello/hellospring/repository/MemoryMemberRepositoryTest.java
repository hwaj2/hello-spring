package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//테스트 케이스 작성(MemoryMemberRepository)
public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트가 끝날때마다 메모리를 깔끔하게지워주는 method생성
    @AfterEach //메소드 끝날때마다 실행되는 어노테이션
    public void afterEach(){
        repository.clearStore();
    }


    @Test
    public void save(){

        Member member = new Member();
        member.setName("dolldori");
        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // optional에서 값을 꺼낼떄는 get()

        System.out.println("result confirm = " + (result==member));
        //assert(jupiter)
        // org.junit.jupiter.api.Assertions.assertEquals(member, null);
        //assert(assert)
        assertThat(member).isEqualTo(null); //편하게 사용하기위해 static으로 올림
    }

    @Test
    public void findByName(){

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(member1).isEqualTo(result);

    }


    @Test
    public void findAll(){

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);


    }

    //  테스트코드 진짜중요!!(없이 개발하게되면 문제발생 多)
    //  모든 테스트의 순서는 보장X(순서의존X) > 그래서 따로 동작하도록해야함
    //  해결방법 : @AfterEach (메소드 끝날때마다 clear)
    //  # TDD(테스트주도개발) : 테스트후 개발하는 방식
    //  테스트를 위해서 제공하는 객체 : assertions
};