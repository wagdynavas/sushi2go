package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public ModelAndView about(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.addObject("cartQuantity", SessionUtil.getCartQuantity(request));
        view.setViewName("contact/contact");
        return view;
    }
}
