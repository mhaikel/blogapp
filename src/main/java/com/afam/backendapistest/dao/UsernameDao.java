package com.afam.backendapistest.dao;

import com.afam.backendapistest.model.UsernameDetailsModel;
import com.afam.backendapistest.model.UsernameRequestModel;
import org.springframework.stereotype.Repository;

@Repository
public interface UsernameDao {
    public UsernameDetailsModel usernameResponse(String usernameRequestModel);
}
