package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    /**
     * 테스트 실행 시 새롭게 생성해준다(테스트는 독립적으로 실행되어야 하기 때문에)
     * 테스트 실행 전에 MemoryMemberRepository를 만들어서 memberRepository에 담아넣고,
     * 그 값을 memberService에 담아준다.
     * 그렇게 되면 같은 memoryRepository를 사용하게 되는 것이다.
     * memberService 입장에서 보면, 내가 직접 new를 선언하지 않고 외부에서 파라미터를 통해memberRepository를 넣어준다.
     * 이러한 것을 DI라고 한다.(Dependency Injection)
    * */
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    /**
    * 테스트 케이스는 실제 운영에 사용되는 코드가 아니므로 한글로 써도 돌아감
    * 실제 코드 빌드 시에 테스트클래스는 포함되지 않음
    * 상황에 따라 무조건 저 세 조건이 안맞을 수도 있지만 회원가입 케이스 안의
    * 저 주석을 깔고 하면 편하다.
    * */
    @Test
    void 회원가입() {

        /**
        * given : 어떠한 상황이 주어져서(데이터)
        * */
        Member member = new Member();
        member.setName("spring");

        /**
        * when : 이걸 실행했을 때(검증)
        *
        * */
        Long saveId = memberService.join(member);

        /**
        * then : 어떠한 결과가 나와야 한다.
        * 여기서는 위 given에서 저장한 name이, 실제 리포지토리를 통해 저장한 값과
        * 동일한지 알아보기 위해 assertThat 메소드를 통해 비교하고 있다.
        * 물론 처음에는 Assertions가 붙었지만, 지금 static으로 바꿨기 떄문에 생략해서 사용하는중.
        * 결과는 당연히 통과
        * */
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }


    /**
     * 테스트 케이스는 사실 성공보다 예외 케이스가 더 중요하기 때문에,
     * 예외 케이스를 만들어 본다.
     * */
    @Test
    public void 중복_회원_예외() {
        /*
         * given
         * */
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        /*
         * when
         * member1과 member2가 같은 값이므로, try - catch를 통해 예외 처리를 해준다.
         *
         * */
        memberService.join(member1);
        /*
        * assertThrows 메소드는 첫번째 인자로 발생할 예외 클래스의 클래스 타입을 받는다.
        * 그 후, 람다식을 사용하여 어떤 로직을 태울 때 동일한 exception 타입인지 체크한다.
        * */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        /*try {
            memberService.join(member2);
            // 만약 위 코드가 실행되고, catch문을 안타면 실패이기 때문에 fail()을 넣어준다.
            fail();
        } catch (IllegalStateException e) {
            *//*
            * catch부를 타면 정상적으로 성공한 것
            * assertThat안의 메세지가 validateDuplicateMember메소드의 메세지와 같으면 성공,
            * 왜냐면 e.getMessage()부는 실제 service코드에서의 IllegalStateException의 메세지를
            * 들고오는 것이기 때문이다.
            * 다르면 실패.
            * *//*
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.1");
        }*/

        /*
        * then
        * */

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}