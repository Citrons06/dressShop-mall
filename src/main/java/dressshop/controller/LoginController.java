package dressshop.controller;

import dressshop.domain.member.dto.LoginFormDto;
import dressshop.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new MemberDto());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginFormDto loginFormDto) {
        log.info("loginFormDto={}", loginFormDto);
        return "redirect:/";
    }
}
