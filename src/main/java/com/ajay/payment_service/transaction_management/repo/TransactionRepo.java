package com.ajay.payment_service.transaction_management.repo;

import com.ajay.payment_service.transaction_management.dao.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepo extends JpaRepository<TransactionEntity, String> {
}
