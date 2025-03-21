package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    // @Autowired
    //private BCryptPasswordEncoder passwordEncoder;


    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public ModelAndView createUser(User user, BindingResult result) {
        ModelAndView view = new ModelAndView();
        String username = user.getUsername();
        User userExist = null;
        if(!username.isBlank()) {
            userExist = getByUsername(user.getUsername());
        }
        if(userExist != null) {
            result.rejectValue("username", "", "This username has already been used.");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("password", "", "Password doesn't match!");
        }
        if(result.hasErrors()) {
            view.setViewName("account/register");
            view.addObject(user);
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreationDate(LocalDate.now());
            userRepository.save(user);
            view.setViewName("redirect:/login");
        }
        return view;
    }

    public User createUser(User user) {
        if (user != null) user.setCreationDate(LocalDate.now());
        return userRepository.save(user);
    }
}