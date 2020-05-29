package com.wagdynavas.sushi2go.controllers;


import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ModelAndView create(User user, BindingResult result) {
        return userService.createUser(user, result);
    }

    @GetMapping("/${username}}")
    public ModelAndView listUsersByUsername(@PathVariable  String username) {
        //return userService.getusersByUsername(user);

        return null;
    }

}
