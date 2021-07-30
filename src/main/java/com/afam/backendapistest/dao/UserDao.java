package com.afam.backendapistest.dao;


import com.afam.backendapistest.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    SignUpResponse signUp(User request);

    //todo
    String findByToken(String confirmationToken);

    //todo
    GenericResponse updateEnableFlag(String confirmationToken);

    //todo
    GenericResponse checkEnableFlag(String confirmationToken);

    //todo
    GenericResponse userLogin(UserLoginRequestModel request);

    //todo
    GenericResponse verifiedUsernameCheck(String username);

}
