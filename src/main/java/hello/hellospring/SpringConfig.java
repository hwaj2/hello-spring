package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//  2번째 방법 :  자바코드로 직접 스프링 빈 등록(config파일)
//  과거에는 xml파일을 이용해 빈등록을 했지만, 지금은 거의 사용하지X
@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
/*
   //DataSource dataSource;
    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
      this.em = em;
    }
*/

/*    @Bean
    public TimeTraceApp timeTraceApp(){
        return  new TimeTraceApp();
    }*/

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

/*
    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
    }
*/

/*
    @Bean을 이용해 직접등록하게될 경우, 다른 repository로 변경시 기존소스를 전혀 수정하지않고, 개발가능!
    정형화되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다
    => 객체지향의 다형성 (소스변경없이 구현체를 변경)
    정리 ) 스프링의 DI를 사용하면 기존코드를 전혀 손대지않고, 설정만으로 구현클래스를 변경할수있다.
*/

};