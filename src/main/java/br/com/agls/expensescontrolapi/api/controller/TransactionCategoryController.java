package br.com.agls.expensescontrolapi.api.controller;

import br.com.agls.expensescontrolapi.api.dto.out.TransactionCategoryResponsePageable;
import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.service.TransactionCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-categories")
@RequiredArgsConstructor
public class TransactionCategoryController {

    private final TransactionCategoryService transactionCategoryService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid TransactionCategory transactionCategory) {
        this.transactionCategoryService.save(transactionCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid TransactionCategory transactionCategory) {
        this.transactionCategoryService.update(transactionCategory);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TransactionCategoryResponsePageable> list(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.transactionCategoryService.list(pageable));
    }

    @GetMapping("/list-active")
    public ResponseEntity<List<TransactionCategory>> listActive() {
        return ResponseEntity.ok(this.transactionCategoryService.listActive());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.transactionCategoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
