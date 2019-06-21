package com.idothestamping.lab.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false, defaultValue = "") String showMessage, Model m) {
        m.addAttribute("shouldShowExtraMessage", !showMessage.equals(""));
        return "login";
    }

    //@PostMapping: ("/login" POST route is done by Spring magic on WebSecurityConfig  ".loginPage("/login")")

    @GetMapping("/profile") //  Auto login route from WebSecurityConfig: .defaultSuccessUrl("/profile",
    public String afterLogin(Principal p, Model m){
        // From Evan
        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();
        m.addAttribute("principal",currentUser);

        Iterable<AppUser> allUsers = appUserRepository.findAll();
        m.addAttribute("allUsers",allUsers);
        return "profile";
    }

    @GetMapping("/allusers")
    public String showAllUsers(Model m){
        Iterable<AppUser> allUsers = appUserRepository.findAll();
        m.addAttribute("allUsers",allUsers);
        return "allUsers";
    }


    @GetMapping("/signup")
    public String getSignupPage(Model m) {
        m.addAttribute("newuser",new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String getSignUp(@ModelAttribute AppUser newuser){
        AppUser res = new AppUser(newuser.username,passwordEncoder.encode(newuser.password),newuser.firstName,newuser.lastName,newuser.dateofBirth,newuser.bio);
        appUserRepository.save(res);

        AppUser user = appUserRepository.findByUsername(res.username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(res, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        long id = user.id;
        return "redirect:/user/"+id;
    }

    @GetMapping("/user/{id}")
    public String getMyProfile(@PathVariable Principal p, Model m, Long id){
        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();
        m.addAttribute("principal",currentUser);

        AppUser friend = appUserRepository.findById(id).get();
        m.addAttribute("friend", friend);
        return "profile";
    }

    @PostMapping("/users/{id}")  // Get specific user data
    public String getFriendProfile(Model m, Long id){
        AppUser friend = appUserRepository.findById(id).get();
        m.addAttribute("friend", friend);
        return "profile";
    }

    @GetMapping("/friends/{id}")
    public String getMyProfile(@PathVariable Long id, Model m){
        AppUser friend = appUserRepository.findById(id).get();
        m.addAttribute("principal", friend);

        Iterable<AppUser> allUsers = appUserRepository.findAll();
        m.addAttribute("allUsers",allUsers);
        return "profile";
    }

    @PostMapping("/friends/{id}/friends")
    public RedirectView addFriend(@PathVariable Long id, Long friend, Principal p, Model m) {
        // we have the ID of two users
        // go get actual AppUser instances
        AppUser curfriend = appUserRepository.findById(id).get();
        AppUser newfriend = appUserRepository.findById(friend).get();
        // use the principal: check both of the dinosaurs belong to the currently logged in user
        // make them be friends
        curfriend.friends.add(newfriend);
        newfriend.friends.add(curfriend);
        // save! yes please omg
        appUserRepository.save(curfriend);
        appUserRepository.save(newfriend);
        // redirect back to the current dino
        return new RedirectView("/user/" + id);
    }

}