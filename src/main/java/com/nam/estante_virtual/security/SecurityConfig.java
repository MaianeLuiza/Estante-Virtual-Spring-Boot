package com.nam.estante_virtual.security;

import com.nam.estante_virtual.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder generateEncryption() {
        BCryptPasswordEncoder encryption = new BCryptPasswordEncoder();
        return encryption;
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetailsService userDetailsService = new UserDetailService(userRepository);
        return userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/user/*").hasAnyAuthority("USER", "ADMIN", "BIBLIOTECARIO")
                .antMatchers("/auth/admin/*").hasAnyAuthority("ADMIN")
                .antMatchers("/auth/librarian/*").hasAnyAuthority("BIBLIOTECARIO")
                .antMatchers("/user/admin/*").hasAnyAuthority("ADMIN")
                .and()
                .exceptionHandling().accessDeniedPage("/auth-access-denied")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = userDetailsServiceBean();
        BCryptPasswordEncoder encryption = generateEncryption();

        auth.userDetailsService(userDetailsService).passwordEncoder(encryption);
    }

}
