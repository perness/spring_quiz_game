package com.ncorp.controller;

import com.ncorp.entity.MatchStats;
import com.ncorp.service.MatchStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.annotation.SessionScope;

import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;

@Named
@SessionScope
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

