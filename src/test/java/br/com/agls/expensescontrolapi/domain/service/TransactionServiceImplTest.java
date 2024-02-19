package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.api.dto.out.TransactionSavedResponseDTO;
import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import br.com.agls.expensescontrolapi.domain.service.impl.TransactionServiceImpl;
import br.com.agls.expensescontrolapi.infra.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService = new TransactionServiceImpl(transactionRepository);

    public TransactionServiceImplTest() {
        this.transactionRepository = Mockito.mock(TransactionRepository.class);
        this.transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    @DisplayName("Save valid transaction")
    void saveValidTransactionShouldSaveSuccessfully() {
        UUID UUI = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setId(UUI);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionSavedResponseDTO transactionResponse = transactionService.save(transaction);

        Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
        Assertions.assertEquals(UUI.toString(), transactionResponse.getTransactionId());
    }

    @Test
    @DisplayName("Find transactions per user id")
    void findTransactionsPerUserIdShouldFindSuccessfully() {
        String userId = UUID.randomUUID().toString();
        Integer page = 0;
        Integer size = 10;
        transactionService.getTransactionsPerUserId(userId, page, size);

        Mockito.verify(transactionRepository, Mockito.times(1)).findByUserId(userId, size, page);
    }
}
