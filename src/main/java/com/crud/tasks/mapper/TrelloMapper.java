package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDtoList) {
        return trelloBoardDtoList.stream()
                .map(trelloBoardDto -> new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToList(trelloBoardDto.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToBoardDto(final List<TrelloBoard> trelloBoardList) {
        return trelloBoardList.stream()
                .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToListDto(trelloBoard.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDtoList) {
        return trelloListDtoList.stream()
                .map(trelloListDto -> new TrelloList(trelloListDto.getId(), trelloListDto.getName(), trelloListDto.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloListList) {
        return trelloListList.stream()
                .map(trelloList -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(), trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDesc(), trelloCardDto.getPos(), trelloCardDto.getIdList());
    }

}
