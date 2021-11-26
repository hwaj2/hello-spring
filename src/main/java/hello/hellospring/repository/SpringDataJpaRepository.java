package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JPQL의 개념에 대해서 더이해하고 깊이 생각해보기
// 인터페이스만 만들어놓으면 JpaRepository(스프링 data jpa제공) 인터페이스에 대한 구현체를 자동으로 생성및, 빈으로 등록해줌
// 구현클래스 없이 인터페이스 구현만으로 db연동 개발이 끝남 ..엥?진짜? 어떻게...? 너무신기해

public interface SpringDataJpaRepository extends JpaRepository<Member ,Long>, MemberRepository {

    // JPQL - select m from member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
