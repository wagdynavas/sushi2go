package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("cartQuantity")
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Integer cartQuantity = SessionUtil.getCartQuantity(request);
        mv.addObject("cartQuantity", cartQuantity);
        mv.setViewName("home/home");
        return mv;
    }
}
