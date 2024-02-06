package br.com.agls.expensescontrolapi.api.controller;

import br.com.agls.expensescontrolapi.api.dto.in.TransactionRequestDTO;
import br.com.agls.expensescontrolapi.api.dto.out.TransactionResponseDTO;
import br.com.agls.expensescontrolapi.api.dto.out.TransactionResponsePagedDTO;
import br.com.agls.expensescontrolapi.domain.service.TransactionService;
import br.com.agls.expensescontrolapi.util.TransactionBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> save(
            @RequestHeader("x-request-id") String requestId,
            @RequestHeader("x-user-id") String userId,
            @RequestBody @Valid TransactionRequestDTO transactionRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.transactionService.save(TransactionBuilder.execute(transactionRequestDTO, TransactionBuilder.RequestParams.builder()
                        .requestId(requestId)
                        .userId(userId)
                        .build())));
    }

    @GetMapping
    public ResponseEntity<TransactionResponsePagedDTO> getTransactionsPerUserId(
            @RequestHeader("x-request-id") String requestId,
            @RequestHeader("x-user-id") String userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok().body(TransactionResponsePagedDTO.builder()
                .transactions(this.transactionService.getTransactionsPerUserId(userId, page, size))
                .build());
    }
}
