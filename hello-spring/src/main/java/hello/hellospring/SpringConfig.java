package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    /*
    * MemberRepository는 인터페이스고
    * MemoryMemberRepository가 구현체이므로 리턴값에 넣는다.
    * 인터페이스는 new가 안되기 떄문에.
    * */
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
