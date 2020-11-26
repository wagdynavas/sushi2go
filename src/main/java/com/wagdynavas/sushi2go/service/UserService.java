package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

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

    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public ModelAndView createUser(User user, BindingResult result) {
        ModelAndView view = new ModelAndView();
        User userExist = getByUsername(user.getUsername());
        if(userExist != null) {
            result.rejectValue("username", "", "This username has already been used.");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("password", "", "Password does't match!");
        }
        if(result.hasErrors()) {
            view.setViewName("user/registration");
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