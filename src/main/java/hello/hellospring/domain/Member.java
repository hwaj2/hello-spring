package hello.hellospring.domain;

import javax.persistence.*;

@Entity //RDBMS에서 Table을 객체화시킨것  https://jobc.tistory.com/120(참고)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY ) //DB가 알아서 값을 넣어주는 기능(IDENTITY)
    private Long id;

    // @Column("user_name")  컬럼명이다를경우 사용 @
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
};