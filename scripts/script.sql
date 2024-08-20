-- Coloque scripts iniciais aqui
CREATE TABLE client (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          limit INTEGER NOT NULL
);

CREATE TABLE transaction (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          type VARCHAR(1) NOT NULL,
                          amount INTEGER NOT NULL,
                          description VARCHAR(100) NOT NULL,
                          create_at DATE NOT NULL
);

    DO $$
BEGIN
INSERT INTO clientes (name, limit)
VALUES
    ('o barato sai caro', 1000 * 100),
    ('zan corp ltda', 800 * 100),
    ('les cruders', 10000 * 100),
    ('padaria joia de cocaia', 100000 * 100),
    ('kid mais', 5000 * 100);
END; $$