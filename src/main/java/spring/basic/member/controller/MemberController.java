package spring.basic.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.basic.member.domain.Member;
import spring.basic.member.service.MemberService;

import java.util.List;

@Controller
public class MemberController {


    MemberService service;

    @Autowired
    public MemberController(MemberService service){
        this.service = service;
    }

    @GetMapping("members/new")
    public String createMember(){
        return "members/createForm";
    }

    @GetMapping("members/find")
    public String findMember(){
        return "members/findForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm memberform){
        Member m = new Member();
        m.setName(memberform.getName());

        service.join(m);

        return "redirect:/";        //제일 첫 페이지로 돌아감
    }

    @PostMapping("members/find")
    public String find(@RequestParam("id")int id, Model model) {
        Member m = service.findMemberById(id);

        //찾은 객체를 통째로 다음 페이지로 넘겨요
        model.addAttribute("member",m);
        return "members/findMember";
    }

    @GetMapping("members") // localhost:8080/members
    public String memberList(Model model){
        List<Member> members = service.findAllMember();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
