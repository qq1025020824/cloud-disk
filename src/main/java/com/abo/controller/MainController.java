package com.abo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = {"/","/index.jsp"})
    public String main(){
        return "index";
    }
}

