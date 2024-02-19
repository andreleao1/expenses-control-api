package br.com.agls.expensescontrolapi.domain.service.impl;

import br.com.agls.expensescontrolapi.api.dto.out.TransactionSavedResponseDTO;
import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import br.com.agls.expensescontrolapi.domain.exceptions.ConstraintViolationException;
import br.com.agls.expensescontrolapi.domain.service.AccountService;
import br.com.agls.expensescontrolapi.domain.service.TransactionService;
import br.com.agls.expensescontrolapi.infra.repository.TransactionRepository;
import jakarta.transaction.Transactional;
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
    private final AccountService accountService;


    @Transactional
    @Override
    public TransactionSavedResponseDTO save(Transaction transaction) {
        LOGGER.info("Saving transaction request id: {}", transaction.getRequestId());

        try {
            Transaction transactionSaved = this.transactionRepository.save(transaction);
            this.accountService.updateBalance(transactionSaved);
            LOGGER.info("Transaction saved transaction id: {} request id: {}", transactionSaved.getId(), transactionSaved.getRequestId());
            return TransactionSavedResponseDTO.builder().transactionId(transactionSaved.getId().toString()).build();
        } catch (Exception e) {
            LOGGER.error("Error trying to save transaction, request id {} error message: {}", transaction.getRequestId(), e.getMessage());
            throw new ConstraintViolationException("Error trying to save transaction category");
        }
    }

    @Override
    public List<Transaction> getTransactionsPerUserId(String userId, Integer page, Integer size) {
        AtomicReference<List<Transaction>> transactions = new AtomicReference<>(new ArrayList<>());

        try {
            LOGGER.info("Fetching transactions per user id: {}", userId);
            this.transactionRepository.findByUserId(userId, size, page).ifPresent(transactions::set);
        } catch (Exception e) {
            LOGGER.error("Error trying to get transactions, user id {} error message: {}", userId, e.getMessage());
            throw new ConstraintViolationException("Error trying to get transactions");
        }

        return transactions.get();
    }
}
