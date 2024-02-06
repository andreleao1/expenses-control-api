package br.com.agls.expensescontrolapi.api.controller;

import br.com.agls.expensescontrolapi.api.dto.in.AccountRequestDTO;
import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.domain.service.AccountService;
import br.com.agls.expensescontrolapi.util.AccountBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{accountId}/user/{userId}/archive")
    public ResponseEntity<Void> archive(@PathVariable String accountId, @PathVariable String userId) {
        this.accountService.archive(accountId, userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{accountId}/user/{userId}/unarchive")
    public ResponseEntity<Void> unarchive(@PathVariable String accountId, @PathVariable String userId) {
        this.accountService.unarchive(accountId, userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> findAllByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(this.accountService.getAccountsByUserId(userId));
    }

    @DeleteMapping("/{accountId}/user/{userId}")
    public ResponseEntity<Void> delete(@PathVariable String accountId, @PathVariable String userId) {
        this.accountService.delete(accountId, userId);

        return ResponseEntity.noContent().build();
    }
}
