package br.com.agls.expensescontrolapi.domain.service.impl;

import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.service.TransactionCategoryService;
import br.com.agls.expensescontrolapi.infra.repository.TransactionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private final TransactionCategoryRepository transactionCategoryRepository;
    @Override
    public TransactionCategory save(TransactionCategory transactionCategory) {
        return this.transactionCategoryRepository.save(transactionCategory);
    }

    @Override
    public List<TransactionCategory> list() {
        return this.transactionCategoryRepository.findAll();
    }
}
