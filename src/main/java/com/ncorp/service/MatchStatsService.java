package com.ncorp.service;

import com.ncorp.entity.MatchStats;
import com.ncorp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class MatchStatsService {

    @Autowired
    private EntityManager entityManager;


    public void reportVictory(String userName){
        MatchStats match = getMatchStats(userName);
        match.setMatchesWon(match.getMatchesWon() + 1);
    }

    public void reportLoss(String userName){
        MatchStats match = getMatchStats(userName);
        match.setMatchesLost(match.getMatchesLost() + 1);
    }

    public MatchStats getMatchStats(String username) {

        TypedQuery<MatchStats> query = entityManager.createQuery(
                "select m from MatchStats m where m.user.userName=?1", MatchStats.class);
        query.setParameter(1, username);

        List<MatchStats> results = query.getResultList();
        if(!results.isEmpty()){
            return results.get(0);
        }

        User user = entityManager.find(User.class, username);
        if(user == null) throw new IllegalArgumentException("No existing user: " + username);

        MatchStats match = new MatchStats();
        match.setUser(user);
        entityManager.persist(match);

        return match;
    }
}
