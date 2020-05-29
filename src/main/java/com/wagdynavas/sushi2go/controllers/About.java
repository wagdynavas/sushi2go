package com.wagdynavas.sushi2go.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class About {

    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView view = new ModelAndView();
        view.setViewName("about");
        return view;
    }
}
