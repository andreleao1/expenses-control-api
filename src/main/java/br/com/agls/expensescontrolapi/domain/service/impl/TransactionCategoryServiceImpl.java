package br.com.agls.expensescontrolapi.domain.service.impl;

import br.com.agls.expensescontrolapi.api.dto.TransactionCategoryResponsePageable;
import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.exceptions.ConstraintViolationException;
import br.com.agls.expensescontrolapi.domain.service.TransactionCategoryService;
import br.com.agls.expensescontrolapi.infra.repository.TransactionCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCategoryServiceImpl.class);

    private final TransactionCategoryRepository transactionCategoryRepository;
    @Override
    public void save(TransactionCategory transactionCategory) {
        try {
            this.transactionCategoryRepository.save(transactionCategory);
        } catch (Exception e) {
            LOGGER.error("Error trying to save transaction category {}", e.getMessage());
            throw new ConstraintViolationException("Error trying to save transaction category");
        }
    }

    @Override
    public void update(TransactionCategory transactionCategory) {
        this.transactionCategoryRepository.update(transactionCategory.getName(), transactionCategory.getDescription(),
                transactionCategory.getStatus(), transactionCategory.getIcon(), transactionCategory.getColor(),
                transactionCategory.getId());
    }

    @Override
    public List<TransactionCategory> listActive() {
        return this.transactionCategoryRepository.findAllByStatusActive();
    }

    @Override
    public TransactionCategoryResponsePageable list(Pageable pageable) {

        TransactionCategoryResponsePageable transactionCategoryResponsePageable = TransactionCategoryResponsePageable
                .builder()
                .content(List.of())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(0)
                .build();

        Page<TransactionCategory> transactionCategoryPage = this.transactionCategoryRepository.findAll(pageable);

        if (!transactionCategoryPage.getContent().isEmpty()) {
            transactionCategoryResponsePageable.setContent(transactionCategoryPage.getContent());
            transactionCategoryResponsePageable.setTotalElements((int) transactionCategoryPage.getTotalElements());
        }

        return transactionCategoryResponsePageable;
    }

    @Override
    public void delete(Long id) {
        if(this.transactionCategoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Transaction category not found");
        }
        this.transactionCategoryRepository.deleteById(id);
    }
}
