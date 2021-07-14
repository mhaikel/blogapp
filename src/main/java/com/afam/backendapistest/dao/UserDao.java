package com.afam.backendapistest.dao;


import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    GenericResponse signUp(User request);
}
