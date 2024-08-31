create table if not exists client
(
    client_id    bigint not null
        primary key,
    balance      bigint,
    credit_limit bigint,
    name         varchar(255)
);

create table if not exists transactions
(
    id          uuid         not null
        primary key,
    amount      bigint       not null,
    created_at  timestamp(6) with time zone,
    description varchar(255) not null,
    type        varchar(255)
        constraint transactions_type_check
            check ((type)::text = ANY ((ARRAY ['CREDITO'::character varying, 'DEBITO'::character varying])::text[])),
    client_id   bigint       not null
        constraint fk7y7yu8a8upa3ct3qwfwonbn9q
            references client
);

    DO $$
BEGIN
INSERT INTO client (client_id, name, credit_limit, balance)
VALUES
    (1, 'o barato sai caro', 1000 * 100,0 ),
    (2, 'zan corp ltda', 800 * 100,0 ),
    (3, 'les cruders', 10000 * 100, 0 ),
    (4, 'padaria joia de cocaia', 100000 * 100, 0),
    (5, 'kid mais', 5000 * 100, 0);
END; $$;

create sequence client_id_seq
    start with 7;

-- Alterando a tabela client para usar a sequence
ALTER TABLE client
    ALTER COLUMN client_id SET DEFAULT nextval('client_id_seq');

CREATE INDEX idx_transactions_client_id ON transactions (client_id);
CREATE INDEX idx_transactions_client_id_created_at ON transactions (client_id, created_at DESC);
CREATE INDEX idx_transactions_client_id_payment_type ON transactions (client_id, type);