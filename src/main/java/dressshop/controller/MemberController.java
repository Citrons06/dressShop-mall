package dressshop.controller;

import dressshop.domain.member.dto.MemberDto;
import dressshop.exception.customException.MemberJoinException;
import dressshop.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입 폼 불러오기
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberForm") MemberDto memberDto) {
        return "members/joinForm";
    }

    //회원 가입
    @PostMapping("/join")
    public String join(@Valid @RequestBody @ModelAttribute("memberForm") MemberDto memberDto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "members/joinForm";
        }

        try {
            memberService.join(memberDto);
        } catch (MemberJoinException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/joinForm";
        }

        return "redirect:/";
    }

    //회원 단건 조회
    @GetMapping("/members/{memberId}")
    public String findById(@PathVariable Long memberId, Model model) {
        MemberDto member = memberService.findMember(memberId);
        model.addAttribute("member", member);
        return "members/{memberId}";
    }

    //회원 전체 조회
    @GetMapping("/admin/members/list")
    public String findList(Model model) {
        List<MemberDto> members = memberService.findList();
        model.addAttribute("members", members);
        return "admin/members/memberList";
    }

    //회원 수정 폼 불러오기
    @GetMapping("/members/edit/{memberId}")
    public String editForm(Long memberId, Model model) {
        MemberDto member = memberService.findMember(memberId);
        model.addAttribute("editForm", member);
        return "members/editForm";
    }

    //회원 수정
    @PutMapping("/members/edit/{memberId}")
    public String editMember(@PathVariable Long memberId,
                             @ModelAttribute("editForm") @Valid MemberDto memberDto) {
        memberService.editMember(memberId, memberDto);
        return "redirect:/";
    }

    //회원 탈퇴
    @PostMapping("/members/{memberId}/delete")
    public String resign(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "redirect:/";
    }

    //USER 권한 정지
    @PostMapping("/admin/members/{memberId}/disable")
    public String disableMember(@PathVariable Long memberId) {
        memberService.disableMember(memberId);
        return "redirect:/";
    }

    //회원 삭제 폼 불러오기
    @GetMapping("/admin/members/{memberId}/delete")
    public String deleteForm(@PathVariable Long memberId, Model model) {
        MemberDto member = memberService.findMember(memberId);
        model.addAttribute("deleteForm", member);
        return "admin/members/deleteForm";
    }

    //회원 삭제
    @PostMapping("/admin/members/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "redirect:/admin/members/list";
    }
}