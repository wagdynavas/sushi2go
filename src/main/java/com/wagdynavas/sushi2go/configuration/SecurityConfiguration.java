package com.wagdynavas.sushi2go.configuration;

import com.wagdynavas.sushi2go.exceptions.SimpleFailureHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${spring.queries.user-query}")
    private String usernameQuery;

    @Value("${spring.queries.roles-query}")
    private String roleQuery;

    private final DataSource dataSource;

    private final BCryptPasswordEncoder passwordEncoder;

    private final SimpleFailureHandler simpleFailureHandler;

    private static final String LOGIN = "/login";

    private static final String[] noAuthorizationNeeded  = {
            "/", "/about", "/products/list", "/contact", "/products/add-to-order", "/media/**", "/checkout/**",
            "/delete/**", "/create-checkout-session", "/change-tip/**", "/checkout/succeeded", "/checkout/canceled",
            "/orders/fulfill", "/home"
    };

    private static final String[] staticResources  =  {
            "/css/**",
            "/img/**",
            "/js/**",
            "/fonts/**",
            "/media/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(DataSource dataSource, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jdbcUserDetailsService(dataSource)); // Custom user details service
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(usernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(roleQuery);
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         return httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers(noAuthorizationNeeded).permitAll()
                 .requestMatchers(LOGIN).permitAll()
                 .requestMatchers("/registration").hasAuthority("ROLE_ADMIN")
                 .requestMatchers(staticResources).permitAll()
                 .anyRequest()
                 .authenticated()).formLogin(login -> login
                         .loginPage(LOGIN)
                         .failureUrl("/login?error=true")
                         .failureHandler(simpleFailureHandler)
                         .defaultSuccessUrl("/", true)
                         .usernameParameter("username")
                         .passwordParameter("password")
                 )
                 .logout(logout -> logout
                         .logoutUrl("/logout")  // Replaces AntPathRequestMatcher
                         .logoutSuccessUrl(LOGIN)
                 )
                 .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()  {
        return (web) -> web.ignoring().requestMatchers("/webjars/**", "/webjars/**");
    }
}
