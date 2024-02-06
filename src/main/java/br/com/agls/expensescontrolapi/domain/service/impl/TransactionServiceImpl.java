package br.com.agls.expensescontrolapi.domain.service.impl;

import br.com.agls.expensescontrolapi.api.dto.out.TransactionResponseDTO;
import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import br.com.agls.expensescontrolapi.domain.exceptions.ConstraintViolationException;
import br.com.agls.expensescontrolapi.domain.service.TransactionService;
import br.com.agls.expensescontrolapi.infra.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;


    @Override
    public TransactionResponseDTO save(Transaction transaction) {
        LOGGER.info("Saving transaction request id: {}", transaction.getRequestId());

        try {
            Transaction transactionSaved = this.transactionRepository.save(transaction);
            LOGGER.info("Transaction saved transaction id: {} request id: {}", transactionSaved.getId(), transactionSaved.getRequestId());
            return TransactionResponseDTO.builder().transactionId(transactionSaved.getId().toString()).build();
        } catch (Exception e) {
            LOGGER.error("Error trying to save transaction, request id {} error message: {}", transaction.getRequestId(), e.getMessage());
            throw new ConstraintViolationException("Error trying to save transaction category");
        }
    }

    @Override
    public List<Transaction> getTransactionsPerUserId(String userId, Integer page, Integer size) {
        LOGGER.info("Getting transactions per user id: {}", userId);

        AtomicReference<List<Transaction>> transactions = new AtomicReference<>(new ArrayList<>());

        this.transactionRepository.findByUserId(userId, size, page).ifPresent(transaction -> {
            LOGGER.info("Transaction done for user id: {}", userId);
            transactions.set(transaction);
        });

        return transactions.get();
    }
}
