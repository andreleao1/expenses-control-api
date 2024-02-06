package br.com.agls.expensescontrolapi.infra.repository;

import br.com.agls.expensescontrolapi.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT * FROM account WHERE user_id = ?1", nativeQuery = true)
    List<Account> findByUserId(UUID userId);
}
