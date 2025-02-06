package com.ajay.payment_service.transaction_management.service;

import com.ajay.payment_service.transaction_management.constants.Constants;
import com.ajay.payment_service.transaction_management.dao.AccountEntity;
import com.ajay.payment_service.transaction_management.dao.TransactionEntity;
import com.ajay.payment_service.transaction_management.dto.TransactionDTO;
import com.ajay.payment_service.transaction_management.repo.AccountRepo;
import com.ajay.payment_service.transaction_management.repo.TransactionRepo;
import com.ajay.payment_service.transaction_management.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
public class KafkaConsumerService {


    private final TransactionRepo transactionRepository;
    private final AccountRepo accountRepo;


    public KafkaConsumerService(TransactionRepo transactionRepository,  AccountRepo accountRepo) {
        this.transactionRepository = transactionRepository;
        this.accountRepo = accountRepo;
    }

    @KafkaListener(topics = Constants.TOPIC, groupId = "payment-group")
    @Transactional
    public void processTransaction(TransactionDTO transaction, Acknowledgment acknowledgment) {
        try {
            System.out.println("Processing transaction: " + transaction.toString());

            // Check if sender has enough balance
            Optional<AccountEntity> senderAccount = accountRepo.findById(transaction.getSenderId());
            if(senderAccount.isEmpty()){
                System.out.println("Transaction failed: Invalid Sender Id");
                updateTransactionStatus(transaction, Constants.FAILED);
                acknowledgment.acknowledge();
                return;
            }
            if (senderAccount.get().getBalance() < transaction.getAmount()) {
                System.err.println("Transaction failed: Insufficient balance");
                updateTransactionStatus(transaction, Constants.FAILED);
                acknowledgment.acknowledge();
                return;
            }
            Optional<AccountEntity> receiverAccount = accountRepo.findById(transaction.getReceiverId());
            if(receiverAccount.isEmpty()){
                System.err.println("Transaction failed: Invalid Receiver Id");
                updateTransactionStatus(transaction, Constants.FAILED);
                acknowledgment.acknowledge();
                return;
            }

            // Deduct balance and update receiver
            senderAccount.get().setBalance(senderAccount.get().getBalance() - transaction.getAmount());
            accountRepo.save(senderAccount.get());

            receiverAccount.get().setBalance(receiverAccount.get().getBalance() + transaction.getAmount());
            accountRepo.save(senderAccount.get());


            updateTransactionStatus(transaction, Constants.COMPLETED);
            acknowledgment.acknowledge();

            System.out.println("Transaction Completed");

        } catch (Exception e) {
            System.err.println("Error processing transaction: " + e.getMessage());
            throw e;
        }
    }

    private void updateTransactionStatus(TransactionDTO transaction, String status) {
        TransactionEntity transactionEntity = Utility.convertDTOtoDAOTransaction(transaction);
        transactionEntity.setStatus(status);
        transactionEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(transactionEntity);
    }
}
