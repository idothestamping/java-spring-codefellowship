package com.idothestamping.lab.codefellowship;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller //annotation
public class CodefellowshipController {

//    @GetMapping("/getPrinciple")
//    public String getIndex(Principal p, Model m) {
//        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();
//        System.out.println(p.getName());
//        m.addAttribute("principal", currentUser);
//        return "index";
//    }

    @GetMapping("/users/{id}")
    public String getMyProfile(Principal p, Model m){
        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();
        m.addAttribute("principal",currentUser);
        return "index";
    }
}


