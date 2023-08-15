package smallshop.smallshop.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import smallshop.smallshop.domain.Address;
import smallshop.smallshop.domain.BookForm;
import smallshop.smallshop.domain.Member;
import smallshop.smallshop.domain.MemberForm;
import smallshop.smallshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 등록 페이지로 이동하는 요청 처리
    @GetMapping("/members/new")
    public String showCreateForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String showMemberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    // 회원 삭제 처리
    @PostMapping("/members/{memberid}/delete")
    public String deleteMember(@PathVariable Long memberid) {
        Member member = memberService.findMemberById(memberid);
        if (member != null) {
            // memberService에 회원 삭제를 수행하는 메서드를 추가해야 함
            memberService.deleteMember(member);
        }
        return "members/memberList";
    }

    // 회원 수정 페이지로 이동하는 요청 처리
    @GetMapping("/members/{memberid}/edit")
    public String showEditForm(@PathVariable Long memberid, Model model) {
        Member member = memberService.findMemberById(memberid);
        if (member == null) {
            // 회원이 존재하지 않으면 예외 처리 또는 오류 페이지로 이동할 수 있음
        }

        MemberForm memberForm = new MemberForm();
        memberForm.setName(member.getName());
        // 다른 필드도 필요하다면 해당 필드도 설정

        model.addAttribute("memberForm", memberForm);
        return "members/editMemberForm";
    }

    @PostMapping("/members/{memberid}/edit")
    public String editMember(@PathVariable Long memberid, @ModelAttribute("form") BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/updateMemberForm";
        }

        Member member = memberService.findMemberById(memberid);
        if (member == null) {
            // 회원이 존재하지 않으면 예외 처리 또는 오류 페이지로 이동할 수 있음
        }

        // 수정된 정보로 회원 정보 업데이트
        member.setName(form.getName());
        // 다른 필드도 필요하다면 해당 필드도 업데이트

        memberService.updateMember(member);
        return "redirect:/members";
    }


}