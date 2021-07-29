package com.afam.backendapistest.dao;


import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.SignUpResponse;
import com.afam.backendapistest.model.User;
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

}
