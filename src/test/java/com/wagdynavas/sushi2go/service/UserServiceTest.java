package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    //You can use @Mock here that have the sane effect
    private UserRepository userRepository =  Mockito.mock(UserRepository.class);

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    private UserService  userService;

    @BeforeEach
    void before () {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void createdUserHasCreationDate() {
        User user = new User();
        user.setUsername("Claire");
        user.setEmail("clairenavas@gmail.com");
        user.setPassword(passwordEncoder.encode("test"));
        user.setRole("ADMIN");

        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");


        when(userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        User userSaved = userService.createUser(user);

        assertNotNull(userSaved.getCreationDate());

    }
}
