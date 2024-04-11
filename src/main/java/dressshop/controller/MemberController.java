package dressshop.controller;

import dressshop.domain.member.dto.MemberDto;
import dressshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입 폼 불러오기
    @GetMapping("/members/joinForm")
    public String joinForm(@ModelAttribute("member") MemberDto memberDto) {
        return "members/joinForm";
    }

    //회원 가입
    @PostMapping("/members/joinForm")
    public String join(MemberDto memberDto) {
        memberService.join(memberDto);
        return "redirect:/";
    }

    //회원 단건 조회
    @GetMapping("/members/{memberId}")
    public String findById(@PathVariable Long memberId) {
        memberService.findMember(memberId);
        return "members/{memberId}";
    }

    //회원 리스트 조회
    @GetMapping("/members/list")
    public String findList(@ModelAttribute("member") MemberDto memberDto) {
        memberService.findList();
        return "memberList";
    }

    //회원 수정
    @GetMapping("/members/edit/{memberId}")
    public String editMember(@PathVariable Long memberId, @Valid MemberDto memberDto) {
        memberService.editMember(memberId, memberDto);
        return "redirect:/members";
    }

    //회원 탈퇴
    @GetMapping("/members/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "redirect:/";
    }
}
