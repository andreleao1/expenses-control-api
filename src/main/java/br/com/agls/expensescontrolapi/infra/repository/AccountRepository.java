package br.com.agls.expensescontrolapi.infra.repository;

import br.com.agls.expensescontrolapi.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Modifying
    @Query(value = "UPDATE account SET balance = ?1 WHERE account_id = ?2", nativeQuery = true)
    void updateBalance(String newBalance, UUID accountId);

    @Query(value = "SELECT * FROM account WHERE user_id = ?1", nativeQuery = true)
    List<Account> findByUserId(String userId);

    @Query(value = "SELECT * FROM account WHERE account_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<Account> findByIdAndUserId(UUID id, String userId);
}
