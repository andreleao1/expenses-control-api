package br.com.agls.expensescontrolapi.infra.repository;

import br.com.agls.expensescontrolapi.domain.entity.TransactionCategory;
import br.com.agls.expensescontrolapi.domain.enums.TransactionCategoryStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {

    Page<TransactionCategory> findAll(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE transaction_category " +
            "SET " +
            "name = :name, " +
            "description = :description, " +
            "status = :status, " +
            "icon = :icon, " +
            "color = :color " +
            "WHERE id = :id", nativeQuery = true)
    void update(@Param("name") String name, @Param("description") String description, @Param("status") TransactionCategoryStatus status,
                @Param("icon") String icon, @Param("color") String color, @Param("id") Long id);

    @Query(value = "SELECT " +
            "id," +
            "name," +
            "description," +
            "status," +
            "icon," +
            "color " +
            "FROM transaction_category " +
            "WHERE status = '0'", nativeQuery = true)
    List<TransactionCategory> findAllByStatusActive();
}
