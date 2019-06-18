package com.idothestamping.lab.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/signup")
    public String getSignupPage(Model m) {
        m.addAttribute("newuser",new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String getSignUp(@ModelAttribute AppUser newuser){
        AppUser res = new AppUser(newuser.username,passwordEncoder.encode(newuser.password),newuser.firstname,newuser.lastname,newuser.dob,newuser.bio);
        appUserRepository.save(res);

        AppUser user = appUserRepository.findByUsername(res.username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(res, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        long id = user.id;
        return "redirect:/users/"+id;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}