package com.wagdynavas.sushi2go.controllers;


import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/login")
    public String sighIn() {
        return "account/sign-in";
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("account/register");

        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView create(@Valid User user, BindingResult result) {
        return userService.createUser(user, result);
    }

   /* @GetMapping("/${username}}")
    public ModelAndView listUsersByUsername(@PathVariable  String username) {
        //return userService.getusersByUsername(user);

        return null;
    }*/

}
