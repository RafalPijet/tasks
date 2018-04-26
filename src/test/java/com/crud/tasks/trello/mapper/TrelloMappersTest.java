package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMappersTest {

    @Autowired
    private TrelloMapper trelloMapper;
    private Random generator = new Random();

    private List<TrelloListDto> generateTrelloListDto() {
        int counter = generator.nextInt(10) + 1;
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            trelloListDtoList.add(new TrelloListDto(Integer.toString(i), "list" + i, generator.nextBoolean()));
        }
        return trelloListDtoList;
    }

    private List<TrelloList> generateTrelloList() {
        int counter = generator.nextInt(10) + 1;
        List<TrelloList> trelloListList = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            trelloListList.add(new TrelloList(Integer.toString(i), "list" + i, generator.nextBoolean()));
        }
        return trelloListList;
    }
    @Test
    public void testMapTrelloBoardDtoToTrelloBoard() {
        //Given
        int counter = generator.nextInt(10) + 1;
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            trelloBoardDtoList.add(new TrelloBoardDto(Integer.toString(i), "board" + i, generateTrelloListDto()));
        }
        //When
        int counterTrelloBoardDto = trelloBoardDtoList.size();
        List<TrelloBoard> result = trelloMapper.mapToBoards(trelloBoardDtoList);
        int checkNumber = generator.nextInt(result.size());
        //Then
        Assert.assertEquals(counterTrelloBoardDto, result.size());
        Assert.assertEquals(trelloBoardDtoList.get(checkNumber).getId(), result.get(checkNumber).getId());
        Assert.assertEquals(trelloBoardDtoList.get(checkNumber).getName(), result.get(checkNumber).getName());
        Assert.assertEquals(trelloBoardDtoList.get(checkNumber).getLists().size(), result.get(checkNumber).getLists().size());
    }
    @Test
    public void testMapTrelloBoardToTrelloBoardDto() {
        //Given
        int counter = generator.nextInt(10) + 1;
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            trelloBoardList.add(new TrelloBoard(Integer.toString(i), "board" + i, generateTrelloList()));
        }
        //When
        int counterTrelloBoard = trelloBoardList.size();
        List<TrelloBoardDto> result = trelloMapper.mapToBoardDto(trelloBoardList);
        int checkNumber = generator.nextInt(result.size());
        //Then
        Assert.assertEquals(counterTrelloBoard, result.size());
        Assert.assertEquals(trelloBoardList.get(checkNumber).getId(), result.get(checkNumber).getId());
        Assert.assertEquals(trelloBoardList.get(checkNumber).getName(), result.get(checkNumber).getName());
        Assert.assertEquals(trelloBoardList.get(checkNumber).getLists().size(), result.get(checkNumber).getLists().size());
    }
    @Test
    public void testMapTrelloListDtoToTrelloList() {
        //Given
        List<TrelloList> trelloListList = generateTrelloList();
        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(trelloListList);
        int checkNumber = generator.nextInt(result.size());
        //Then
        Assert.assertEquals(trelloListList.size(), result.size());
        Assert.assertEquals(trelloListList.get(checkNumber).getId(), result.get(checkNumber).getId());
        Assert.assertEquals(trelloListList.get(checkNumber).getName(), result.get(checkNumber).getName());
        Assert.assertEquals(trelloListList.get(checkNumber).isClosed(), result.get(checkNumber).isClosed());
    }
    @Test
    public void testMapTrelloListToTrelloListDto() {
        //Given
        List<TrelloListDto> trelloListDtoList = generateTrelloListDto();
        //When
        List<TrelloList> result = trelloMapper.mapToList(trelloListDtoList);
        int checkNumber = generator.nextInt(result.size());
        //Then
        Assert.assertEquals(trelloListDtoList.size(), result.size());
        Assert.assertEquals(trelloListDtoList.get(checkNumber).getId(), result.get(checkNumber).getId());
        Assert.assertEquals(trelloListDtoList.get(checkNumber).getName(), result.get(checkNumber).getName());
        Assert.assertEquals(trelloListDtoList.get(checkNumber).isClosed(), result.get(checkNumber).isClosed());
    }
    @Test
    public void testMapTrelloCardDtoToTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Test Description Card", "Bottom", "1");
        //When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals(trelloCardDto.getName(), result.getName());
        Assert.assertEquals(trelloCardDto.getDesc(), result.getDescription());
        Assert.assertEquals(trelloCardDto.getPos(), result.getPos());
        Assert.assertEquals(trelloCardDto.getIdList(), result.getListId());
    }
    @Test
    public void testMapTrelloCardToTrelloCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Test Card", "Test Description Card", "Bottom", "1");
        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals(trelloCard.getName(), result.getName());
        Assert.assertEquals(trelloCard.getDescription(), result.getDesc());
        Assert.assertEquals(trelloCard.getPos(), result.getPos());
        Assert.assertEquals(trelloCard.getListId(), result.getIdList());

    }
}
