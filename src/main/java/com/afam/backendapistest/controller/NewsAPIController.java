package com.afam.backendapistest.controller;

import com.afam.backendapistest.model.NewsAPIPojoResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/newsapi")
public class NewsAPIController {


    private final Logger logger = LoggerFactory.getLogger(NewsAPIController.class);

    @GetMapping("/homepage")
    public NewsAPIPojoResponse newsAPIResponse(){

        logger.info("got here 1::::::::::::::: ");

        RestTemplate restTemplate = new RestTemplate();
        final String uri = "http://newsapi.org/v2/everything?domains=wsj.com&apiKey=b119916e09e84be08cc31a8a948e2639";

        logger.info("got here 2:::::::::::: " + uri);

        ResponseEntity<NewsAPIPojoResponse> myPojo = restTemplate.getForEntity(uri, NewsAPIPojoResponse.class);
        logger.info("response::: " + myPojo.getBody());

        //return myPojo != null ? myPojo.getBody() : new NewsAPIPojoResponse();
//        JSONObject respJson = new JSONObject(myPojo);
//        logger.info("respJson ::: " + respJson);
        return myPojo.getBody();
    }



}
