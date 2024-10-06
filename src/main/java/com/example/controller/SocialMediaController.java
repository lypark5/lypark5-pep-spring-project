package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateAccountException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;


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
    private MessageService messageService;
 
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            // try creating the acct, if successful, we'll get the resulting acct.
            Account newAccount = accountService.addAccount(account);
            return ResponseEntity.ok(newAccount);       // for .ok, can put body right in the parentheses
        } catch (DuplicateAccountException e) {         // custom named exception, more specific so comes first
            // return ResponseEntity.conflict().body(null);     // no such .conflict() direct method exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }    
 

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account resAccount = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(resAccount);       // for .ok, can put body right in the parentheses
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    // in Spring Boot, endpoint order by specificity does not matter, just go by logical order.


    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesOfUser(@PathVariable int accountId) {
        return ResponseEntity.ok(accountService.getMessagesOfUser(accountId));
    }


    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {            // method named here
        return ResponseEntity.ok(messageService.getAllMessages());     // method named in service
    }


    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return ResponseEntity.ok(messageService.getMessageById(messageId));
    }


    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message reqMessage) {
        try {
            return ResponseEntity.ok(messageService.addMessage(reqMessage));
        }   catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message reqMessage) {
        try {
            Integer updatedRows = messageService.updateMessage(messageId, reqMessage);
            return ResponseEntity.ok(updatedRows);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        try {
            Integer deletedRows = messageService.deleteMessage(messageId);
            return ResponseEntity.ok(deletedRows);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(null);
        }
    }


}
