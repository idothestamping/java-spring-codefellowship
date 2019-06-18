package com.idothestamping.lab.codefellowship;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodefellowshipController {
    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }
}


