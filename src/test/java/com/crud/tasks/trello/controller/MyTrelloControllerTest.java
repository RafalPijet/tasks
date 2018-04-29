package com.crud.tasks.trello.controller;

import com.crud.tasks.controller.TrelloController;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MyTrelloControllerTest {

    @InjectMocks
    private TrelloController trelloController;
    @Mock
    private TrelloFacade trelloFacade;

    @Test
    public void testGetTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(new TrelloListDto("1", "my_list", false));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "my_task", trelloListDtoList));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtoList);
        //When
        List<TrelloBoardDto> result = trelloController.getTrelloBoards();
        //Then
        assertEquals(trelloBoardDtoList.size(), result.size());
        result.forEach(trelloBoardDto -> {
            assertEquals(trelloBoardDtoList.get(0).getId(), trelloBoardDto.getId());
            assertEquals(trelloBoardDtoList.get(0).getName(), trelloBoardDto.getName());
            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }
    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("New", "New Card", "top", "1212");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("123456789", "Test", "http://trele.morele");
        when(trelloFacade.createCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto result = trelloController.createTrelloCard(trelloCardDto);
        //Then
        assertEquals("123456789", result.getId() );
        assertEquals("Test", result.getName());
        assertEquals("http://trele.morele", result.getShortUrl());
    }


}
