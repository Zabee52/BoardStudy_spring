package com.sparta.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "redirect:/board";
    }
    @GetMapping("/forbidden")
    public String forbidden(){
        return "redirect:/forbidden.html";
    }
}
