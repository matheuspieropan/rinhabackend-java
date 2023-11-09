CREATE TABLE public.pessoa
(
    id         bigserial NOT NULL,
    apelido    varchar(255) NULL,
    nascimento varchar(255) NULL,
    nome       varchar(255) NULL,
    stack      varchar(1000) NULL,
    CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);