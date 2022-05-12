package spring.basic.member.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring.basic.member.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcMemberRepository implements MemberRepositoryInterface{

    private DataSource dataSource;
    public int index = 0;

    @Autowired
    public JdbcMemberRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void saveMember(Member m){
        //String sql = "INSERT INTO member(id, name) values(?,?)";
        String sql = "INSERT INTO member(name) VALUES(?)";  //identity

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);    //identity

            //m.setId(index++);

            //pstmt.setInt(1,m.getId());
            pstmt.setString(1,m.getName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(conn);
    }
    @Override
    public Member findById(int id){
        String sql = "SELECT * FROM member WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));

                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;

    }

    @Override
    public List<Member> findAll() {
        List<Member> memberList = new ArrayList<>();
        //SELECT * FROM memeber 결과를 담은 리스트
        String sql = "SELECT * FROM member";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while(rs.next()){
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));

                memberList.add(m);
            }
            return memberList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(conn);
        }
        return null;

    }


    private void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
