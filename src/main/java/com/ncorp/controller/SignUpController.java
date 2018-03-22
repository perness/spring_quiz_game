package com.ncorp.controller;

import com.ncorp.entity.User;
import com.ncorp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.annotation.SessionScope;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScope
public class SignUpController implements Serializable {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String userName, password;

    public String registerUser(){
        User user = userService.createUser(userName, password);

        if (user == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    password,
                    userDetails.getAuthorities());

            authenticationManager.authenticate(token);

            if (token.isAuthenticated()) SecurityContextHolder.getContext().setAuthentication(token);

            return "/index.jsf?faces-redirect=true";
        }
        else{
            return "/signup.jsf?faces-redirect=true&error=true";
        }

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
