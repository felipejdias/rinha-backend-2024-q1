CREATE  UNLOGGED  TABLE IF NOT EXISTS CLIENT
(
    CLIENT_ID    INT NOT NULL PRIMARY KEY,
    BALANCE      INT,
    CREDIT_LIMIT INT,
    NAME         VARCHAR(255)
);

-- Habilite a extensão uuid-ossp
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE UNLOGGED TABLE IF NOT EXISTS TRANSACTIONS
(
    ID          UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    AMOUNT      BIGINT       NOT NULL,
    CREATED_AT  TIMESTAMP(6) WITH TIME ZONE,
    DESCRIPTION VARCHAR(255) NOT NULL,
    TYPE        VARCHAR(255),
    CLIENT_ID   BIGINT       NOT NULL
);

DO
$$
    BEGIN
        INSERT INTO CLIENT (CLIENT_ID, NAME, CREDIT_LIMIT, BALANCE)
        VALUES (1, 'O BARATO SAI CARO', 1000 * 100, 0),
               (2, 'ZAN CORP LTDA', 800 * 100, 0),
               (3, 'LES CRUDERS', 10000 * 100, 0),
               (4, 'PADARIA JOIA DE COCAIA', 100000 * 100, 0),
               (5, 'KID MAIS', 5000 * 100, 0);
    END;
$$;

CREATE SEQUENCE CLIENT_ID_SEQ
    START WITH 7;

-- ALTERANDO A TABELA CLIENT PARA USAR A SEQUENCE
ALTER TABLE CLIENT
    ALTER COLUMN CLIENT_ID SET DEFAULT NEXTVAL('CLIENT_ID_SEQ');

CREATE INDEX IDX_TRANSACTIONS_CLIENT_ID ON TRANSACTIONS (CLIENT_ID);
CREATE INDEX IDX_TRANSACTIONS_CLIENT_ID_CREATED_AT ON TRANSACTIONS (CLIENT_ID, CREATED_AT DESC);
CREATE INDEX IDX_TRANSACTIONS_CLIENT_ID_PAYMENT_TYPE ON TRANSACTIONS (CLIENT_ID, TYPE);

CREATE OR REPLACE FUNCTION register_transaction(
    clientIdParam INTEGER,
    typeParam character varying,
    amountParam BIGINT,
    descriptionParam character varying
)
    RETURNS TABLE(
                     saldoRetorno int,
                     limiteRetorno int
                 )
AS $$
DECLARE
    client client%ROWTYPE;
    newBalance int;
    affectedRows int;
BEGIN
    -- Bloquear a linha do cliente para atualização
    PERFORM * FROM client WHERE client_id = clientIdParam FOR UPDATE;

    -- Determinar se a transação é débito ou crédito
    IF typeParam = 'd' THEN
        newBalance := -amountParam; -- Débito subtrai do saldo
    ELSE
        newBalance := amountParam; -- Crédito adiciona ao saldo
    END IF;

    -- Atualizar o saldo do cliente
    UPDATE client
    SET balance = balance + newBalance
    WHERE client_id = clientIdParam
      AND (newBalance > 0 OR balance + newBalance >= credit_limit * -1) -- Verifica limite no caso de débito
    RETURNING * INTO client;

    -- Verificar se a atualização afetou alguma linha
    GET DIAGNOSTICS affectedRows = ROW_COUNT;

    IF affectedRows = 0 THEN
        RAISE EXCEPTION 'Cliente nao possui limite';
    END IF;

    -- Inserir registro da transação
    INSERT INTO transactions (client_id, amount, type, description, created_at)
    VALUES (clientIdParam, amountParam, typeParam, descriptionParam, current_timestamp);

    -- Retornar o novo saldo e limite
    RETURN QUERY SELECT client.balance, client.credit_limit;
END;
$$ LANGUAGE plpgsql;