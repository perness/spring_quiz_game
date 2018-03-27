package com.ncorp.service;

import com.ncorp.StubApplication;
import com.ncorp.entity.MatchStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MatchStatsServiceTest extends ServiceTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private MatchStatsService matchStatsService;

    @Test
    public void testDefaultStats(){
        String userName = "name";
        String password = "password";

        assertTrue(userService.createUser(userName, password));

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
