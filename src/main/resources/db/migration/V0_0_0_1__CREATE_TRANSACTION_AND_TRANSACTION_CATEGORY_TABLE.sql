CREATE TABLE transaction_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255),
    status VARCHAR(8) NOT NULL,
    icon VARCHAR(255),
    color VARCHAR(7) DEFAULT '#000000'
);

CREATE TABLE "transaction" (
    id VARCHAR(36) PRIMARY KEY,
    description VARCHAR(255),
    category BIGINT NOT NULL,
    request_id VARCHAR(36) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    type VARCHAR(8) NOT NULL,
    payment_method VARCHAR(11) NOT NULL,
    status VARCHAR(9) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

ALTER TABLE "transaction" ADD CONSTRAINT FK_TRANSACTION_TRANSACTIONCATEGORY FOREIGN KEY (category) REFERENCES transaction_category(id);
