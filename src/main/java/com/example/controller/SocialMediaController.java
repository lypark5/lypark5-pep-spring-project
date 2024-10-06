package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.example.entity.Account;
// import com.example.entity.Message;
import com.example.exception.DuplicateAccountException;
import com.example.service.AccountService;
// import com.example.service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

// import javax.naming.AuthenticationException;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
// omit @RequestMapping because account and message have no root in common.
public class SocialMediaController {

    private AccountService accountService;
    // private MessageService messageService;

    @Autowired
    // public SocialMediaController(AccountService accountService, MessageService messageService) {
    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
        // this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            // try creating the acct, if successful, we'll get the resulting acct.
            Account newAccount = accountService.addAccount(account);
            return ResponseEntity.ok(newAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (DuplicateAccountException e) {         // custom named exception
            // return ResponseEntity.conflict().body(null);     // no such .conflict() direct method
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    // @PostMapping("/login")
    // public ResponseEntity<Void> login(@RequestBody Account account) throws AuthenticationException {
    //     accountService.login(account.getUsername(), account.getPassword());
        
    // }


}
