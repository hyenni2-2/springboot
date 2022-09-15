package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

        /*
        * Test 어노테이션을 붙이고 옆 재생 버튼을 누르면 Test 케이스가 돌아감
        * */
        @Test
        public void save() {

            Member member = new Member();
            member.setName("spring");

            repository.save(member);

            /*
            * findById의 반환타입이 Optional일 때 값을 꺼내기 위해서는 get()을 사용.
            * get()으로 바로 꺼내는 건 좋은 방법이 아니지만 Test케이스이므로 사용.
            * */
            Member result = repository.findById(member.getId()).get();
            assertThat(member).isEqualTo(result);
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

            assertThat(result).isEqualTo(member1);
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
}
