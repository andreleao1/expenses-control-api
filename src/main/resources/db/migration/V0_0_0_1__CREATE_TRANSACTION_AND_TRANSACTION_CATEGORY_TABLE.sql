CREATE TABLE transaction_category (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    icon VARCHAR(255),
    color VARCHAR(255) DEFAULT '#000000'
);

CREATE TABLE "transaction" (
    id VARCHAR(255) PRIMARY KEY,
    description VARCHAR(255),
    category BIGINT NOT NULL,
    request_id VARCHAR(255) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    type VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

ALTER TABLE "transaction" ADD CONSTRAINT FK_TRANSACTION_TRANSACTIONCATEGORY FOREIGN KEY (category) REFERENCES transaction_category(id);
