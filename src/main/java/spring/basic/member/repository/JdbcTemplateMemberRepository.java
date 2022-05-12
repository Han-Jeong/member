package spring.basic.member.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import spring.basic.member.domain.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateMemberRepository implements MemberRepositoryInterface {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /*
    SQL : String sql = "INSERT INTO member(name) VALUES(?)
    PS <- SQL, id값 자동 생성 요청
    name 값을 넣어주기
    connection <- ps
    ps 실행
     */

    @Override
    public void saveMember(Member m) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
       //String : 열의 이름
        //Object : 그 자리에 들어갈 값
        parameters.put("name",m.getName());

        jdbcInsert.execute(parameters);
    }

    @Override
    public Member findById(int id) {
        List<Member> result = jdbcTemplate.query("SELECT * FROm member WHERE id = ?",memberRowMapper(),id);
        if(result.stream().findFirst().isPresent()){
            return result.stream().findFirst().get();
        }
        return null;
    }

    @Override
    public List<Member> findAll() {

        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    //select 했을 때 사용할 결과 행들(resultset)
    private RowMapper<Member> memberRowMapper(){
        // RowMapper를 반환해주는 메소드
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                /*
                resultset(select 결과물)을 member객체에 저장
                여러 행이 반환되도, JdbcTemplate이 rowNum만큼 돌려서 씀
                그래서 우리는 한행만 넣는 척해도 상관 없음
                 */
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                return m;
            }
        };
    }
}
