package com.idothestamping.lab.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;	import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;	import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;	import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;


import java.security.Principal;
import java.sql.Timestamp;


@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/post")
    public String getPost(Principal p, Model m) {
        System.out.println(p.getName());
        AppUser me = appUserRepository.findByUsername(p.getName());
        m.addAttribute("loggedInUser", me);
        return "post";
    }

    @GetMapping("/post/add")
    public String getPostCreator() {
        return "createPost";
    }

    @PostMapping("/post")
    public RedirectView addPost(String body, Principal p) {
        Post newPost = new Post();
        newPost.body = body;
        newPost.ts = new Timestamp(System.currentTimeMillis());
        AppUser me = appUserRepository.findByUsername(p.getName());
        newPost.creator = me;
        postRepository.save(newPost);
        return new RedirectView("/post");
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable long id, Model m, Principal p) {
        Post post = postRepository.findById(id).get();
        // check if that post belongs to the currently logged in user
        if (post.getCreator().username.equals(p.getName())) {
            m.addAttribute("post", post);
            return "post";
        } else {
            throw new PostDoesNotBelongToYouException("That post does not belong to you.");
        }
        // if so, do the nice things
        // otherwise, tell them no
    }


    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    class PostDoesNotBelongToYouException extends RuntimeException {
        public PostDoesNotBelongToYouException(String s) {
            super(s);
        }
    }
}
