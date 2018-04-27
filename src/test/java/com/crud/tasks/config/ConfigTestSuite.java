package com.crud.tasks.config;

import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigTestSuite {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TrelloConfig trelloConfig;


    @Test
    public void testAdminConfig() {
        //Given
        //When
        String result = adminConfig.getAdminMail();
        //Then
        assertEquals("rafal.pijet@gmail.com", result);
    }
    @Test
    public void testTrelloConfig() {
        //Given
        //When
        //Then
        assertEquals("https://api.trello.com/1", trelloConfig.getTrelloApiEndPoint());
        assertEquals("addc8bd8f2f2b04162f1798bfbbd54bd", trelloConfig.getTrelloApiKey());
        assertEquals("6a594905cc1d968a9f2150aaae58c931d473cfa83341691e6556951de0ef84ec", trelloConfig.getTrelloToken());
        assertEquals("rafapijet", trelloConfig.getTrelloUsername());
    }
}
