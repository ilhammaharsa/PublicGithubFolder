package com.multipolar.bootcamp.account.controller;


import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.dto.ErrorMessage;
import com.multipolar.bootcamp.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody Account account, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        Account createdAccount = accountService.createOrUpdateAccount(account);
        return ResponseEntity.ok(createdAccount);
    }
    //getAll
    @GetMapping
    public List<Account> getAccount(){
        return accountService.getAllAccount();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Account> getAccountId(@PathVariable String id){
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/id/{id}")
    public Account updateAccount(@PathVariable String id, @RequestBody Account account){
        return accountService.createOrUpdateAccount(account);
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCustomerId(@PathVariable String id){
        accountService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

}
