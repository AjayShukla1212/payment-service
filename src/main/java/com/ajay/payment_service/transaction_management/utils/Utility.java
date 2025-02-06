package com.ajay.payment_service.transaction_management.utils;

import com.ajay.payment_service.transaction_management.dao.TransactionEntity;
import com.ajay.payment_service.transaction_management.dto.TransactionDTO;

import java.sql.Timestamp;

public class Utility {

    public static TransactionEntity convertDTOtoDAOTransaction(final TransactionDTO transactionDTO){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setReceiverId(transactionDTO.getReceiverId());
        transaction.setSenderId(transactionDTO.getSenderId());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return transaction;
    }
}
