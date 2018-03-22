package com.ncorp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception{
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/", "/index.jsf", "/signup.jsf", "/assets/**").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .antMatchers("/ui/**").authenticated()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.jsf")
                    .permitAll()
                    .failureUrl("/login.jsf?error=true")
                    .defaultSuccessUrl("/index.jsf")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index.jsf");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT userName, password, enabled " +
                                    "FROM users " +
                                    "WHERE userName = ?"
                    )
                    .authoritiesByUsernameQuery(
                            "SELECT x.userName, y.roles " +
                                    "FROM users x, user_roles y " +
                                    "WHERE x.userName = ? and y.user_userName = x.userName "
                    )
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
