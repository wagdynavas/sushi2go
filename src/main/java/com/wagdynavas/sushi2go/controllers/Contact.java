package com.wagdynavas.sushi2go.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Contact {

    @GetMapping("/contact")
    public ModelAndView about() {
        ModelAndView view = new ModelAndView();
        view.setViewName("contact/contact");
        return view;
    }
}
