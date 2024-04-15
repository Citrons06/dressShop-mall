package dressshop.controller;

import dressshop.domain.member.Member;
import dressshop.domain.member.dto.MemberDto;
import dressshop.exception.customException.MemberJoinException;
import dressshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public String joinForm(Model model) {
        model.addAttribute("memberForm", new MemberDto());
        return "members/joinForm";
    }

    //회원 가입
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("memberForm") MemberDto memberDto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "members/joinForm";
        }

        try {
            memberService.join(memberDto);
        } catch (MemberJoinException e) {
            model.addAttribute("errorMessage", e.getMessage());
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
        model.addAttribute("member", member);
        return "members/editForm";
    }

    //회원 수정
    @PostMapping("/members/edit/{memberId}")
    public String editMember(@PathVariable Long memberId,
                             @ModelAttribute("member") @Valid MemberDto memberDto) {
        memberService.editMember(memberId, memberDto);
        return "redirect:/";
    }

    //회원 탈퇴
    @PostMapping("/members/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "redirect:/";
    }
}
