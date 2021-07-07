package com.afam.backendapistest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendAPIsTestController {

    @RequestMapping({"/apitest"})
    public String firstPage(){
        return "Hello World!!!";
    }
}
