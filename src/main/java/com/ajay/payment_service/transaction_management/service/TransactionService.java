package com.ajay.payment_service.transaction_management.service;

import com.ajay.payment_service.transaction_management.constants.Constants;
import com.ajay.payment_service.transaction_management.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public String initiateTransaction(TransactionDTO transaction) {
        kafkaTemplate.send(Constants.TOPIC, transaction);
        return "Transaction initiated successfully.";
    }
}
