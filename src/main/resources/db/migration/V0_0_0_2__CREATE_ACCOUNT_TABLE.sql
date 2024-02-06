CREATE TABLE account (
    account_id uuid NOT NULL,
    user_id varchar(254) NOT NULL,
    name VARCHAR(100) NOT NULL,
    icon VARCHAR(254) NOT NULL,
    balance TEXT NOT NULL DEFAULT 0.0,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (account_id)
);

-- Criar índice com user_id e user_id + account_id
