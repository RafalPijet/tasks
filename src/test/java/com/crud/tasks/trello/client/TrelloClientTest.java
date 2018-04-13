package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;
    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("test");
    }
    @Test
    public void shouldFetchTrelloBoards() {
        //Given

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndPoint() + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }
    @Test
    public void shouldCreateCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test task", "Test Description", "top", "test_id");
        URI uri = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndPoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", "Test task")
                .queryParam("desc", "Test Description")
                .queryParam("pos", "top")
                .queryParam("idList", "test_id").build().encode().toUri();

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard("1", "Test task", "http://test.com");

        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }
    @Test
    public void shouldReturnEmptyList() {
        //Given
        TrelloBoardDto[] trelloBoards = null;

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndPoint() + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoardsKodillaVersion = trelloClient.getTrelloBoards();
        //Then
        assertNull(trelloBoards);
        assertEquals(0, fetchedTrelloBoardsKodillaVersion.size());
    }
}