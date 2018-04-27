package com.crud.tasks;

import com.crud.tasks.domain.AttachmentsByType;
import com.crud.tasks.domain.Badges;
import com.crud.tasks.domain.Trello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnotherTestSuite {

    @Test
    public void testObjectTrelloMethods() {
        //Given
        Trello trello = new Trello(123, 456);
        //When
        int boardResult = trello.getBoard();
        int cardResult = trello.getCard();
        //Then
        assertEquals(123, boardResult);
        assertEquals(456, cardResult);
    }
    @Test
    public void testObjectAttachmentsByTypeMethod() {
        //Given
        Trello trello = new Trello(123, 456);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        //When
        Trello result = attachmentsByType.getTrello();
        //Then
        assertEquals(trello, result);
    }
    @Test
    public void testObjectBadgesMethods() {
        //Given
        Trello trello = new Trello(123, 456);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        Badges badges = new Badges(111, attachmentsByType);
        //When
        AttachmentsByType result = badges.getAttachments();
        int votesResult = badges.getVotes();
        //Then
        assertEquals(111, votesResult);
        assertEquals(attachmentsByType, result);
    }
}
