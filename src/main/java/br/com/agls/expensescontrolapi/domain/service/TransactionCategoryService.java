package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.api.dto.TransactionCategoryResponsePageable;
import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionCategoryService {

    void save(TransactionCategory transactionCategory);

    void update(TransactionCategory transactionCategory);

    TransactionCategoryResponsePageable list(Pageable pageable);

    List<TransactionCategory> listActive();

    void delete(Long id);
}
