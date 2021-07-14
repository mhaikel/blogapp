package com.afam.backendapistest.controller;

import com.afam.backendapistest.dao.UserDao;
import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bloguser")
public class UserController {
    @Autowired
    UserDao userDao;

    @PostMapping("/sign_up_user")
    public ResponseEntity<GenericResponse> sign_up_user(@RequestBody User requestModel){
        GenericResponse response = userDao.signUp(requestModel);
        return ResponseEntity.ok(response);
    }
}
