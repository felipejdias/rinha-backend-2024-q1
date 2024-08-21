-- Coloque scripts iniciais aqui
CREATE TABLE client (
                          client_id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          credit_limit INTEGER NOT NULL
);

CREATE TABLE transaction (
                          transaction_id UUID PRIMARY KEY,
                          type VARCHAR(1) NOT NULL,
                          amount INTEGER NOT NULL,
                          description VARCHAR(100) NOT NULL,
                          create_at DATE NOT NULL
);
    DO $$
BEGIN
INSERT INTO clientes (name, credit_limit)
VALUES
    ('o barato sai caro', 1000 * 100),
    ('zan corp ltda', 800 * 100),
    ('les cruders', 10000 * 100),
    ('padaria joia de cocaia', 100000 * 100),
    ('kid mais', 5000 * 100);
END; $$