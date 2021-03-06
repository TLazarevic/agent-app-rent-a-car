package com.example.agentapp.controller.user;

import com.example.agentapp.dto.user.EmailDTO;
import com.example.agentapp.model.Notification;
import com.example.agentapp.model.user.User;
import com.example.agentapp.rabbit.EmailProducerController;
import com.example.agentapp.service.user.RegisterService;
import com.example.agentapp.service.user.UserDetailsService;
import com.example.agentapp.service.user.UserService;
import com.example.agentapp.service.user.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    RegisterService registerService;

    @Autowired
    EmailProducerController emailProducerController;

    @PostMapping(value= "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> register(@RequestBody User user) {

        if (registerService.validate(user)) {
            try {
                EmailDTO email = registerService.registerUser(user);
                this.sendVerificationMail(email);

                return new ResponseEntity<>(new Notification("You registered successfully, you will get verification mail soon!", true), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new Notification(e.getMessage(), false), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new Notification("User input didn't pass server valdiation!", false), HttpStatus.BAD_REQUEST);
        }
    }

    public void sendVerificationMail(EmailDTO emailDTO) {
        try {
            System.err.println("Sending email to RabbitMQ!");
            emailProducerController.publish(emailDTO);
            System.err.println("Email published!");
        } catch (Exception e) {
        }
    }

}
