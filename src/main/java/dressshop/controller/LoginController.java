package dressshop.controller;

import dressshop.config.auth.PrincipalDetails;
import dressshop.domain.member.Member;
import dressshop.domain.member.dto.MemberDto;
import dressshop.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/loginForm")
    public String loginForm(@ModelAttribute("memberForm") MemberDto memberDto) {
        return "login/loginForm";
    }

    @PostMapping("/loginForm")
    public String login(@Valid @ModelAttribute("memberForm") MemberDto memberDto,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        try {
            loginService.loadUserByUsername(memberDto.getEmail());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login/loginForm";
        }
        return "redirect:/";
    }

    @GetMapping("/loginInfo")
    @ResponseBody
    public String loginInfo(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        if (principal.getName() == null) {
            log.info("form 로그인={}", principal);
        } else {
            log.info("oauth 로그인={}", principal);
        }
        return "로그인 완료";
    }
}
