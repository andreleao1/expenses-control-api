package br.com.agls.expensescontrolapi.api.controller;

import br.com.agls.expensescontrolapi.api.dto.in.AccountRequestDTO;
import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.domain.service.AccountService;
import br.com.agls.expensescontrolapi.util.AccountBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody AccountRequestDTO accountRequest) {
        Account account = AccountBuilder.execute(accountRequest);

        this.accountService.save(account);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{accountId}/archive")
    public ResponseEntity<Void> archive(@PathVariable String accountId) {
        this.accountService.archive(accountId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{accountId}/unarchive")
    public ResponseEntity<Void> unarchive(@PathVariable String accountId) {
        this.accountService.unarchive(accountId);

        return ResponseEntity.ok().build();
    }
}
