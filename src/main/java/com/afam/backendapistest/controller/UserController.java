package com.afam.backendapistest.controller;


import com.afam.backendapistest.dao.UserDao;
import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.SignUpResponse;
import com.afam.backendapistest.model.User;
import com.afam.backendapistest.service.EmailService;
import com.afam.backendapistest.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bloguser")
public class UserController {
    @Autowired
    UserDao userDao;
    User userModal;

    @Autowired
    Token token;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> signUpUser(@RequestBody User requestModel){
        SignUpResponse  response = userDao.signUp(requestModel);

        if (response.getResponseCode().startsWith("00")){
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(requestModel.getEmail());
            mailMessage.setSubject("COMPLETE REGISTRATION!!!");
            mailMessage.setText("To confirm your account, please click here : " + "http://localhost:7001/backendapistest/confirm-account/token?token=" + response.getToken());

            emailService.sendEmail(mailMessage);

        }


        return ResponseEntity.ok(response);
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
