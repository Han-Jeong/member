package spring.basic.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basic.member.repository.JdbcMemberRepository;
import spring.basic.member.repository.JdbcTemplateMemberRepository;
import spring.basic.member.repository.MemberRepositoryInterface;
import spring.basic.member.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    /*
    controller는 이 페이지에 모으지 않습니다!
    왜냐하면 원래부터 스프링 관할
     */

    @Bean // (=@Service)
    public MemberService memberService(){
        return new MemberService(memberRepositoryInterface());
    }
    @Bean
    public MemberRepositoryInterface memberRepositoryInterface(){
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
