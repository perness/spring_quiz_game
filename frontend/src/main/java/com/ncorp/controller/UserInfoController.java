package com.ncorp.controller;

import com.ncorp.entity.MatchStats;
import com.ncorp.service.MatchStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class UserInfoController implements Serializable {

    @Autowired
    private MatchStatsService matchStatsService;

    public String getUserName(){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public MatchStats getStats(){
        return matchStatsService.getMatchStats(getUserName());
    }

}

