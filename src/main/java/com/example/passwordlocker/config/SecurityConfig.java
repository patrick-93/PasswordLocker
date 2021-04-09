package com.example.passwordlocker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }

    @Autowired
    private UserDetailsService appUserDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Whitelist the static content and main page
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/img/**", "/create-first-user")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }



//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
//        return new AppUserDetailsService();
//    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//
//        return authenticationProvider;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(appUserDetailsService);
    }

    /*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
     */
}
