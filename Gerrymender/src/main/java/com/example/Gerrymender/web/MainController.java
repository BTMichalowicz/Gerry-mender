package com.example.Gerrymender.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String welcome(){
        return "Homepage";
    }

    @RequestMapping("/Test")
    public String FLstate(){
        return "index";
    }

}
