package com.saurabh.forgotpassword.controller;


import com.saurabh.forgotpassword.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email){
        String response = forgotPasswordService.forgotPass(email);

        if(!response.startsWith("Invalid")){
            response= "http://localhost:8080/reset-password?token=" + response;
        }
        return response;

    }

    @PutMapping("/reset-password")
    public String resetPass(@RequestParam String token ,@RequestParam String password){
        return forgotPasswordService.resetPass(token,password);
    }
}
