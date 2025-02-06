package com.ajay.payment_service.transaction_management.repo;

import com.ajay.payment_service.transaction_management.dao.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepo extends JpaRepository<AccountEntity, String> {

    @Modifying
    @Transactional
    @Query("UPDATE AccountEntity a SET a.balance = a.balance - :amount WHERE a.accountId = :accountId AND a.balance >= :amount")
    int deductBalance(String accountId, Double amount);
}
