package br.com.agls.expensescontrolapi.infra.repository;

import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT " +
            "ID, DESCRIPTION, REQUEST_ID, USER_ID, VALUE, CATEGORY_ID, TYPE, PAYMENT_METHOD, STATUS, CREATED_AT, UPDATED_AT " +
            "FROM TRANSACTION " +
            "WHERE USER_ID = ?1" +
            "LIMIT ?2 ", nativeQuery = true)
    Optional<List<Transaction>> findByUserId(String userId, Integer size, Integer page);
}
