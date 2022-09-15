package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // save시 저장하기 위한 구현체, 실무에서는 ConcurrentHashMap을 쓴다.
    private static Map<Long, Member> store = new HashMap<>();
    // 0,1,2... 키값을 생성. 실무에서는 어텀롱? 을 쓴다고 한다(동시성 문제).
    private static long sequence = 0L;

    /*
     * setter로 시퀀스값 세팅해줌
     * store맵에 put으로 id값 넣어준다.
     * */
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;

    }

    /*
     * store맵에서 get메소드로 id값을 매개변수로 한 값 출력.
     * Optional.ofNullable로 감싸서 반환하면 클라이언트측에서 액션을 취할 수 있음. 나중에 설명 예정
     * */
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /*
     * java8 람다식 사용.
     * values().stream() -> map.values(): map의 value값들을 collection 형태로 리턴
     * 그 콜렉션 값들을 stream()을 통해  loop로 돌리는데,
     * filter()를 통해 조건을 건다(필터링) :
     *  member -> member.getName().equals(name) 이 부분에서 화살표 왼쪽은 메서드 파라미터,
     *  오른쪽은 메서드의 구현부이다.
     *  즉, 반복 작업의 요소(여기서는 Member 객체)를 member라고 명명하고 ->
     *  자동으로 member객체로 인식하는 이유: 위 HashMap 선언 시 Value값이 Member객체였기 떄문!!
     *  그말인즉슨, store.values() = Member 객체이기 떄문에 자동으로 Member객체로 인식함.
     *  filter 메서드의 파라미터로 넣어줌.
     *  loop를 돌면서 getName()하고, 그 값을 equals(name) 메서드를 통해 비교한 뒤 같은 값이 있으면 반환
     *  findAny()는 값이 하나라도 있으면 반환하고 만약 값이 없다면 Optional에 Null이 포함되서 반환
     * */
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(mem -> mem.getName().equals(name))
                .findAny();
    }

    /*
    * 모든 멤버를 찾는 메소드이기 때문에 List로 담아서 반환하는데, 그 값은 store의 values들을 다 반환.
    * */
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
