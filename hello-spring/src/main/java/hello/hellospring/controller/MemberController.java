package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 스프링 프로젝트 빌드 시 스프링 컨테이너라는 통이 생성된다.
 * 그 떄, 클래스에 @Controller라는 어노테이션이 붙어있으면
 * 해당 컨트롤러 객체를 생성하여 스프링이 관리한다.
 * 이럻게 관리되는 것을 ['스프링 빈'이 관리된다] 라고 하는 것이다.
 * */
@Controller
public class MemberController {

    /*
    * 하나만 생성해놓고 공용으로 쓰면 된다. 이렇게 하는 이유는 나중에 따로 설명
    * final로 MemberService를 선언하고,
    * */
    private MemberService memberService;


    /*
    * MemberService 생성자를 만든다.
    * 그 후, @Autowired 어노테이션을 붙여줌.
    * @Autowired는 매개변수로 들어온 MemberService를 가져다가 연결해준다.
    * 하지만, 현재 MemberService 클래스에는 @Service 어노테이션이 없기 때문에
    * @Autowired 어노테이션을 통해 서비스를 연결시켜줄 수 없다.
    * */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
