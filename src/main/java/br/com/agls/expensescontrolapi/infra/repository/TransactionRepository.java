package br.com.agls.expensescontrolapi.infra.repository;

import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT " +
            "id, description, category_id, request_id, user_id, value, type, payment_method, status, transaction_date, created_at, updated_at, account_id " +
            "FROM transaction " +
            "WHERE user_id = ?1 " +
            "LIMIT ?2 " +
            "OFFSET ?3", nativeQuery = true)
    Optional<List<Transaction>> findByUserId(String userId, Integer size, Integer page);
}
