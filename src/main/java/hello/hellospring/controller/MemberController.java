package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    // DI의 주입방법 3가지(생성자, 필드, setter)
    // => 의존관계가 실행중에 동적으로 변하는경우 거의없으므로 1)생성자주입을 권장함
    // 1) 생성자주입(★★가장좋은방법)
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;

    }
/*
    // 2) 필드주입 : 중간에 바꿀수없는 방법이 없다는 단점이 존재
    @Autowired
    private final MemberService memberService;
*/

/*
    // 3) setter주입 : 생성,setter 둘다생김, 중간에 setter를 통해 바꿔지게되면 문제가생김(최초세팅후 변경X것이 좋음)
    @Autowired
    public void setMemberService(MemberService memberService){
        this.memberService = memberService;
        memberService.setMemberSevice(); //외부변경가능성존재(호출하지말아야될 메소드는 되도록호출X)
    }
*/
    /**
     * 회원가입
     */
//   http의 get(조회)/post(등록,변경)방식
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    /**
     * 전체 회원조회
     */
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "/members/memberList";
    }

};


    /*
    <정리> - 내가 이해한 내용
    컨트롤러라는 @Controller
    어노테이션이 있으면
    스프링 컨테이너에서 해당클래스를 객체를 생성,관리 하게됨(객체들간의 의존관계)
    즉, 스프링컨테이너는 어노테이션이 붙은 객체들을 자동으로 생성,관리해줌

    기존 자바에서는 new로 객체생성 경우, 개발자가 직접 객체를 관리하였음
    문제점 ) 같은 객체를 이용해야되는 서비스의 경우 공유X(회원-주문,배송), 여러개의 객체생성(그때마다 new로 객체를 생성해줄필요X)


    스프링은 개발자가아닌 컨테이너에게 위임한것
    @(어노테이션)을 등록하면 스프링 컨테이너에서 자동으로 객체를 생성, 의존관계를 관리해준다.
    스프링은 @로 딱 하나의 객체를 생성하기 때문에, 여러클래스에서 같은객체를 공용도 가능 (@이없는경우 객체를 찾을수없음)
    
    정리) 스프링은 빈을등록할때 하나만등록해서 공유(deault-싱글톤패턴) = 같은 스프링빈(객체)이면, 모두 같은 인스턴스를 반환함
    스프링 빈을등록하는 방법 2가지) 1. 컴포넌트스캔 2.자바에 직접 등록
    */
