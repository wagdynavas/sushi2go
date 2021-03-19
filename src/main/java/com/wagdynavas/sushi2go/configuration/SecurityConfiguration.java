package com.wagdynavas.sushi2go.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.queries.user-query}")
    private String usernameQuery;

    @Value("${spring.queries.roles-query}")
    private String roleQuery;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String[] noAuthorizationNeeded  = {
            "/", "/about", "/products/list", "/contact", "/products/add-to-order", "/media/**", "/checkout/**",
            "/delete/**", "/create-checkout-session", "/change-tip/**", "/checkout/succeeded", "/checkout/canceled",
            "/orders/fulfill"
    };

    private static final String[] staticResources  =  {
            "/css/**",
            "/img/**",
            "/js/**",
            "/fonts/**",
            "/media/**"
    };

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usernameQuery)
                .authoritiesByUsernameQuery(roleQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
         httpSecurity.authorizeRequests()
                 .antMatchers(noAuthorizationNeeded).permitAll()
                 .antMatchers("/login").permitAll()
                 .antMatchers("/registration").hasAuthority("ROLE_ADMIN")
                 .antMatchers(staticResources).permitAll()
                 .anyRequest()
                    .authenticated()
                        .and().csrf().disable()
                    .formLogin()
                        .loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/")
                        .usernameParameter("username").passwordParameter("password")
                 .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
    }

}
