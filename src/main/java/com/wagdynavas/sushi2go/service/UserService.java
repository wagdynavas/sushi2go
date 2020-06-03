package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    public ModelAndView getUserById(Long userId) {
        ModelAndView view = new ModelAndView();
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            view.setViewName("user/registration");
            view.addObject("user", user);
        } else {
            view.setViewName("user/error");
        }

        return view;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ModelAndView createUser(User user, BindingResult result) {
        ModelAndView view = new ModelAndView();
        User userExist = getByEmail(user.getEmail());
        if(userExist != null) {
            result.rejectValue("email", "", "This email has already been used.");
        }
        if(result.hasErrors()) {
            view.setViewName("user/registration");
            view.addObject(user);
        }else {
            //user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            view.setViewName("redirect:/login");
        }
        return view;
    }
}