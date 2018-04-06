package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndPoint;
    @Value("${trello.app.key}")
    private String trelloApiKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(createUrl(), TrelloBoardDto[].class);

        TrelloBoardDto[] nullableResponse = new TrelloBoardDto[1];
        nullableResponse[0] = new TrelloBoardDto("no data", "no data");

        return Arrays.asList(ofNullable(boardsResponse).orElse(nullableResponse));

    }

    private URI createUrl() {
        return  UriComponentsBuilder.fromHttpUrl(trelloApiEndPoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloApiKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();
    }
}
