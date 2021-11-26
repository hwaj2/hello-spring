package hello.hellospring.repository;

//스프링 JdbcTemplate : JPA방식과 함께 실무에서도 많이 쓰이는 방법

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// 스프링 JdbcTemplate : jdbcTemplate과 myBatis같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거시켜줌(단, SQL은 직접생성)
public class JdbcTemplateMemberRepository implements  MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    //@Autowired - * 생성자가 클래스에 1개이면 해당어노테이션 생략가능!
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());

        return member;
    }


    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result
                 = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }



    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result
                = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }



    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }


    private RowMapper<Member> memberRowMapper(){

        //lamda식으로 변경
        return(rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };

/*
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;

            }
        };
*/

    }

};