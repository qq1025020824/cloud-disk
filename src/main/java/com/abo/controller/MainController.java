package com.abo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController extends BaseController{

    @RequestMapping(value = {"/","/{test}"})
    public String main(){
    	if(this.getUserID()!=null){
    		return "redirect:/file/view?folder=home";
    	}else{
    		return "redirect:/login";
    	}
    }
}

