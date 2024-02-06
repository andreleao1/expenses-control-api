package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.api.dto.out.TransactionResponseDTO;
import br.com.agls.expensescontrolapi.domain.entity.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionResponseDTO save(Transaction transaction);

    List<Transaction> getTransactionsPerUserId(String userId, Integer page, Integer size);
}
