CREATE TABLE transaction_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255),
    status VARCHAR(8) NOT NULL,
    transaction_type VARCHAR(8) NOT NULL,
    icon VARCHAR(255),
    color VARCHAR(7) DEFAULT '#000000'
);

CREATE TABLE "transaction" (
    id uuid NOT NULL,
    description VARCHAR(255),
    category_id BIGINT NOT NULL,
    request_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    type VARCHAR(8) NOT NULL,
    payment_method VARCHAR(11) NOT NULL,
    status VARCHAR(9) NOT NULL,
    transaction_date VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE "transaction" ADD CONSTRAINT FK_TRANSACTION_TRANSACTIONCATEGORY FOREIGN KEY (category_id) REFERENCES transaction_category(id);
