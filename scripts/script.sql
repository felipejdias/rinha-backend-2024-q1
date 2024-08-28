-- Coloque scripts iniciais aqui
CREATE TABLE client (
                        client_id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        credit_limit INTEGER NOT NULL,
                        balance INTEGER DEFAULT 0
);

CREATE TABLE transaction (
                             transaction_id VARCHAR(36) PRIMARY KEY,
                             type VARCHAR(1) NOT NULL,
                             amount INTEGER NOT NULL,
                             description VARCHAR(100) NOT NULL,
                             create_at DATE NOT NULL,
                             client_id INTEGER NOT NULL
);

-- Adicionando a Foreign Key
ALTER TABLE transaction
    ADD CONSTRAINT fk_transaction_client
        FOREIGN KEY (client_id)
            REFERENCES client(client_id);

-- Criando a sequence
CREATE SEQUENCE client_id_seq;

-- Alterando a tabela client para usar a sequence
ALTER TABLE client
ALTER COLUMN client_id SET DEFAULT nextval('client_id_seq');

    DO $$
BEGIN
INSERT INTO client (name, credit_limit)
VALUES
    ('o barato sai caro', 1000 * 100),
    ('zan corp ltda', 800 * 100),
    ('les cruders', 10000 * 100),
    ('padaria joia de cocaia', 100000 * 100),
    ('kid mais', 5000 * 100);
END; $$