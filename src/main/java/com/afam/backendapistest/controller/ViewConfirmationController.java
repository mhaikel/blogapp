package com.afam.backendapistest.controller;

import com.afam.backendapistest.dao.UserDao;
import com.afam.backendapistest.dao.UsernameDaoImpl;
import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewConfirmationController {
    private final Logger logger = LoggerFactory.getLogger(ViewConfirmationController.class);

    @Autowired
    UserDao userDao;

    @GetMapping("/confirm-account/token/{token}")
    public String validateTokenLink(){

        return "accountVerified";
    }

    @RequestMapping(value="/confirm-account/token", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        String token = userDao.findByToken(confirmationToken);
        GenericResponse getEnableFlageResponse = userDao.checkEnableFlag(confirmationToken);

        if(token != null && getEnableFlageResponse.getResponseCode().startsWith("00"))
        {
            GenericResponse response = userDao.updateEnableFlag(confirmationToken);
            logger.info("Update Response ::: " + response.getResponseCode());
            return "accountVerified";
        }else {
            return "invalidToken";
        }
    }
}

