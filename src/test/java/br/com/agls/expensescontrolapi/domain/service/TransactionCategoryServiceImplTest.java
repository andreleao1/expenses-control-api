package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.api.dto.out.TransactionCategoryResponsePageable;
import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.enums.TransactionCategoryStatus;
import br.com.agls.expensescontrolapi.domain.service.impl.TransactionCategoryServiceImpl;
import br.com.agls.expensescontrolapi.infra.repository.TransactionCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionCategoryServiceImplTest {

    private TransactionCategoryRepository transactionCategoryRepository;

    private TransactionCategoryServiceImpl transactionCategoryService;

    @BeforeEach
    void setUp() {

        transactionCategoryRepository = Mockito.mock(TransactionCategoryRepository.class);
        transactionCategoryService = new TransactionCategoryServiceImpl(transactionCategoryRepository);
    }

    @Test
    @DisplayName("Save valid transaction category")
    void saveValidTransactionCategoryShouldSaveSuccessfully() {
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setName("Category 1");
        transactionCategory.setDescription("Category 1 description");
        transactionCategory.setStatus(TransactionCategoryStatus.ACTIVE);
        transactionCategory.setIcon("icon1");
        transactionCategory.setColor("color1");

        transactionCategoryService.save(transactionCategory);

        verify(transactionCategoryRepository, times(1)).save(transactionCategory);
    }

    @Test
    @DisplayName("Update valid transaction category")
    void updateValidTransactionCategoryShouldUpdateSuccessfully() {
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setId(1L);
        transactionCategory.setName("Category 1");
        transactionCategory.setDescription("Category 1 description");
        transactionCategory.setStatus(TransactionCategoryStatus.ACTIVE);
        transactionCategory.setIcon("icon1");
        transactionCategory.setColor("color1");

        transactionCategoryService.update(transactionCategory);

        verify(transactionCategoryRepository, times(1)).update(
                transactionCategory.getName(), transactionCategory.getDescription(),
                transactionCategory.getStatus(), transactionCategory.getIcon(),
                transactionCategory.getColor(), transactionCategory.getId());
    }

    @Test
    @DisplayName("Archive transaction category")
    void listActiveNoActiveCategoriesShouldReturnEmptyList() {
        when(transactionCategoryRepository.findAllByStatusActive()).thenReturn(new ArrayList<>());

        List<TransactionCategory> result = transactionCategoryService.listActive();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("List active transaction categories")
    void listActiveHasActiveCategoriesShouldReturnListOfActiveCategories() {
        List<TransactionCategory> activeCategories = new ArrayList<>();
        activeCategories.add(new TransactionCategory());
        activeCategories.add(new TransactionCategory());

        when(transactionCategoryRepository.findAllByStatusActive()).thenReturn(activeCategories);

        List<TransactionCategory> result = transactionCategoryService.listActive();

        assertEquals(activeCategories.size(), result.size());
        assertTrue(result.containsAll(activeCategories));
    }

    @Test
    @DisplayName("List all transaction categories")
    void listNoCategoriesShouldReturnEmptyPage() {
        Pageable pageable = PageRequest.of(0, 10);
        when(transactionCategoryRepository.findAll(pageable)).thenReturn(Page.empty());

        TransactionCategoryResponsePageable result = transactionCategoryService.list(pageable);

        assertEquals(0, result.getContent().size());
        assertEquals(0, result.getTotalElements());
        assertEquals(pageable.getPageNumber(), result.getPage());
        assertEquals(pageable.getPageSize(), result.getSize());
    }

    @Test
    @DisplayName("List all transaction categories")
    void listHasCategoriesShouldReturnPageWithCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        List<TransactionCategory> categories = new ArrayList<>();
        categories.add(new TransactionCategory());
        categories.add(new TransactionCategory());

        Page<TransactionCategory> page = new PageImpl<>(categories, pageable, categories.size());
        when(transactionCategoryRepository.findAll(pageable)).thenReturn(page);

        TransactionCategoryResponsePageable result = transactionCategoryService.list(pageable);

        assertEquals(categories.size(), result.getContent().size());
        assertEquals(categories.size(), result.getTotalElements());
        assertEquals(pageable.getPageNumber(), result.getPage());
        assertEquals(pageable.getPageSize(), result.getSize());
        assertTrue(result.getContent().containsAll(categories));
    }

    @Test
    @DisplayName("Delete existing category")
    void deleteExistingCategoryIdShouldDeleteSuccessfully() {
        Long categoryId = 1L;
        when(transactionCategoryRepository.findById(categoryId)).thenReturn(Optional.of(new TransactionCategory()));

        transactionCategoryService.delete(categoryId);

        verify(transactionCategoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Delete non existing category")
    void deleteNonExistingCategoryIdShouldThrowEntityNotFoundException() {
        Long categoryId = 1L;
        when(transactionCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> transactionCategoryService.delete(categoryId));
    }
}