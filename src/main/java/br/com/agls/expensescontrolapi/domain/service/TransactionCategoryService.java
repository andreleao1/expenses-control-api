package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;

import java.util.List;

public interface TransactionCategoryService {

    TransactionCategory save(TransactionCategory transactionCategory);

    List<TransactionCategory> list();
}
