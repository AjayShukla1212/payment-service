# Payment Initiation and Amount Transfer Application Using Apache Kafka and Spring Boot

This application facilitates payment initiation and amount transfers between two accounts.

## Prerequisites

Before running the application, ensure the following are set up:

1. **Kafka Setup**  
   Set up Kafka on your local system. Follow the instructions in the official Kafka documentation: [Kafka Quickstart](https://kafka.apache.org/quickstart).

2. **PostgreSQL Configuration**  
   Enter your PostgreSQL database credentials in the `application.yml` file.

## Running the Application

1. After configuring Kafka and PostgreSQL, run the application.
2. Once the application is up and stable, insert initial balances into the `accounts` table.

### Insert Initial Account Balances

Execute the following SQL queries to add balances to the accounts:

```sql
INSERT INTO accounts (account_id, balance) VALUES ('1101', 25000.0);
INSERT INTO accounts (account_id, balance) VALUES ('1102', 20000.0);
INSERT INTO accounts (account_id, balance) VALUES ('1103', 30000.0);
```

## Initiating a Transaction

Use the following cURL command to initiate a transaction:

```bash
curl --location 'http://localhost:8080/transactions/initiate' \
--header 'Content-Type: application/json' \
--data '{
  "transaction_id": "34fb4335-fb89-4e33-91fc-c9d37adfcb75",
  "amount": 800,
  "currency": "INR",
  "sender_id": "1103",
  "receiver_id": "1102",
  "transaction_type": "payment"
}'
```

## Transaction Flow

1. Upon executing the cURL command, the transaction initiation is logged and stored in the Kafka cluster.
2. A Kafka consumer validates the transaction data.
3. If the sender has sufficient balance, the transaction details are saved to the database.

---

