package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.api.dto.TransactionCategoryResponsePageable;
import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.enums.TransactionCategoryStatus;
import br.com.agls.expensescontrolapi.domain.service.impl.TransactionCategoryServiceImpl;
import br.com.agls.expensescontrolapi.infra.repository.TransactionCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionCategoryServiceImplTest {

    @Mock
    private TransactionCategoryRepository transactionCategoryRepository;

    @InjectMocks
    private TransactionCategoryServiceImpl transactionCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ValidTransactionCategory_ShouldSaveSuccessfully() {
        // Arrange
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setName("Category 1");
        transactionCategory.setDescription("Category 1 description");
        transactionCategory.setStatus(TransactionCategoryStatus.ACTIVE);
        transactionCategory.setIcon("icon1");
        transactionCategory.setColor("color1");

        // Act
        transactionCategoryService.save(transactionCategory);

        // Assert
        verify(transactionCategoryRepository, times(1)).save(transactionCategory);
    }

    @Test
    void update_ValidTransactionCategory_ShouldUpdateSuccessfully() {
        // Arrange
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setId(1L);
        transactionCategory.setName("Category 1");
        transactionCategory.setDescription("Category 1 description");
        transactionCategory.setStatus(TransactionCategoryStatus.ACTIVE);
        transactionCategory.setIcon("icon1");
        transactionCategory.setColor("color1");

        // Act
        transactionCategoryService.update(transactionCategory);

        // Assert
        verify(transactionCategoryRepository, times(1)).update(
                transactionCategory.getName(), transactionCategory.getDescription(),
                transactionCategory.getStatus(), transactionCategory.getIcon(),
                transactionCategory.getColor(), transactionCategory.getId());
    }

    @Test
    void listActive_NoActiveCategories_ShouldReturnEmptyList() {
        // Arrange
        when(transactionCategoryRepository.findAllByStatusActive()).thenReturn(new ArrayList<>());

        // Act
        List<TransactionCategory> result = transactionCategoryService.listActive();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void listActive_HasActiveCategories_ShouldReturnListOfActiveCategories() {
        // Arrange
        List<TransactionCategory> activeCategories = new ArrayList<>();
        activeCategories.add(new TransactionCategory());
        activeCategories.add(new TransactionCategory());

        when(transactionCategoryRepository.findAllByStatusActive()).thenReturn(activeCategories);

        // Act
        List<TransactionCategory> result = transactionCategoryService.listActive();

        // Assert
        assertEquals(activeCategories.size(), result.size());
        assertTrue(result.containsAll(activeCategories));
    }

    @Test
    void list_NoCategories_ShouldReturnEmptyPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(transactionCategoryRepository.findAll(pageable)).thenReturn(Page.empty());

        // Act
        TransactionCategoryResponsePageable result = transactionCategoryService.list(pageable);

        // Assert
        assertEquals(0, result.getContent().size());
        assertEquals(0, result.getTotalElements());
        assertEquals(pageable.getPageNumber(), result.getPage());
        assertEquals(pageable.getPageSize(), result.getSize());
    }

    @Test
    void list_HasCategories_ShouldReturnPageWithCategories() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<TransactionCategory> categories = new ArrayList<>();
        categories.add(new TransactionCategory());
        categories.add(new TransactionCategory());

        Page<TransactionCategory> page = new PageImpl<>(categories, pageable, categories.size());
        when(transactionCategoryRepository.findAll(pageable)).thenReturn(page);

        // Act
        TransactionCategoryResponsePageable result = transactionCategoryService.list(pageable);

        // Assert
        assertEquals(categories.size(), result.getContent().size());
        assertEquals(categories.size(), result.getTotalElements());
        assertEquals(pageable.getPageNumber(), result.getPage());
        assertEquals(pageable.getPageSize(), result.getSize());
        assertTrue(result.getContent().containsAll(categories));
    }

    @Test
    void delete_ExistingCategoryId_ShouldDeleteSuccessfully() {
        // Arrange
        Long categoryId = 1L;
        when(transactionCategoryRepository.findById(categoryId)).thenReturn(Optional.of(new TransactionCategory()));

        // Act
        transactionCategoryService.delete(categoryId);

        // Assert
        verify(transactionCategoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void delete_NonExistingCategoryId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        when(transactionCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> transactionCategoryService.delete(categoryId));
    }
}