CREATE TABLE accounts (
    account_id VARCHAR(50) PRIMARY KEY,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00
);


CREATE TABLE transactions (
    transaction_id UUID PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    sender_id VARCHAR(50) NOT NULL,
    receiver_id VARCHAR(50) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) CHECK (status IN ('pending', 'completed', 'failed')) NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);




INSERT INTO accounts (account_id, balance) VALUES ('1101', 25000.0);
INSERT INTO accounts (account_id, balance) VALUES ('1102', 20000.0);
INSERT INTO accounts (account_id, balance) VALUES ('1103', 30000.0);