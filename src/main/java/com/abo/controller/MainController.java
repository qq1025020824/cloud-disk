package com.abo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Huangbin on 2017/3/28.
 */
@Controller
public class MainController {

    @RequestMapping(value = {"/","/index.jsp"})
    public String main(){
        return "index";
    }
}

