package com.afam.backendapistest.controller;


import com.afam.backendapistest.dao.UserDao;
import com.afam.backendapistest.dao.UsernameDao;
import com.afam.backendapistest.dao.UsernameDaoImpl;
import com.afam.backendapistest.model.*;
import com.afam.backendapistest.service.EmailService;
import com.afam.backendapistest.util.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bloguser")
public class UserController {
    @Autowired
    UserDao userDao;

    @Autowired
    Token token;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsernameDao usernameDao;

    @Autowired
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> signUpUser(@RequestBody User requestModel){
        SignUpResponse  response = userDao.signUp(requestModel);

        try {
            if (response.getResponseCode().startsWith("00")){
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(requestModel.getEmail());
                mailMessage.setSubject("COMPLETE REGISTRATION!!!");
                mailMessage.setText("To confirm your account, please click here : " + "http://localhost:7001/backendapistest/confirm-account/token?token=" + response.getToken());

                emailService.sendEmail(mailMessage);

            }
        }catch (Exception e){
            e.getMessage();
        }



        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestModel userRequestDetails){
        UserLoginRequestModel userRequest = new UserLoginRequestModel();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // the below picks up username and encoded password from the database
        // and stores them in a variable has shown in the next two lines
        UsernameDetailsModel usernameEncodedPasswordResp = usernameDao.usernameResponse(userRequestDetails.getUsername());
        String encodedPassword = usernameEncodedPasswordResp.getPassword();

        //the below boolean expression checks if the encoded password from the database matches with the password
        // passed in when logging in i.e from username login request pojo class when signing in...
        boolean doesPasswordMatch = encoder.matches(userRequestDetails.getPassword(), encodedPassword);

        logger.info("password match check ::: " + doesPasswordMatch);

        userRequest.setUsername(userRequestDetails.getUsername());
        userRequest.setPassword(encodedPassword);

        logger.info("encoded password ::: " + encodedPassword);

       GenericResponse usernamePasswordResponse = userDao.userLogin(userRequest);
       GenericResponse usernameFlagCheckResponse = userDao.verifiedUsernameCheck(userRequest.getUsername());

      if (doesPasswordMatch == true) {
          if (usernamePasswordResponse.getResponseCode().startsWith("99")){
              return new ResponseEntity("Invalid User Credentials",HttpStatus.UNAUTHORIZED);
          }else if (usernamePasswordResponse.getResponseCode().startsWith("00") &&
                  usernameFlagCheckResponse.getResponseCode().startsWith("99") ){
              return new ResponseEntity("User not verified, please verify your account",HttpStatus.PRECONDITION_REQUIRED);
          }else if (usernamePasswordResponse.getResponseCode().startsWith("00") &&
                  usernameFlagCheckResponse.getResponseCode().startsWith("00")){
              return new ResponseEntity("Login Successful",HttpStatus.OK);
          }

      }else {
          return new ResponseEntity("Invalid User Credentials", HttpStatus.UNAUTHORIZED);
      }
      return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }





//    @GetMapping("/confirm-account")
//    public String validateTokenLink(){
//        return "accountVerified";
//    }

//    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
//    {
//        Token token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
//
//        if(token != null)
//        {
//            UserEntity user = userRepository.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
//            user.setEnabled(true);
//            userRepository.save(user);
//            modelAndView.setViewName("accountVerified");
//        }
//        else
//        {
//            modelAndView.addObject("message","The link is invalid or broken!");
//            modelAndView.setViewName("error");
//        }
//
//        return modelAndView;
//    }


}
