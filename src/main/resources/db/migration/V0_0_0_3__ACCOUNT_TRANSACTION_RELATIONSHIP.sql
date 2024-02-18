ALTER TABLE "transaction" ADD COLUMN account_id UUID NOT NULL;
ALTER TABLE "transaction" ADD FOREIGN KEY (account_id) REFERENCES account (account_id);
