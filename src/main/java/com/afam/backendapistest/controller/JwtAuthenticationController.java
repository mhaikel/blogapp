package com.afam.backendapistest.controller;

import java.util.Objects;

import com.afam.backendapistest.dao.UsernameDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.afam.backendapistest.service.JwtUserDetailsService;


import com.afam.backendapistest.config.JwtTokenUtil;
import com.afam.backendapistest.model.JwtRequest;
import com.afam.backendapistest.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

//        JwtRequest encodedPass = new JwtRequest();
//
//        encodedPass.setUsername(authenticationRequest.getUsername());
//        encodedPass.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
//        logger.info("got here::::::" + encodedPass);
        logger.info("got here 1");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        logger.info("got here 2");

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());


        final String token = jwtTokenUtil.generateToken(userDetails);
        logger.info("Token ::: " + token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }




}