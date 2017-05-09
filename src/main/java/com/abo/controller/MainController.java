package com.abo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = {"/","/{test}"})
    public String main(){
    	if(this.getUserID()!=null){
    		return "redirect:/file/view?folder=home";
    	}else{
    		return "redirect:/login";
    	}
    }
}

