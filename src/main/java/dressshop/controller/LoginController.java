package dressshop.controller;

import dressshop.domain.member.dto.MemberDto;
import dressshop.service.member.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        } catch (UsernameNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login/loginForm";
        }

        return "redirect:/";
    }
}
