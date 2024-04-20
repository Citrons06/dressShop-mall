package dressshop.controller;

import dressshop.domain.member.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@ModelAttribute("member") MemberDto memberDto) {
        return "home";
    }
}
