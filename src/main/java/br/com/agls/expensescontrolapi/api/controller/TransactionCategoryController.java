package br.com.agls.expensescontrolapi.api.controller;

import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.service.TransactionCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-categories")
@RequiredArgsConstructor
public class TransactionCategoryController {

    private final TransactionCategoryService transactionCategoryService;

    @PostMapping
    public ResponseEntity<TransactionCategory> save(@RequestBody @Valid TransactionCategory transactionCategory) {
        return ResponseEntity.ok(this.transactionCategoryService.save(transactionCategory));
    }

    @GetMapping
    public ResponseEntity<List<TransactionCategory>> list() {
        return ResponseEntity.ok(this.transactionCategoryService.list());
    }
}
