package com.afam.backendapistest.service;

import java.util.ArrayList;

import com.afam.backendapistest.dao.UsernameDao;
import com.afam.backendapistest.model.UsernameDetailsModel;
import com.afam.backendapistest.model.UsernameRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UsernameDao usernameDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UsernameRequestModel model = new UsernameRequestModel();
//        model.setUsername(username);
        UsernameDetailsModel user = usernameDao.usernameResponse(username);
        if(null != user){
            return  new User(username, user.getPassword(),new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("Invalid Credentials:: " + username);
        }

    }
}
