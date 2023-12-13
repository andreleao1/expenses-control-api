package br.com.agls.expensescontrolapi.infra.repository;

import br.com.agls.expensescontrolapi.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
