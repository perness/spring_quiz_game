package com.ncorp.service;

import com.ncorp.Application;
import com.ncorp.entity.MatchStats;
import com.ncorp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MatchStatsServiceTest extends ServiceTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private MatchStatsService matchStatsService;

    @Test
    public void testDefaultStats(){
        String userName = "name";
        String password = "password";

        User user = userService.createUser(userName, password);

        MatchStats matchStats = matchStatsService.getMatchStats(userName);

        assertEquals(0, (int) matchStats.getMatchesLost());
        assertEquals(0, (int) matchStats.getMatchesWon());
    }

    @Test
    public void testStats(){
        String userName = "name";
        String password = "password";

        userService.createUser(userName, password);

        matchStatsService.reportLoss(userName);
        matchStatsService.reportVictory(userName);
        matchStatsService.reportVictory(userName);

        MatchStats matchStats = matchStatsService.getMatchStats(userName);

        assertEquals(1, (int) matchStats.getMatchesLost());
        assertEquals(2, (int) matchStats.getMatchesWon());
    }

}
